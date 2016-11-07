package org.zoxweb.server.net;


import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import java.util.logging.Logger;

import org.zoxweb.server.io.ByteBufferUtil;
import org.zoxweb.server.io.IOUtil;


import org.zoxweb.server.net.NIOSocket;

import org.zoxweb.server.net.ProtocolSessionFactory;
import org.zoxweb.server.net.ProtocolSessionProcessor;
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
		public NIOTunnelFactory(InetSocketAddressDAO remoteAddress)
		{
			this.remoteAddress = remoteAddress;
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
		
	}
	
//	
//	class ClosePolicy
//		implements AutoCloseable
//	{
//
//		private SourceOrigin source = SourceOrigin.UNKNOWN;
//		
//		public synchronized void updateOrigin(SourceOrigin origin)
//		{
//			if (SourceOrigin.UNKNOWN == source)
//			{
//				source = origin;
//			}
//		}
//		
//		
//		
//		@Override
//		public void close()
//		{
//			switch (source) 
//			{
//			case LOCAL:
//				// original disconnection was local
//				// we need to write data to remote 
//				
//				
//				break;
//			case REMOTE:
//				// original disonnection remote
//				break;
//			case UNKNOWN:
//				
//				break;
//			default:
//				break;
//			
//			}
//		}
//		
//	}
	
	
	
	
	
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
			if(clientChannel == null)
			{
				clientChannel = (SocketChannel)key.channel();
				clientChannelSK = key;
				
				remoteChannel = SocketChannel.open((new InetSocketAddress(remoteAddress.getInetAddress(), remoteAddress.getPort())));
				relay = new ChannelRelayTunnel(SourceOrigin.REMOTE, getReadBufferSize(), remoteChannel, clientChannel, clientChannelSK,  true,  getSelectorController());
				
				getSelectorController().register(NIOChannelCleaner.DEFAULT, remoteChannel, SelectionKey.OP_READ, relay, false);
			}
			
			
			int read = 0 ;
    		do
    		{
    			bBuffer.clear();
    			
    			read = ((SocketChannel)key.channel()).read(bBuffer);
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
				if (relay != null)
				{
//					try
//					{
//						relay.processRead(null);
//					}
//					catch(Exception e)
//					{
//						e.printStackTrace();
//					}
					relay.close();

				}
    			
    			close();
    				
    			if (debug)
    				log.info(key + ":" + key.isValid()+ " " + Thread.currentThread() + " " + TaskUtil.getDefaultTaskProcessor().availableExecutorThreads());
    					
    		}
    		
			
    	}
    	catch(Exception e)
    	{
    		if(debug)
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
			
			
			new NIOSocket(new NIOTunnelFactory(remoteAddress), new InetSocketAddress(port), null, null, TaskUtil.getDefaultTaskProcessor());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			TaskUtil.getDefaultTaskScheduler().close();
			TaskUtil.getDefaultTaskProcessor().close();
		}
	}

	
	
	
	
}
