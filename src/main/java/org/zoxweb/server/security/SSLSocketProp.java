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
package org.zoxweb.server.security;

import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public interface SSLSocketProp
{
	
	/**
	 * Returns the SSLFactory
	 * 
	 * @return SSLSocketFactory
	 */
	public SSLSocketFactory getSSLFactory();
	
	
	/**
	 * Returns the HostnameVerifier
	 * 
	 * @return HostnameVerifier
	 */
	public HostnameVerifier getHostnameVerifier();
	
	
	/**
	 * Updates the URLConnection if it is a secure connection with getSSLFactory
	 *  
	 * @param con
	 */
	public void updateURLConnection(URLConnection con);

}