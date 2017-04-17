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

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.zoxweb.server.util.ApplicationConfigManager;
import org.zoxweb.shared.data.ApplicationConfigDAO;

public class HTTPInitShutdownDefault
	implements ServletContextListener
{

	private static final transient Logger log = Logger.getLogger("");
	
	public void contextInitialized(ServletContextEvent event) 
	{
		try
		{
			log.info( "" + ApplicationConfigManager.SINGLETON.loadDefault().getProperties());
			
		}
		catch( Throwable t)
		{
			log.info("error loading default config " + t);
			
			
			try
			{
				ApplicationConfigManager.SINGLETON.save( new ApplicationConfigDAO());
			}
			catch(Exception e)
			{
				log.info("error saving default config " + e);
				//e.printStackTrace();
			}
		}
		log.info( "init done");
		
	}
	
	
	
	public void contextDestroyed(ServletContextEvent event) 
	{
		// TODO Auto-generated method stub
	}
}