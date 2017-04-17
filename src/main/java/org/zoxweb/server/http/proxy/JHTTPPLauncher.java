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
package org.zoxweb.server.http.proxy;

public class JHTTPPLauncher 
{
	private static void error( String msg)
	{
		if (msg != null)
		{
			System.err.println("Error:" + msg);
		}
		
		System.err.println("usage: JHTTPP2Launcher [proxy port, default 8080] [ProxyRules.json]");
	}
	
	
	public static void main(String[] args) 
	{
		
		int port = 8080;
		String fileName = null;
		
		try
		{
			int index = 0;
			if ( args.length > 0)
			{
				port = Integer.parseInt(args[index++]);
			}
			if (args.length > 1)
			{
				fileName = args[index++];
			}
			JHTTPPUtil.proxySetup(port, fileName);
			//new JHTTPPServer(port, null);
		}
		catch( Exception e)
		{
			e.printStackTrace();
			error(null);
		}
	}
}
