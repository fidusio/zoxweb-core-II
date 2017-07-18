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
package org.zoxweb.shared.http;

import java.util.List;

import org.zoxweb.shared.net.InetSocketAddressDAO;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.GetValue;
import org.zoxweb.shared.util.ReferenceID;
import org.zoxweb.shared.util.SetDescription;
import org.zoxweb.shared.util.SetName;

/**
 *
 */
public interface HTTPMessageConfigInterface
extends ReferenceID<String>, SetName, SetDescription
{
	/**
	 * @return true if mutli part encoding
	 */
	public boolean isMultiPartEncoding();

	/**
	 * @param multiPartEncoding true to enable multi part encoding
	 */
	public void setMultiPartEncoding(boolean multiPartEncoding);
	
	
	/**
	 * Get the action parameters as an array list of NVPairs.
	 * The parameters sequence should be preserved during invocation 
	 * @return http parameters
	 */
	public ArrayValues<GetNameValue<String>> getParameters();

	/**
	 * Set the action parameters list
	 * @param params
	 */
	public void setParameters(List<GetNameValue<String>> params);

	/**
	 * Get the action type
	 * @return the method
	 */
	public HTTPMethod getMethod();
	
	/**
	 * Set the action type
	 * @param httpMethod
	 */
	public void setMethod(HTTPMethod httpMethod);
	
	/**
	 * Set the action type
	 * @param httpMethod
	 */
	public void setMethod(String httpMethod);
	
	/**
	 * Get the URI extension
	 * @return the uri part
	 */
	public String getURI();
	
	/**
	 * Set the URI extension
	 * @param uri
	 */
	public void setURI(String uri);
	
	/**
	 * Get the URL
	 * @return url part 
	 */
	public String getURL();
	
	
	/**
	 * Set the URL
	 * @param url
	 */
	public void setURL(String url);

	/**
	 * Set the HTTP request parameters
	 * @return headers
	 */
	public ArrayValues<GetNameValue<String>> getHeaderParameters();

	/**
	 * Get the HTTP request parameters
	 * @param headerParams
	 */
	public void setHeaderParameters(List<GetNameValue<String>> headerParams);
	
	/**
	 * @return true if url encoding is enabled
	 */
	public HTTPParameterFormatter getHTTPParameterFormatter();
	
	/**
	 * enable url encoding
	 * @param value
	 */
	public void setHTTPParameterFormatter(HTTPParameterFormatter value);
	
	
	/**
	 * @return true if url encoding is enabled
	 */
	public boolean isURLEncodingEnabled();
	
	/**
	 * enable url encoding
	 * @param value
	 */
	public void setURLEncodingEnabled(boolean value);
	
	
	
	/**
	 * Set the request payload or content
	 * @param payload
	 */
	public void setContent(byte[] payload);

	/**
	 * Set the request payload or content
	 * @param payload
	 */
	public void setContent(String payload);
	
	
	/**
	 * Get the request payload or content
	 * @return content
	 */
	public byte[] getContent();
	
	/**
	 * @return content length
	 */
	public int getContentLength();
	/**
	 * Set the content length
	 * @param length
	 */
	public void setContentLength(int length);

	/**
	 * @return the multipart boundary 
	 */
	public String getBoundary();

	/**
	 * This is a optional parameter that is set by the http call 
	 * in case of mutlitpart post
	 * @param boundary
	 */
	public void setBoundary(String boundary);
	
	/**
	 * @return true if redirect is enabled
	 */
	public boolean isRedirectEnabled();

	public void setRedirectEnabled(boolean redirectEnabled);
	
	/**
	 * The connect timeout in millis seconds before throwing an exception, 0 to disable
	 * 
	 * @return connection timeout in millis
	 */
	public int getConnectTimeout();
	
	/**
	 * Set the connection timeout is millis 
	 * @param connectTimeout
	 */
	public void setConnectTimeout(int connectTimeout);
	
	/**
	 * The read timeout in millis seconds before throwing an exception, 0 to disable
	 * 
	 * @return the read timeout in millis
	 */
	public int getReadTimeout();
	
	/**
	 * Set the read timeout is millis
	 * @param readTimeout
	 */
	public void setReadTimeout(int readTimeout);

	/**
	 * Get the encoding to be used for the parameter, if null default will be used
	 * @return charset
	 */
	public String getCharset();


	/**
	 * 
	 * Set the charset	 
	 * @param charset
	 */
	public void setCharset(String charset);
	
	/**
	 * 
	 * @return the user
	 */
	public String getUser();
	
	/**
	 * Set the user
	 * @param user
	 */
	public void setUser(String user);
	
	/**
	 * @return user password
	 */
	public String getPassword();
	
	/**
	 * Set user password
	 * @param password
	 */
	public void setPassword(String password);


	
	/**
	 * 
	 * @return HTTPAuthentication
	 */
	public HTTPAuthentication getAuthentitcation();
	
	public void setAuthentication(HTTPAuthentication httpAuthentication);
	
	/**
	 * @return the proxy address null if not set
	 */
	public InetSocketAddressDAO getProxyAddress();
	
	/**
	 * Set the proxy address
	 * @param proxyAddress
	 */
	public void setProxyAddress(InetSocketAddressDAO proxyAddress);
	
	/**
	 * @return reason
	 */
	public String getReason();
	
	public void setReason(String reason);
	
	/**
	 * 
	 * Get the header content type
	 * 
	 * @return content type
	 */
	public String getContentType();
	
	/**
	 * Set the header content type
	 * 
	 * @param contentType
	 */
	public void setContentType(String contentType);
	
	/**
	 * 
	 * Set the header content type
	 * 
	 * @param contentType
	 */
	public void setContentType(GetValue<String> contentType);
	
	/**
	 * Return the Cookie header value
	 * @return cookie
	 */
	public String getCookie();
	
	/**
	 * Set cookie
	 * @param cookieValue
	 */
	public void setCookie(String cookieValue);
	
	/**
	 * Set cookie
	 * @param cookieValue
	 */
	public void setCookie(GetValue<String> cookieValue);
	
	/**
	 * @return HTTPVersion
	 */
	public HTTPVersion getHTTPVersion();
	
	public void setHTTPVersion(String version);
	
	public void setHTTPVersion(HTTPVersion version);
	
	
	public void setHTTPStatusCode(HTTPStatusCode status);
	
	public HTTPStatusCode getHTTPStatusCode();
	
	
}
