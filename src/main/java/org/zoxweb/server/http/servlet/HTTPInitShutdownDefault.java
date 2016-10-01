package org.zoxweb.server.http.servlet;



import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



import org.zoxweb.server.util.ApplicationConfigManager;
import org.zoxweb.shared.data.ApplicationConfigDAO;




public class HTTPInitShutdownDefault implements ServletContextListener{

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