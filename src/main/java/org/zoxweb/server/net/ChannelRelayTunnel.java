package org.zoxweb.server.net;

import java.io.Closeable;
import java.io.IOException;


import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

import org.zoxweb.server.io.ByteBufferUtil;
import org.zoxweb.server.io.IOUtil;


public class  ChannelRelayTunnel
	extends ProtocolSessionProcessor
{

	private static boolean debug = false;
	private static final transient Logger log = Logger.getLogger(ChannelRelayTunnel.class.getName());
	
	
	private SocketChannel readSource;
	private SocketChannel writeDestination;
	private SelectionKey currentSK = null;
	private SelectionKey writeChannelSK;
	private final boolean autoCloseDesticantion;
	//private ByteBuffer bBuffer = null;
	private Closeable closeInterface = null;
	
	
	
	public ChannelRelayTunnel(int bufferSize, SocketChannel readSource, 
			  				  SocketChannel writeDestination, SelectionKey writeChannelSK, boolean autoCloseDesticantion,
			  				  SelectorController sc)
	{
		this(bufferSize, readSource, writeDestination, writeChannelSK, autoCloseDesticantion, sc, null);
	}
	
	
	
	public ChannelRelayTunnel(int bufferSize, SocketChannel readSource, 
							  SocketChannel writeDestination, SelectionKey writeChannelSK,boolean autoCloseDesticantion,
							  SelectorController sc, Closeable closeInterface)
	{
		bBuffer = ByteBufferUtil.allocateByteBuffer(bufferSize);
		this.readSource = readSource;
		this.writeDestination = writeDestination;
		this.writeChannelSK = writeChannelSK;
		this.autoCloseDesticantion = autoCloseDesticantion;
		this.closeInterface = closeInterface;
		setSelectorController(sc);
		
	}
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ChannelRelayTunnel";
	}
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "NIO Channel Relay Tunnel";
	}
	
	@Override
	public void close() throws IOException 
	{
		
		// TODO Auto-generated method stub
		IOUtil.close(readSource);
		getSelectorController().cancelSelectionKey(currentSK);
		IOUtil.close(closeInterface);
		if (autoCloseDesticantion && !writeChannelSK.isValid())
		{
			IOUtil.close(writeDestination);
		}
		postOp();
	}
	
	@Override
	public synchronized  void processRead(SelectionKey key)
	{
		try
		{
			
			if (key != null)
				currentSK = key;
			int read;
			do
			{
				bBuffer.clear();
				read = ((SocketChannel)currentSK.channel()).read(bBuffer);
				if (read > 0)
				{
					ByteBufferUtil.write(writeDestination, bBuffer);	
				}
			}while(read > 0);
			
	    	if(read == -1)
	    	{
	    		// we have to close 
	    		close();
	    		
	    	}
		}
		catch(Exception e)
		{
			
			if (debug)
			{
				log.info("error:" + key + ":" + writeChannelSK);
				e.printStackTrace();
			}
			IOUtil.close(this);
		}
		
		
		//setSeletectable(true);
		notifyAll();
		
	}
	
	
	
	
	public synchronized void waitThenStopReading()
	{
		
		while(!isSeletectable())
		{
			try 
			{
				wait(100);
				log.info("after wait");
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (currentSK != null)
			getSelectorController().cancelSelectionKey(currentSK);
		else
			IOUtil.close(this);
		
	}

}