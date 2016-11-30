package org.zoxweb;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;


import org.zoxweb.server.crypto.CryptoUtil;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.net.NIOSocket;
import org.zoxweb.server.net.NIOTunnel.NIOTunnelFactory;


import org.zoxweb.server.net.security.SecureNetworkTunnel.KeyStoreConfig;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.net.InetSocketAddressDAO;

public class SecureNIOTunnelTest {
	
	private static transient final Logger log = Logger.getLogger(SecureNIOTunnelTest.class.getName());
	
	public static void main(String ...args)
	{
		try
		{
			int index = 0;
			String ksConfig = args[index++];
			KeyStoreConfig ksc = GSONUtil.create(false).fromJson(IOUtil.inputStreamToString(new FileInputStream(ksConfig), true), KeyStoreConfig.class);
			
			
			//System.setProperty("javax.net.debug","all");
			SSLContext sslc = CryptoUtil.initSSLContext(ksc.keystore_file, ksc.keystore_type, ksc.keystore_password.toCharArray(),  ksc.alias_password != null ?  ksc.alias_password.toCharArray() : null);
			
		
			
			@SuppressWarnings("resource")
			NIOSocket nios = new NIOSocket(null, null, null, null, TaskUtil.getDefaultTaskProcessor());
			for(; index < args.length; index++)
			{
				try
				{
					String parsed[] = args[index].split(",");
					int port = Integer.parseInt(parsed[0]);
					InetSocketAddressDAO remoteAddress = new InetSocketAddressDAO(parsed[1]);
					ServerSocketChannel ssc = ServerSocketChannel.open();
				
					ssc.bind(new InetSocketAddress(port));
					System.out.println("Adding:" + ssc + " " + remoteAddress);
					nios.addServerSocket(ssc, new NIOTunnelFactory(remoteAddress, sslc));
					//					
				}
				catch(Exception e)
				{
				
					e.printStackTrace();
				}
			}
			//nios.addServerSocket(new InetSocketAddress(8080), NIOProxyProtocol.FACTORY);
		
	
			//ss.setEnabledCipherSuites(new String[]{"TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"});
			//ss.setEnabledProtocols(new String[]{"TLSv1.2"});
		
			
			
			
		
		
			
			

			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("SecureNetworkTunnel keystoreJSONConfigFile localport,remoteHost:remotePort...");
			System.exit(0);
		}
	}


}
