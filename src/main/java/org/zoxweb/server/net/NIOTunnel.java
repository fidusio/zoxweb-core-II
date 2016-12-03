package org.zoxweb.server.net;


import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.zoxweb.server.io.ByteBufferUtil;
import org.zoxweb.server.io.IOUtil;


import org.zoxweb.server.net.NIOSocket;

import org.zoxweb.server.net.ProtocolSessionFactory;
import org.zoxweb.server.net.ProtocolSessionProcessor;

import org.zoxweb.server.net.security.SSLSessionData;
import org.zoxweb.server.net.security.SSLUtil;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.shared.net.InetSocketAddressDAO;
import org.zoxweb.shared.util.Const.SourceOrigin;


public class NIOTunnel 
extends ProtocolSessionProcessor
{
	private static boolean debug = false;
	private static final transient Logger log = Logger.getLogger(NIOTunnel.class.getName());
	
	
	public static class NIOTunnelFactory
	implements ProtocolSessionFactory<NIOTunnel>
	{
		
		final private InetSocketAddressDAO remoteAddress;
		final private SSLUtil sslUtil;
		
		
		public NIOTunnelFactory(InetSocketAddressDAO remoteAddress, SSLContext sslContext)
		{
			this(remoteAddress, new SSLUtil(sslContext));	
		}
		
		
		public NIOTunnelFactory(InetSocketAddressDAO remoteAddress, SSLUtil sslUtil)
		{
			this.remoteAddress = remoteAddress;
			this.sslUtil = sslUtil;
			
		}

		@Override
		public NIOTunnel newInstance() 
		{
			// TODO Auto-generated method stub
			return new NIOTunnel(remoteAddress);
		}

		@Override
		public boolean isBlocking() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public SSLUtil getSSLUtil() {
			// TODO Auto-generated method stub
			return sslUtil;
		}

	
		
	}
	

	private SocketChannel remoteChannel = null;
	
	private SocketChannel clientChannel = null;
	private SelectionKey  clientChannelSK = null;
	
	private ChannelRelayTunnel relay = null;
	
	final private InetSocketAddressDAO remoteAddress;
	public NIOTunnel(InetSocketAddressDAO remoteAddress)
	{
		this.remoteAddress = remoteAddress;
		bBuffer = ByteBuffer.allocate(getReadBufferSize());
	}
	
	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return "NIOTunnel";
	}

	@Override
	public String getDescription() 
	{
		// TODO Auto-generated method stub
		return "NIO Tunnel";
	}

	@Override
	public void close() throws IOException
	{
		
		// TODO Auto-generated method stub
		getSelectorController().cancelSelectionKey(clientChannelSK);
		IOUtil.close(remoteChannel);
		IOUtil.close(clientChannel);
		postOp();
		log.info("close ended");
	}
	


	@Override
	protected void processRead(SelectionKey key) 
	{
		
		
		try
    	{
			SSLSessionData sslSessionData = ((ProtocolSessionProcessor)key.attachment()).getInputSSLSessionData();
			if(clientChannel == null)
			{
				clientChannel = (SocketChannel)key.channel();
				clientChannelSK = key;
				
				remoteChannel = SocketChannel.open((new InetSocketAddress(remoteAddress.getInetAddress(), remoteAddress.getPort())));
				relay = new ChannelRelayTunnel(SourceOrigin.REMOTE, getReadBufferSize(), remoteChannel, clientChannel, clientChannelSK,  true,  getSelectorController());
				relay.setOutputSSLSessionData(sslSessionData);
				getSelectorController().register(NIOChannelCleaner.DEFAULT, remoteChannel, SelectionKey.OP_READ, relay, false);
			}
			
			
			int read = 0 ;
    		do
    		{
    			bBuffer.clear();
    			
    			if (sslSessionData != null)
    			{
    				// ssl mode
    				read = sslSessionData.read(((SocketChannel)key.channel()), bBuffer, false);
    			}
    			else
    			{
    				read = ((SocketChannel)key.channel()).read(bBuffer);
    			}
    			if (read > 0)
    			{
    				ByteBufferUtil.write(remoteChannel, bBuffer);
    			}
    		}
    		while(read > 0);
    		
    		if (read == -1)
    		{
    			if(debug)
    				log.info("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+Read:" + read);
    			
    			getSelectorController().cancelSelectionKey(key);
    			IOUtil.close(relay);	
    			close();
    				
    			if (debug)
    				log.info(key + ":" + key.isValid()+ " " + Thread.currentThread() + " " + TaskUtil.getDefaultTaskProcessor().availableExecutorThreads());		
    		}
    	}
    	catch(Exception e)
    	{
    		//if(debug)
    			e.printStackTrace();
    		IOUtil.close(this);
    		if(debug)
    			log.info(System.currentTimeMillis() + ":Connection end " + key + ":" + key.isValid()+ " " + Thread.currentThread() + " " + TaskUtil.getDefaultTaskProcessor().availableExecutorThreads());
    		
    	}
		finally
		{
			//setSeletectable(true);
		}

	}

	

	
	
	
	
	
	
	
	


	
		
	
	
	
		
	
	
	

	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("resource")
	public static void main(String ...args)
	{
		try
		{
			
			int index = 0;
			int port = Integer.parseInt(args[index++]);
			InetSocketAddressDAO remoteAddress = new InetSocketAddressDAO(args[index++]);
			TaskUtil.setThreadMultiplier(4);
			
			
			new NIOSocket(new NIOTunnelFactory(remoteAddress, (SSLUtil)null), new InetSocketAddress(port), null, null, TaskUtil.getDefaultTaskProcessor());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			TaskUtil.getDefaultTaskScheduler().close();
			TaskUtil.getDefaultTaskProcessor().close();
		}
	}

	
	
	
	
}
