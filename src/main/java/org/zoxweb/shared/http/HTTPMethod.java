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

import org.zoxweb.shared.util.GetName;

/**
 * HTTP Protocol methods or actions RFC2616
 * @author naema01
 *
 */
public enum HTTPMethod
implements GetName
{
	
	OPTIONS("OPTIONS"),
	GET("GET"),
	HEAD("HEAD"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	TRACE("TRACE"),
	CONNECT("CONNECT"),
	// This is a new method crucial for update support
	PATCH("PATCH"),
	;

	private String name;
	
	HTTPMethod(String name)
	{
		this.name= name;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
}
