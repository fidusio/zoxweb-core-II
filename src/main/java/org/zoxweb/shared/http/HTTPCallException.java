/*
 * Copyright 2012 ZoxWeb.com LLC.
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

import java.io.IOException;

@SuppressWarnings("serial")
public class HTTPCallException 
extends IOException 
{
	
	
	private HTTPResponseData responseData;
	
	public HTTPCallException()
	{
	}
	
	
	public HTTPCallException(String reason)
	{
		this(reason, null);
	}
	
	public HTTPCallException(String reason, HTTPResponseData rd)
	{
		super( reason);
		responseData = rd;
	}
	
	@Override
	public String toString() {
		return super.toString()+"\n" + getResponseData();
	}


	public HTTPResponseData getResponseData() 
	{
		return responseData;
	}
}
