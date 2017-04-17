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

import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.GetValue;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedUtil;

/**
 *
 */
public enum HTTPMimeType
	implements GetValue<String>
{
	APPLICATION_WWW_URL_ENC("application/x-www-form-urlencoded"),
	APPLICATION_JSON("application/json"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	MULTIPART_FORM_DATA("multipart/form-data"),
	TEXT_CSV("text/csv"),
	TEXT_HTML("text/html"),
	TEXT_PLAIN("text/plain"),
	
	;

	
	private final String value;
	
	
	HTTPMimeType( String value)
	{
		this.value = value;
	}
	/**
	 * @see org.zoxweb.shared.util.GetValue#getValue()
	 */
	@Override
	public String getValue() 
	{
		// TODO Auto-generated method stub
		return value;
	}
	
	public static HTTPMimeType lookup(String str)
	{
		return (HTTPMimeType) SharedUtil.matchingEnumContent(HTTPMimeType.values(), str);
	}
	
	public static GetNameValue<String> toContentType(GetValue<String> contentType)
	{
		return new NVPair(HTTPHeaderName.CONTENT_TYPE, contentType);
	}
	
	
	public static GetNameValue<String> toContentType(String contentType)
	{
		return new NVPair(HTTPHeaderName.CONTENT_TYPE, contentType);
	}

}
