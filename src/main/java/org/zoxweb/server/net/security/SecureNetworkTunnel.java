package org.zoxweb.server.net.security;

import java.io.FileInputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

import org.zoxweb.server.crypto.CryptoUtil;
import org.zoxweb.server.io.IOUtil;

import org.zoxweb.server.net.NetworkTunnel;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.net.InetSocketAddressDAO;


public class SecureNetworkTunnel
implements Runnable
{
	private static transient final Logger log = Logger.getLogger(SecureNetworkTunnel.class.getName());
	/*
	 * json file format
	   {
			keystore_file: "file location",
			keystore_type: "jks",
			keystore_password: "passwd",
			alias_password: "passwd"
	   }
	 */
	public static class KeyStoreConfig
	{
		public String keystore_file;
		public String keystore_type;
		public String keystore_password;
		public String alias_password;
	}
	
	
	
	private ServerSocket ss;
	private InetSocketAddressDAO remoteSocketAddress;
	public SecureNetworkTunnel(SSLServerSocketFactory sslssf, int localPort, InetSocketAddressDAO remoteAddress) throws IOException
	{
		ss = sslssf.createServerSocket(localPort);
		remoteSocketAddress = remoteAddress;
		new Thread(this).start();
	}
	
	
	@SuppressWarnings("resource")
	@Override
	public void run() 
	{
		try
		{
			log.info("secure socket started " + ss.getLocalPort() + " " + remoteSocketAddress);
			while (!ss.isClosed())
			{
				Socket sIn = ss.accept();
				Socket sRemote = new Socket(remoteSocketAddress.getInetAddress(), remoteSocketAddress.getPort());
				new NetworkTunnel(sIn, sRemote);
			}
		}
		catch(Exception e)
		{
			IOUtil.close(ss);
		}
	}
	
	
	public static void main(String ...args)
	{
		try
		{
			int index = 0;
			String ksConfig = args[index++];
			KeyStoreConfig ksc = GSONUtil.create(false).fromJson(IOUtil.inputStreamToString(new FileInputStream(ksConfig), true), KeyStoreConfig.class);
			
			
			//System.setProperty("javax.net.debug","all");
			SSLContext sslc = CryptoUtil.initSSLContext(ksc.keystore_file, ksc.keystore_type, ksc.keystore_password.toCharArray(),  ksc.alias_password != null ?  ksc.alias_password.toCharArray() : null);
			SSLServerSocketFactory sslssf = sslc.getServerSocketFactory();
			
			
//			@SuppressWarnings("resource")
//			NIOSocket nios = new NIOSocket(null, null, null, null, TaskUtil.getDefaultTaskProcessor());
//			for(; index < args.length; index++)
//			{
//				try
//				{
//					String parsed[] = args[index].split(",");
//					int port = Integer.parseInt(parsed[0]);
//					InetSocketAddressDAO remoteAddress = new InetSocketAddressDAO(parsed[1]);
//					ServerSocketChannel ssc = ServerSocketChannel.open();
//					SSLServerSocketChannel sslssc = new SSLServerSocketChannel(ssc, sslc, log);
//					sslssc.bind(new InetSocketAddress(port));
//					System.out.println("Adding:" + sslssc + " " + remoteAddress);
//					nios.addServerSocket(sslssc, new NIOTunnelFactory(remoteAddress));
//					//					
//				}
//				catch(Exception e)
//				{
//				
//					e.printStackTrace();
//				}
//			}
			
			
			
			for(; index < args.length; index++)
			{
				try
				{
					String parsed[] = args[index].split(",");
					int port = Integer.parseInt(parsed[0]);
					InetSocketAddressDAO remoteAddress = new InetSocketAddressDAO(parsed[1]);
					new SecureNetworkTunnel(sslssf, port, remoteAddress);
					
				}
				catch(Exception e)
				{
					log.info("Failed to start " + args[index]);
					e.printStackTrace();
				}
			}
				
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("SecureNetworkTunnel keystoreJSONConfigFile localport,remoteHost:remotePort...");
			System.exit(0);
		}
	}
	
}
