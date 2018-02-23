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
import org.zoxweb.shared.util.SharedUtil;

/**
 * HTTP Protocol methods or actions RFC2616
 * @author javconsigliere
 *
 */
public enum HTTPMethod
implements GetNameValue<String>
{
	
	OPTIONS("OPTIONS", "doOptions"),
	GET("GET", "doGet"),
	HEAD("HEAD", "doHead"),
	POST("POST", "doPost"),
	PUT("PUT", "doPut"),
	DELETE("DELETE", "doDelete"),
	TRACE("TRACE", "doTrace"),
	CONNECT("CONNECT", "doConnect"),
	// This is a new method crucial for update support
	PATCH("PATCH", "doPatch"),
	;

	private String name;
	private String value;
	
	HTTPMethod(String name, String value)
	{
		this.name= name;
		this.value = value;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public String getValue() 
	{
		// TODO Auto-generated method stub
		return value;
	}
	
	public static HTTPMethod lookup(String match)
	{
		return SharedUtil.lookupEnum(values(), match);
	}
}
