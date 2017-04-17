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

import org.zoxweb.shared.security.SecurityConsts.OAuthParam;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.GetValue;
import org.zoxweb.shared.util.NVPair;

public enum HTTPHeaderName
    implements GetName
{
	ACCEPT("Accept"),
	ACCEPT_CHARSET("Accept-Charset"),
	ACCEPT_LANGUAGE("Accept-Language"),
	ACCEPT_DATETIME("Accept-Datetime"),
	ACCEPT_ENCODING("Accept-Encoding"),
	AUTHORIZATION(OAuthParam.AUTHORIZATION.getNVConfig().getDisplayName()),
	CONNECTION("Connection"),
	CONTENT_DISPOSITION("Content-Disposition"),
	CONTENT_ENCODING("Content-Encoding"),
	CONTENT_LENGTH("Content-Length"),
	CONTENT_TRANSFER_ENCODING("Content-Transfer-Encoding"),
	CONTENT_TYPE("Content-Type"),
	
	COOKIE("Cookie"),
	FROM("From"),
	HOST("Host"),
	PROXY_AGENT("Proxy-Agent"),
	PROXY_CONNECTION("Proxy-Connection"),
	SET_COOKIE("Set-Cookie"),
	TRANSFER_ENCODING("Transfer-Encoding"),
	USER_AGENT("User-Agent"),
	X_ACCEPT_ENCODING("X-Accept-Encoding"),
	;

	
	
	private final String name;
	
	
	HTTPHeaderName( String n)
	{
		name = n;
	}
	
	
	@Override
	public String getName() 
	{
		// TODO Auto-generated method stub
		return name;
	}
	
	public String toString()
	{
		return name;
	}
	
	
	public static GetNameValue<String> toHTTPHeader(GetName gn, GetValue<String> value)
	{
		return new NVPair(gn, value);
	}
	
	
	public static GetNameValue<String> toHTTPHeader(GetName gn, String value)
	{
		return new NVPair(gn, value);
	}
	
	
	public static GetNameValue<String> toHTTPHeader(String name, GetValue<String> value)
	{
		return new NVPair(name, value);
	}
	
	
	public static GetNameValue<String> toHTTPHeader(String name, String value)
	{
		return new NVPair(name, value);
	}

}
