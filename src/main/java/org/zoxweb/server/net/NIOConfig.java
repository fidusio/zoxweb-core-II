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
package org.zoxweb.server.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.zoxweb.server.http.proxy.NIOProxyProtocol;
import org.zoxweb.server.http.proxy.NIOProxyProtocol.NIOProxyProtocolFactory;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.logging.LoggerUtil;
import org.zoxweb.server.net.NIOTunnel.NIOTunnelFactory;
import org.zoxweb.server.net.security.SSLSessionDataFactory;
import org.zoxweb.server.net.security.SecureNetworkTunnel;
import org.zoxweb.server.security.CryptoUtil;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.ConfigDAO;
import org.zoxweb.shared.net.InetSocketAddressDAO;
import org.zoxweb.shared.util.AppCreator;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.NVEntity;

/**
 * NIO config starts multi 
 * @author mnael
 *
 */
public class NIOConfig
implements Closeable,
		   AppCreator<NIOSocket, ConfigDAO>
{
	
	private ConfigDAO configDAO;
	private static final transient Logger log = Logger.getLogger(NIOConfig.class.getName());
	private List<Closeable> services = new ArrayList<Closeable>();

	
	
	public NIOConfig(String configDAOFile) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException
	{
		this((ConfigDAO)GSONUtil.fromJSON(IOUtil.inputStreamToString(configDAOFile)));
	}
	
	
	public NIOConfig(InputStream configDAOFile) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException
	{
		this((ConfigDAO)GSONUtil.fromJSON(IOUtil.inputStreamToString(configDAOFile, true)));
	}
	
	public NIOConfig(ConfigDAO configDAO)
	{
		setAppConfig(configDAO);
		//this.configDAO = parse(configDAO);
	}
	
	
	
	public NIOSocket createApp() throws IOException
	{
		NIOSocket ret = new NIOSocket(TaskUtil.getDefaultTaskProcessor());
		services.add(ret);
		
		for (NVEntity nve : configDAO.getContent().values())
		{
			// create the SSLEngine first
			// and attachments
			if (nve instanceof ConfigDAO)
			{
				ConfigDAO config = (ConfigDAO)nve;
				
				if (config.attachment() instanceof ProtocolSessionFactory)
				{
					ProtocolSessionFactory<?> psf = (ProtocolSessionFactory<?>) config.attachment();
					int port = config.getProperties().getValue("port");
					if (psf.getIncomingSSLSessionDataFactory() != null && psf instanceof NIOTunnelFactory)
					{
						log.info("Creating secure network tunnel:" + config.getProperties().get("port") + "," + ((NIOTunnelFactory)psf).getRemoteAddress() );
						// secure temporary fix since the NIO Secure Socket still not fully operational
						services.add(new SecureNetworkTunnel(psf.getIncomingSSLSessionDataFactory().getSSLContext().getServerSocketFactory(), 
											    port, 
											    ((NIOTunnelFactory)psf).getRemoteAddress()));
					}
					else
					{
						ServerSocketChannel ssc = ServerSocketChannel.open();
					
						ssc.bind(new InetSocketAddress(port));
	
						ret.addServerSocket(ssc, psf);
					}
				}
			}
		}
		
		
		return ret;
	}
	
	
	public static ConfigDAO parse(ConfigDAO configDAO)
	{
		ArrayValues<NVEntity> content = configDAO.getContent();
		
		
		// first pass
		for (NVEntity nve : content.values())
		{
			// create the SSLEngine first
			// and attachments
			if (nve instanceof ConfigDAO)
			{
				ConfigDAO config = (ConfigDAO)nve;
				
				if (config.getBeanClassName() != null)
				{
					try 
					{
						Object toAttach = Class.forName(config.getBeanClassName()).newInstance();
						
						if (toAttach instanceof SSLSessionDataFactory)
						{
							SSLContext sslc = CryptoUtil.initSSLContext((String)config.getProperties().getValue("keystore_file"), 
																		(String)config.getProperties().getValue("keystore_type"), 
																		((String)config.getProperties().getValue("keystore_password")).toCharArray(),  
																		config.getProperties().getValue("alias_password") != null ?  ((String)config.getProperties().getValue("alias_password")).toCharArray() : null, 
																		(String)config.getProperties().getValue("trustore_file"), 
																		config.getProperties().getValue("trustore_password") != null ?  ((String)config.getProperties().getValue("trustore_password")).toCharArray() : null);
							((SSLSessionDataFactory)toAttach).setSSLContext(sslc);
							
							((SSLSessionDataFactory)toAttach).setExecutor(TaskUtil.getDefaultTaskProcessor());
						}
							
							
						config.attach(toAttach);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
		
		
		for (NVEntity nve : content.values())
		{
			if (nve instanceof ConfigDAO)
			{
				ConfigDAO config = (ConfigDAO)nve;
				if (config.attachment() instanceof NIOTunnelFactory)
				{
					NIOTunnelFactory nioTF = (NIOTunnelFactory) config.attachment();
					try 
					{
						String remote_host = config.getProperties().getValue("remote_host");
						String ssl_engine = config.getProperties().getValue("ssl_engine");
						nioTF.setRemoteAddress(new InetSocketAddressDAO(remote_host));
						if (ssl_engine != null)
						{
							ConfigDAO factory = (ConfigDAO)configDAO.getContent().get(ssl_engine);
							if (factory != null)
								nioTF.setIncomingSSLSessionDataFactory((SSLSessionDataFactory) factory.attachment());
						}
						
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else if (config.attachment() instanceof NIOProxyProtocolFactory)
				{
					NIOProxyProtocolFactory nioPPF = (NIOProxyProtocolFactory) config.attachment();
					try 
					{
						InetFilterRulesManager incomingIFRM =  new InetFilterRulesManager();
						InetFilterRulesManager outgoingIFRM =  new InetFilterRulesManager();
						for (GetNameValue<?> gnv : config.getProperties().values())
						{
							if(gnv.getName().equals("incoming_inet_rule"))
							{
								if (gnv.getValue() != null)
								{
									incomingIFRM.addInetFilterProp((String)gnv.getValue());
								}
							}
							
							if(gnv.getName().equals("outgoing_inet_rule"))
							{
								if (gnv.getValue() != null)
								{
									outgoingIFRM.addInetFilterProp((String)gnv.getValue());
								}
							}
						}
						
						if (incomingIFRM.getAll().size() > 0)
						{
							nioPPF.setIncomingInetFilterRulesManager(incomingIFRM);
						}
						
						if (outgoingIFRM.getAll().size() > 0)
						{
							nioPPF.setOutgoingInetFilterRulesManager(outgoingIFRM);
						}
						
						
						nioPPF.setLogger(LoggerUtil.loggerToFile(NIOProxyProtocol.class.getName()+".proxy", (String)config.getProperties().getValue("log_file")));
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		
		return configDAO;
	}
	
	
	
	
	
	
	
	@SuppressWarnings("resource")
	public static void main(String ...args)
	{
		try
		{
			System.out.println("loading file " + args[0]);
			ConfigDAO configDAO = GSONUtil.fromJSON(IOUtil.inputStreamToString(args[0]));
			NIOConfig nioConfig = new NIOConfig(configDAO);
			nioConfig.createApp();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void close() throws IOException 
	{
		// TODO Auto-generated method stub
		for (Closeable c : services)
		{
			IOUtil.close(c);
		}
		
		
	}


	@Override
	public void setAppConfig(ConfigDAO appConfig) 
	{
		// TODO Auto-generated method stub
		this.configDAO = parse(appConfig);
	}


	@Override
	public ConfigDAO getAppConfig() {
		// TODO Auto-generated method stub
		return configDAO;
	}


	


	
}
