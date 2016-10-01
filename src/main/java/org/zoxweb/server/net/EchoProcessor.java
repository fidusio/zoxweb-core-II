package org.zoxweb.server.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import java.util.logging.Logger;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.task.TaskUtil;

import java.nio.channels.SocketChannel;

public class EchoProcessor 
extends ProtocolSessionProcessor 
{
	
	private static final transient Logger log = Logger.getLogger(EchoProcessor.class.getName());
	
	
	
	private ByteBuffer bBuffer = ByteBuffer.allocate(1024);
	private static long counter = 0;
	
	public static final ProtocolSessionFactory<EchoProcessor> FACTORY = new ProtocolSessionFactory<EchoProcessor>()
			{

				@Override
				public EchoProcessor newInstance() {
					// TODO Auto-generated method stub
					return new EchoProcessor();
				}
				public boolean isBlocking()
				{
					return false;
				}
		
			};
	
	
	private EchoProcessor()
	{
		counter++;
		log.info("Session:" + counter);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "echo";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Echo";
	}

	

	@Override
	protected void processRead(SelectionKey key) 
	{
    	try
    	{
    		int read;
    		bBuffer.clear();
    		do
    		{
    			
    			read = ((SocketChannel)key.channel()).read(bBuffer);
    			
    			
    		}while(read > 0);
    		
    		
    		if (bBuffer.position() > 0)
			{
    			log.info("read size:" + read);
    			ByteBuffer wBuffer = ByteBuffer.allocate(1024);
    			wBuffer.put("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=iso-8859-1\r\n\r\n<html><body><h1>Hello, World!</h1></body></html>\r\n\r\n".getBytes("UTF-8"));
    			
    			wBuffer.flip();
    			((SocketChannel)key.channel()).write(wBuffer);
    			
    			IOUtil.close(key.channel());
    			
			}
    		
	    	if (read == -1)
	    	{
	    		key.cancel();
	    		IOUtil.close(key.channel());
	    	}
    	}
    	catch(IOException e)
    	{
  
    		key.cancel();
    		IOUtil.close(key.channel());
    	}
    	
    	setSeletectable(true);
		
	}
	
	
	
	
	
	@SuppressWarnings("resource")
	public static void main(String ...args)
	{
		try
		{
			new NIOSocket(EchoProcessor.FACTORY, new InetSocketAddress(Integer.parseInt(args[0])), null, null, TaskUtil.getDefaultTaskProcessor());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public boolean isClosed() {
//		// TODO Auto-generated method stub
//		return false;
//	}
	
	public void postOp() 
	{
		// TODO Auto-generated method stub
		//ByteBufferUtil.cache(bBuffer);
	}

}
