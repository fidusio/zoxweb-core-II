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

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.net.NIOConfig;
import org.zoxweb.server.net.security.IPBlockerListener;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.ApplicationConfigManager;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.ApplicationConfigDAO;
import org.zoxweb.shared.data.ConfigDAO;
import org.zoxweb.shared.security.IPBlockerConfig;
import org.zoxweb.shared.data.ApplicationConfigDAO.ApplicationDefaultParam;

public class HTTPInitShutdownDefault
	implements ServletContextListener
{

	private static final transient Logger log = Logger.getLogger("");
	NIOConfig nioConfig = null;
	public void contextInitialized(ServletContextEvent event) 
	{
		try
		{
			log.info("" + ApplicationConfigManager.SINGLETON.loadDefault().getProperties());
			
			String filename = ApplicationConfigManager.SINGLETON.loadDefault().lookupValue(ApplicationDefaultParam.NIO_CONFIG);
			if (filename != null)
			{
				try
				{
					File file = ApplicationConfigManager.SINGLETON.locateFile(ApplicationConfigManager.SINGLETON.loadDefault(), filename);
					ConfigDAO configDAO = GSONUtil.fromJSON(IOUtil.inputStreamToString(new FileInputStream(file), true));
					log.info("" + configDAO);
					nioConfig = new NIOConfig(configDAO);
					nioConfig.createApp();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			
			
			filename = ApplicationConfigManager.SINGLETON.loadDefault().lookupValue("ip_blocker_config");
			if (filename != null)
			{
				try
				{
					File file = ApplicationConfigManager.SINGLETON.locateFile(ApplicationConfigManager.SINGLETON.loadDefault(), filename);
					IPBlockerConfig appConfig = GSONUtil.fromJSON(IOUtil.inputStreamToString(new FileInputStream(file), true), IPBlockerConfig.class);
					IPBlockerListener.Creator c = new IPBlockerListener.Creator();
					//log.info("\n" + GSONUtil.toJSON(appConfig, true, false, false));
					c.setAppConfig(appConfig);
					c.createApp();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch( Throwable t)
		{
			log.info("error loading default config " + t);
			
			
			try
			{
				ApplicationConfigManager.SINGLETON.save(new ApplicationConfigDAO());
			}
			catch(Exception e)
			{
				log.info("error saving default config " + e);
				//e.printStackTrace();
			}
		}
		log.info("init done");
		
	}
	
	
	
	public void contextDestroyed(ServletContextEvent event) 
	{
		log.info("destroy started");
		// TODO Auto-generated method stub
		IOUtil.close(nioConfig);
		TaskUtil.getDefaultTaskScheduler().close();
		TaskUtil.getDefaultTaskProcessor().close();
		log.info("destroy done");
	}
}