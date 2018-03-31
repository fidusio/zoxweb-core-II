/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.server.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.zoxweb.server.io.CloseEnabledInputStream;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.net.NetUtil;
import org.zoxweb.server.security.SSLCheckDisabler;
import org.zoxweb.server.security.SSLSocketProp;

import org.zoxweb.shared.filters.ReplacementFilter;

import org.zoxweb.shared.http.HTTPAuthorizationType;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPParameterFormatter;
import org.zoxweb.shared.http.HTTPCallException;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPResponseData;
import org.zoxweb.shared.http.HTTPStatusCode;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class HTTPCall 
{
	private HTTPMessageConfigInterface hcc;
	private String urlOverride;
	private ReplacementFilter uriFilter;
	
	private SSLSocketProp ssp;
	private OutputStream osBypass;
	private CloseEnabledInputStream contentAsIS;
	//private boolean embedParamsInURI;

	static
	{
		try
		{
			HTTPUtil.addHTTPMethods();
		}
		catch(Exception e)
		{
			e.fillInStackTrace();
		}
	}
	
	public HTTPCall(HTTPMessageConfigInterface params)
	{
		this( params, null, null, null, null, null);
	}
	
	public HTTPCall(HTTPMessageConfigInterface params, SSLSocketProp ssp)
	{
		this( params, ssp, null, null, null, null);
	}

	public HTTPCall(HTTPMessageConfigInterface params,
					SSLSocketProp ssp,
					String urlOverride,
					ReplacementFilter uriFilter,
					OutputStream osBypass,
					CloseEnabledInputStream contentAsIS)
//					boolean embedParamsInURI)
	{
		SharedUtil.checkIfNulls("HTTPActionParameters can't be null", params);
		this.hcc = params;
		this.urlOverride = urlOverride;
		this.uriFilter = uriFilter;
		this.ssp = ssp;
		this.osBypass = osBypass;
		//this.embedParamsInURI = embedParamsInURI;
		this.contentAsIS = contentAsIS;

		// if the ssl override  is not set but secure check is disabled then set
		// it to the default SSLCheckDisabler
		if (this.ssp == null && !params.isSecureCheckEnabled())
		{
			this.ssp = SSLCheckDisabler.SINGLETON;
		}
	}
	
	
//	public HTTPCall( HTTPCallConfig params, String urlOverride, URIFilter uriFilter, SSLSocketProp ssp, InputStream isContent, boolean embedParamsInURI)
//	{
//		SharedUtil.checkIfNulls("HTTPActionParameters can't be null", params);
//		this.hcc = params;
//		this.urlOverride = urlOverride;
//		this.uriFilter = uriFilter;
//		this.ssp = ssp;
//		this.isContent = isContent;
//		this.embedParamsInURI = embedParamsInURI;
//	}

	public boolean isSecure()
	{
		if (fullURL().toLowerCase().startsWith("https:"))
		{
			return true;
		}
		
		return false;
	}
	/**
	 * 
	 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
	 * 
	 * @return HTTPResponseData
	 * @throws IOException
	 */
	public HTTPResponseData sendRequest() throws IOException
	{
		ByteArrayOutputStream ret = null;
		int status;
		Map<String, List<String>> respHeaders = null;
		// format the payload first
		String encodedContentParams = HTTPUtil.formatParameters(hcc.getParameters(), hcc.getCharset(), hcc.isURLEncodingEnabled(), hcc.getHTTPParameterFormatter());
		String urlURI = fullURL();
		boolean embedPostPutParamsInURI = false;
		GetNameValue<String> contentType = hcc.getParameters().get(HTTPHeaderName.CONTENT_TYPE.getName());
		if (contentType != null && contentType.getValue() != null && contentType.getValue().toLowerCase().indexOf("x-www-form-urlencoded") == -1)
		{
			embedPostPutParamsInURI = true;
		}
		
		if (encodedContentParams.length() > 0)
		{
			switch( hcc.getMethod())
			{
			
			case POST:
			case PUT:
				if (!embedPostPutParamsInURI || hcc.isMultiPartEncoding())
				{
					HTTPMultiPartUtil.preMutliPart(hcc);
					break;
				}
			case GET:
			case DELETE:
			case PATCH:
			case PURGE:
			case CONNECT:
			case COPY:
			case LINK:
			case LOCK:
			case PROPFIND:
			case TRACE:
			case UNLINK:
			case UNLOCK:
			case VIEW:
				// if we have a GET
				if (!SharedStringUtil.isEmpty(encodedContentParams))
				{
					if (hcc.getHTTPParameterFormatter() == HTTPParameterFormatter.URL_ENCODED)
						urlURI += "?" + encodedContentParams;
					else if (hcc.getHTTPParameterFormatter() == HTTPParameterFormatter.URI_REST_ENCODED)
					{
						urlURI = SharedStringUtil.concat(urlURI, encodedContentParams, "" + HTTPParameterFormatter.URI_REST_ENCODED.getValue());
					}
				}
				break;
			default:
				break;
			
					
			}
		}
		
		URL url = new URL(urlURI);
		
		
		OutputStream os = null;
		InputStream is = null;
		InputStream isError = null;
		HttpURLConnection con = null;
		Proxy proxy = null;
		if (hcc.getProxyAddress() != null)
		{
			proxy = new Proxy(NetUtil.lookup(hcc.getProxyAddress().getProxyType()), new InetSocketAddress(hcc.getProxyAddress().getInetAddress(),  hcc.getProxyAddress().getPort()));
		}
		
		try
		{
			if (proxy != null)
			{
				con = (HttpURLConnection) url.openConnection(proxy);
			}
			else
			{
				con = (HttpURLConnection) url.openConnection();
			}
			
	
			if ( ssp != null)
				ssp.updateURLConnection(con);
			con.setRequestMethod(hcc.getMethod().toString());
			
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(true);
			con.setInstanceFollowRedirects(hcc.isRedirectEnabled());
			con.setConnectTimeout(hcc.getConnectTimeout());
			con.setReadTimeout(hcc.getReadTimeout());
			
			
			
		
			ArrayValues<GetNameValue<String>> reqHeaders = hcc.getHeaderParameters();
			// set the request headers
			if (reqHeaders != null)
			{
				for ( GetNameValue<String> nvp : reqHeaders.values())
				{
					con.setRequestProperty( nvp.getName(), nvp.getValue());
				}
			}
			
			
			// set the authentication from url or hcc
			// if url.getUserInfo() is not null ?
			// if hcc.getUser() && hcc.getPassword() != null
			// if still null check if the HTTPAuthentication is not null
			GetNameValue<String> authorizationHeader = HTTPAuthorizationType.BASIC.toHTTPHeader(url.getUserInfo(), null);
			if (authorizationHeader == null)
			{
				authorizationHeader = HTTPAuthorizationType.BASIC.toHTTPHeader(hcc.getUser(), hcc.getPassword());
			}
			
			if(authorizationHeader == null && hcc.getAuthentitcation() != null)
			{
				authorizationHeader = hcc.getAuthentitcation().toHTTPHeader();
			}
			
			
			if (authorizationHeader != null)
			{
				con.setRequestProperty(authorizationHeader.getName(), authorizationHeader.getValue());
			}
			
			
//			if (!SharedStringUtil.isEmpty(url.getUserInfo()))
//			{
//				GetNameValue<String> gnvAutho = HTTPAuthentication.BASIC.toHeaderParamater(url.getUserInfo(), null);
//				con.setRequestProperty(gnvAutho.getName(), gnvAutho.getValue());
//			}
//			else if (!SharedStringUtil.isEmpty(hcc.getUser()) && !SharedStringUtil.isEmpty(hcc.getPassword()))
//			{
//				con.setRequestProperty(HTTPHeaderName.AUTHORIZATION.getName(), HTTPAuthentication.BASIC + " " + new String(SharedBase64.encode(SharedUtil.toCanonicalID(':', hcc.getUser(), hcc.getPassword()).getBytes(SharedStringUtil.UTF_8))));
//			}
			
			
			
			// write the payload if it is a post
			if (!embedPostPutParamsInURI)
			{
				switch (hcc.getMethod())
				{
				case POST:
				case PUT:
					os = con.getOutputStream();
					if (hcc.isMultiPartEncoding())
					{
						HTTPMultiPartUtil.writeMultiPartContent(os, hcc.getBoundary(), hcc.getParameters());
					}
					else
						os.write(encodedContentParams.getBytes());
					break;
					default:
						
				}
			}
			
			
			

			
			if (hcc.getContent() != null && hcc.getContent().length > 0)
			{
				if (os == null)
				{
					os = con.getOutputStream();
				}
				
				os.write( hcc.getContent());
			}
			if (contentAsIS != null)
			{
				if ( os == null)
				{
					os = con.getOutputStream();
				}
				IOUtil.relayStreams(contentAsIS, os, false);
			}
			
			
			IOUtil.flush(os);
			status = (proxy != null && proxy.type() == Proxy.Type.SOCKS) ? 200 : con.getResponseCode();
			
			// check if we have an error in the response
			isError = con.getErrorStream();
			if ( isError != null)
			{
				ret  = IOUtil.inputStreamToByteArray(isError, false);
				respHeaders = con.getHeaderFields();
				HTTPResponseData rd = new HTTPResponseData(ret.toByteArray(), status, respHeaders);
				throw new HTTPCallException(con.getResponseMessage(),  rd);
			}
			
			HTTPStatusCode hsc = HTTPStatusCode.statusByCode(status);
			
			
			
		
			
			if (hsc != null && hcc.isRedirectEnabled())
			{
				switch(hsc)
				{
				case MOVED_PERMANENTLY:
				case FOUND:
				case SEE_OTHER:
					NVPair cookie = HTTPUtil.extractCookie(con.getHeaderFields(), 0);
					if (cookie != null)
						hcc.getHeaderParameters().add(cookie);
					String newURL = con.getHeaderField("Location");
					
					hcc.setURI(null);
					hcc.setURL(newURL);
					
					if (contentAsIS != null)
					{
						throw new HTTPCallException("Can not forward with ContentAsIS set",  new HTTPResponseData(null, status, con.getHeaderFields()));
					}
					HTTPCall hccRedirect = new HTTPCall(hcc, ssp, urlOverride, uriFilter, osBypass, contentAsIS);
					return hccRedirect.sendRequest();
					
				default:
						
					
				}
			}
			
			
			
			is = con.getInputStream();
			
			
			if ( osBypass == null)
			{
				ret = IOUtil.inputStreamToByteArray(is, false);
			}
			else 
			{
				IOUtil.relayStreams(is, osBypass, false);
			}
			

			
			respHeaders = con.getHeaderFields();
			
			
		}
		finally
		{
			if ( con != null)
				con.disconnect();
			
			
			IOUtil.close(os);
			IOUtil.close(is);
			IOUtil.close(isError);
			IOUtil.close(contentAsIS);
		}
		
		
		return new HTTPResponseData( ret != null ? ret.toByteArray() : null, status, respHeaders);
	}
	
	
	
	
	
	private String fullURL()
	{
		
		String ret;
		
		String uri = hcc.getURI();
		if ( uriFilter != null)
		{
			uri = uriFilter.validate( uri);
		}
		
		if ( urlOverride != null)
			ret = SharedStringUtil.concat(urlOverride, uri, "/");
		else
			ret = SharedStringUtil.concat(hcc.getURL(), uri, "/");
		
		
		return ret;
	}
	
	
	
	
//	public String fullyEncondedURL() throws IOException
//	{
//		
//		String encodedContentParams = HTTPUtil.formatParameters(hcc.getParameters(), hcc.getCharset());
//		
//		String urlURI = fullURL();
//		
//		if ( encodedContentParams.length() > 0)
//		{
//			switch( hcc.getMethod())
//			{
//			case GET:
//			case POST:
//			case DELETE:
//			case PATCH:
//				// if we have a GET
//				urlURI += "?" + encodedContentParams;
//				break;
//				default:	
//			}
//		}
//		
//		return urlURI;
//		
//	}
	
	
}