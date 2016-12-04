package org.zoxweb.server.net;

import java.io.Closeable;
import java.io.IOException;


import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

import org.zoxweb.server.io.ByteBufferUtil;
import org.zoxweb.server.io.IOUtil;

import org.zoxweb.server.net.security.SSLSessionData;
import org.zoxweb.server.io.ByteBufferUtil.BufferType;
import org.zoxweb.shared.util.Const.SourceOrigin;


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
	private SourceOrigin origin = null;
	
	
	
	public ChannelRelayTunnel(SourceOrigin origin, int bufferSize, SocketChannel readSource, 
			  				  SocketChannel writeDestination, SelectionKey writeChannelSK, boolean autoCloseDesticantion,
			  				  SelectorController sc)
	{
		this(origin, bufferSize, readSource, writeDestination, writeChannelSK, autoCloseDesticantion, sc, null);
	}
	
	
	
	public ChannelRelayTunnel(SourceOrigin origin, int bufferSize, SocketChannel readSource, 
							  SocketChannel writeDestination, SelectionKey writeChannelSK,boolean autoCloseDesticantion,
							  SelectorController sc, Closeable closeInterface)
	{
		this.origin = origin;
		bBuffer = ByteBufferUtil.allocateByteBuffer(BufferType.HEAP, bufferSize);
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
	
	public SourceOrigin getSourceOrigin()
	{
		return origin;
	}
	
	@Override
	public void close() throws IOException 
	{
		
		// TODO Auto-generated method stub
		if (closeInterface != null)
		{
			IOUtil.close(closeInterface);
		}
		else
		{
			IOUtil.close(readSource);
			getSelectorController().cancelSelectionKey(currentSK);
			
			if (autoCloseDesticantion)
			{
				IOUtil.close(writeDestination);
			}
			postOp();
		}
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
				
				SSLSessionData outputSSLSessionData = getOutputSSLSessionData();
				if (read > 0)
				{
					if (outputSSLSessionData != null)
					{
						outputSSLSessionData.write(writeDestination, bBuffer, true);
						if (debug) log.info("Wrote Encrypted DATA !!!!!");
						
					}
					else
					{
						ByteBufferUtil.write(writeDestination, bBuffer);
						if (debug) log.info(ByteBufferUtil.toString(bBuffer));
					}
				}
			}while(read > 0);
			
	    	if(read < 0)
	    	{
	    		// we have to close 
	    		close();
	    		
	    	}
		}
		catch(Exception e)
		{
			
			//if (debug)
			{
				log.info("error:" + key + ":" + writeChannelSK + ":" +System.currentTimeMillis());
				e.printStackTrace();
			}
			IOUtil.close(this);
		}
		
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