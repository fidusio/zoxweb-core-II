package org.zoxweb.server.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;


import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.net.NIOConfig;
import org.zoxweb.server.net.security.IPBlockerListener;
import org.zoxweb.shared.data.ConfigDAO;
import org.zoxweb.shared.data.ApplicationConfigDAO.ApplicationDefaultParam;
import org.zoxweb.shared.security.IPBlockerConfig;
import org.zoxweb.shared.util.ResourceManager;

public class ServiceManager
	implements AutoCloseable
{
	public static final ServiceManager SINGLETON = new ServiceManager();
	
	private ServiceManager()
	{
		
	}
	
	private static final transient Logger log = Logger.getLogger(ServiceManager.class.getName());
	
	
	public static void close(Object obj)
	{
		if (obj instanceof AutoCloseable)
			IOUtil.close((AutoCloseable)obj);
	}
	
	public synchronized void loadServices() throws NullPointerException, IOException
	{
		String filename = ApplicationConfigManager.SINGLETON.loadDefault().lookupValue(ApplicationDefaultParam.NIO_CONFIG);
		if (filename != null)
		{
			if (ResourceManager.SINGLETON.lookup(NIOConfig.RESOURCE_NAME) != null)
			{
				close(ResourceManager.SINGLETON.lookup(NIOConfig.RESOURCE_NAME));
			}
			try
			{
				File file = ApplicationConfigManager.SINGLETON.locateFile(ApplicationConfigManager.SINGLETON.loadDefault(), filename);
				ConfigDAO configDAO = GSONUtil.fromJSON(IOUtil.inputStreamToString(new FileInputStream(file), true));
				log.info("" + configDAO);
				NIOConfig nioConfig = new NIOConfig(configDAO);
				nioConfig.createApp();
				ResourceManager.SINGLETON.map(NIOConfig.RESOURCE_NAME, nioConfig);
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
				if (ResourceManager.SINGLETON.lookup(IPBlockerListener.RESOURCE_NAME) != null)
				{
					close(ResourceManager.SINGLETON.lookup(IPBlockerListener.RESOURCE_NAME));
				}
				File file = ApplicationConfigManager.SINGLETON.locateFile(ApplicationConfigManager.SINGLETON.loadDefault(), filename);
				IPBlockerConfig appConfig = GSONUtil.fromJSON(IOUtil.inputStreamToString(new FileInputStream(file), true), IPBlockerConfig.class);
				IPBlockerListener.Creator c = new IPBlockerListener.Creator();
				//log.info("\n" + GSONUtil.toJSON(appConfig, true, false, false));
				c.setAppConfig(appConfig);
				IPBlockerListener ipBlocker = c.createApp();
				ResourceManager.SINGLETON.map(IPBlockerListener.RESOURCE_NAME, ipBlocker);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}


	public void close()
	{
		for(Object res : ResourceManager.SINGLETON.resources())
		{
			close(res);
		}
	}
}
