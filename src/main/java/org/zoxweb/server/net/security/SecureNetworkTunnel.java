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
	static class KeyStoreConfig
	{
		String keystore_file;
		String keystore_type;
		String keystore_password;
		String alias_password;
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
				
			
//			ServerSocketChannel ssc = ServerSocketChannel.open();
//			ssc.bind(new InetSocketAddress(port));
//			ThreadPoolExecutor sslThreadPool = new ThreadPoolExecutor(250, 2000, 25, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//			
//			SSLServerSocketChannel sslssc = new SSLServerSocketChannel(ssc, sslc, sslThreadPool, null);
//			
//			System.out.println(ssc);
//			//ss.setEnabledCipherSuites(new String[]{"TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"});
//			//ss.setEnabledProtocols(new String[]{"TLSv1.2"});
//			NIOSocket nios = new NIOSocket(null, null, null, null, TaskUtil.getDefaultTaskProcessor());
//			
//			
//			nios.addServerSocket(ssc, new NIOTunnelFactory(new InetSocketAddressDAO("zoxweb.com:80")));
			
			
//			ss.get
//			Socket s = ss.accept();
//			SocketChannel sc = s.getChannel();
//			cs.setOption(name, value)
//			InputStream is = s.getInputStream();
//			int count = 0;
//			while(is.read() != -1)
//			{
//				System.out.println("read count:" + count++);
//			}
//			Thread.sleep(5000);
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("SecureNetworkTunnel keystoreJSONConfigFile localport,remoteHost:remotePort...");
			System.exit(0);
		}
	}
	
}
