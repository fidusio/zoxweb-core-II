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
package org.zoxweb.server.http.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.zoxweb.server.http.HTTPRequestAttributes;
import org.zoxweb.server.io.FileInfoStreamSource;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.io.UByteArrayOutputStream;
import org.zoxweb.server.util.GSONUtil;

import org.zoxweb.server.util.ZIPUtil;
import org.zoxweb.shared.api.APIError;
import org.zoxweb.shared.data.FileInfoDAO;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPHeaderValue;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPStatusCode;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.QuickLZ;
import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class HTTPServletUtil
{
	public static final int ZIP_LIMIT = 512;
	
	private static final transient Logger log = Logger.getLogger(HTTPServletUtil.class.getName());

	private HTTPServletUtil()
	{
		
	}

	
	public static List<GetNameValue<String>> extractRequestHeaders(HttpServletRequest req)
	{
		ArrayList<GetNameValue<String>> ret = new ArrayList<GetNameValue<String>>();
		
		
		Enumeration<String> headerNames = req.getHeaderNames();
		for (; headerNames.hasMoreElements();)
		{
			String headerName = headerNames.nextElement();
			Enumeration<String> headerValues = req.getHeaders(headerName);
			for (; headerValues.hasMoreElements();)
			{
				ret.add( new NVPair(headerName, headerValues.nextElement()));
			}
		}
		
		
		
		return ret;
	}
	
	
	public static HTTPRequestAttributes extractRequestAttributes(HttpServletRequest req) throws IOException
	{
		return extractRequestAttributes(req, true);
	}
	
	@SuppressWarnings("unchecked")
	public static HTTPRequestAttributes extractRequestAttributes(HttpServletRequest req, boolean readParameters) throws IOException
	{
		HTTPRequestAttributes ret = null;
		
		DiskFileItemFactory dfif = null;
		List<FileItem> items = null;
		// check if the request is of multipart type
		List<GetNameValue<String>> headers = HTTPServletUtil.extractRequestHeaders(req);
		List<GetNameValue<String>> params = new ArrayList<GetNameValue<String>>();
		List<FileInfoStreamSource> streamList = new ArrayList<FileInfoStreamSource>();
		
		/*
		 * 	Retrieve path info if it exists. If the pathInfo starts or ends with a "/", the "/" is removed
		 * 	and value is trimmed.
		 */
		String pathInfo = req.getPathInfo();
		//	Removing the first "/" in pathInfo.
		pathInfo = SharedStringUtil.trimOrNull(SharedStringUtil.valueAfterLeftToken(pathInfo, "/"));
		//	Removing the last "/" in pathInfo.
		if (pathInfo != null)
		{
			if (pathInfo.endsWith("/"))
			{
				pathInfo = SharedStringUtil.trimOrNull(pathInfo.substring(0, pathInfo.length() - 1));
			}
		}
		
		if (ServletFileUpload.isMultipartContent(req))
		{
			
			String body = null;
			dfif = new DiskFileItemFactory();
			try
			{
				ServletFileUpload upload = new ServletFileUpload(dfif);
				upload.setHeaderEncoding(SharedStringUtil.UTF_8);
				items = upload.parseRequest(req);
			}
			catch ( FileUploadException e )
			{
				throw new IOException("Upload problem:" + e);
			}
			for ( FileItem fi : items)
			{
				if ( fi.isFormField())
				{
					String name = fi.getFieldName();
					String value = fi.getString();
					params.add( new NVPair(name, value));
				
				}
				else
				{
					String content = fi.getContentType();
					InputStream is = fi.getInputStream();
					String filename = fi.getName();
					FileInfoDAO fid = new FileInfoDAO();
					fid.setName(filename);
					fid.setCreationTime(System.currentTimeMillis());
					fid.setContentType(content);
					fid.setLength(fi.getSize());
					

					
					
					FileInfoStreamSource fiss = new FileInfoStreamSource( fid, is);
					streamList.add( fiss);
				}
			}
			
			ret = new HTTPRequestAttributes(pathInfo, req.getContentType(), true, headers, params, streamList, body);
		}
		else
		{
			if (readParameters)
			{
				params = (List<GetNameValue<String>>) SharedUtil.toNVPairs(req.getParameterMap());
			}
			 
			 
			ret = new HTTPRequestAttributes(pathInfo, req.getContentType(), false, headers, params, streamList, IOUtil.inputStreamToString(req.getInputStream(), false));
		}
		
		return ret; 
	}
	
	
	public static String toString(HttpServletRequest req) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		List<GetNameValue<String>> headers    = HTTPServletUtil.extractRequestHeaders(req);
		@SuppressWarnings("unchecked")
		List< GetNameValue<String>> parameters = (List<GetNameValue<String>>) SharedUtil.toNVPairs(req.getParameterMap());
		String method = req.getMethod();
		sb.append( "Method:" + method + "\n");
		sb.append( "Headers:\n");
		sb.append( headers);
		sb.append( "Parameters:\n");
		if (parameters == null || parameters.size() == 0)
		{
			UByteArrayOutputStream baos = IOUtil.inputStreamToByteArray(req.getInputStream(), false);
			sb.append("binary data:" + baos.size() + "\n");
			sb.append(new String(SharedBase64.encode( baos.toByteArray())));
		}
		else
			sb.append( parameters);
		return sb.toString();
	}
	
	
	
	
	
	
	

	
	
	public static HTTPHeaderValue shouldZIPResponseContent(HttpServletRequest request, String responseContent)
	{
		if (exceedsUncompressedContentLengthLimit(responseContent))
		{
			return acceptsZIPEncoding(request);
		}
		
		return null;
	}

	public static void setZIPEncodingHeader(HttpServletResponse response, HTTPHeaderValue hv)
	{
		if (hv != null)
			response.setHeader(HTTPHeaderName.CONTENT_ENCODING.getName(), hv.getValue());
	}
	
	public static boolean exceedsUncompressedContentLengthLimit(String content)
	{
		return content.length() > ZIP_LIMIT;
	}
	
	public static HTTPHeaderValue acceptsZIPEncoding(HttpServletRequest req)
	{
		HTTPHeaderValue zip = null;
		if (req != null)
		{
			String encoding = req.getHeader(HTTPHeaderName.X_ACCEPT_ENCODING.getName());
			if (encoding == null)
			{
				encoding = req.getHeader(HTTPHeaderName.ACCEPT_ENCODING.getName());
			}
			
			if (SharedStringUtil.contains(encoding, HTTPHeaderValue.CONTENT_ENCODING_LZ, true))
			{
				zip = HTTPHeaderValue.CONTENT_ENCODING_LZ;
			}
			else if (SharedStringUtil.contains(encoding, HTTPHeaderValue.CONTENT_ENCODING_GZIP, true))
			{
				zip = HTTPHeaderValue.CONTENT_ENCODING_GZIP;
				//zip = HTTPHeaderValue.CONTENT_ENCODING_LZ;
			}
		}
		
		return zip;
	  }
	
	
	
	public static byte[] compress(String zipMode, byte[] content) throws NullPointerException, IllegalArgumentException, IOException
	{
		SharedUtil.checkIfNulls("null value", zipMode, content);
		zipMode = zipMode.toLowerCase();
		switch(zipMode)
		{
		
		case "gzip":
			
			
			return ZIPUtil.gzip(content);
			
//			ByteArrayOutputStream output = null;
//			GZIPOutputStream gzipOutputStream = null;
//			try
//			{
//				output = new ByteArrayOutputStream(content.length);
//				gzipOutputStream = new GZIPOutputStream(output);
//				gzipOutputStream.write(content);
//				gzipOutputStream.flush();
//				gzipOutputStream.finish();
//				return output.toByteArray();
//			}
//			catch(IOException e)
//			{
//			  throw new IllegalArgumentException(e.getMessage());
//			}
//			finally
//			{
//				ServerUtil.close(gzipOutputStream);
//			}
		      
			
		case "lz":
			return  QuickLZ.compress(content, 1);
		
		default:
			throw new IllegalArgumentException("unsupported compression mode " + zipMode);
		
		}
	}
	
	
	public static int sendJSON(HttpServletRequest req, HttpServletResponse resp, HTTPStatusCode code, NVEntity nve)
			throws IOException
	{
		String json = null;
		
		if (nve != null)
		{
			json = nve instanceof APIError ? GSONUtil.toJSON(nve, true, false, true) : GSONUtil.toJSON(nve, false, false, true);
		}
		
		return sendJSON(req, resp, code, json);
	}
	
	public static int sendJSON(HttpServletRequest req, HttpServletResponse resp, HTTPStatusCode code, List<? extends NVEntity> nves)
			throws IOException
	{
		return sendJSON(req, resp, code, nves != null ? GSONUtil.toJSONValues(nves.toArray(new NVEntity[0]), false, false, null) : null);	
	}
	
	public static int sendJSON(HttpServletRequest req, HttpServletResponse resp, HTTPStatusCode code, NVEntity nves[])
			throws IOException
	{
		return sendJSON(req, resp, code, nves != null ? GSONUtil.toJSONValues(nves, false, false, null) : null);
	}
	
	public static int sendJSON(HttpServletRequest req, HttpServletResponse resp, HTTPStatusCode code, String json)
			throws IOException
	{
		resp.setStatus(code.CODE);
		resp.setContentType(HTTPMimeType.APPLICATION_JSON.getValue());
		resp.setCharacterEncoding(SharedStringUtil.UTF_8);
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		//resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setDateHeader("Expires", 0); // Proxies.
		
		if (json != null)
		{
			HTTPHeaderValue zip = shouldZIPResponseContent(req, json);
			
			if (zip != null)
			{
				setZIPEncodingHeader(resp, zip);
				log.info("content will be compressed " + zip);
				// compress
				byte[] responseBytes = compress(zip.getValue(), SharedStringUtil.getBytes(json));
				// encode base64
				if (zip == HTTPHeaderValue.CONTENT_ENCODING_LZ)
				{
					responseBytes = SharedBase64.encode(responseBytes);
				}
				else if (zip == HTTPHeaderValue.CONTENT_ENCODING_GZIP)
				{
					resp.setHeader(HTTPHeaderName.CONTENT_DISPOSITION.getName(), "attachment");
				}
				resp.getOutputStream().write(responseBytes);
				return responseBytes.length;	
			}
			
			byte[] toWrite = SharedStringUtil.getBytes(json);
			
			resp.getOutputStream().write(toWrite);	
			return toWrite.length;
		}
		
		return 0;
	}
	
}