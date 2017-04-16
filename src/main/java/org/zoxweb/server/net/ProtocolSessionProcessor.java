package org.zoxweb.server.net;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import org.zoxweb.server.io.ByteBufferUtil;

import org.zoxweb.server.net.security.SSLSessionData;

import org.zoxweb.shared.util.GetDescription;
import org.zoxweb.shared.util.GetName;

public abstract class ProtocolSessionProcessor
	implements GetName, GetDescription, Closeable, Runnable
{

	protected volatile boolean selectable = true;
	private volatile SelectorController selectorController;
	private volatile InetFilterRulesManager outgoingInetFilterRulesManager;
	private volatile int defaultReadBufferSize = ByteBufferUtil.DEFAULT_BUFFER_SIZE;
	protected volatile ByteBuffer bBuffer = null;
	private volatile SSLSessionData outputSSLSessionData;
	private volatile SSLSessionData inputSSLSessionData;
	private volatile SelectionKey attachement;
	
	protected ProtocolSessionProcessor()
	{
		
	}
	
	public int getReadBufferSize()
	{
		return defaultReadBufferSize;
	}
	
	public synchronized void setReadBufferSize(int size)
	{
		if (size < 512)
			throw new IllegalArgumentException("Invalid size " + size + " min allowed size 512 bytes");
		defaultReadBufferSize = size;
	}
	
	public boolean isSeletectable()
	{
		return selectable;
	}
	
	
	protected void setSeletectable(boolean stat)
	{
		selectable = stat;
	}
	
	

	
	public synchronized void attach(SelectionKey sk)
	{
		attachement = sk;
	}
	
	protected synchronized SelectionKey detach()
	{
		SelectionKey ret = attachement;
		attachement = null;
		return ret;
	}
	
	public void run()
	{
		try
		{
			processRead(detach());
		}
		catch(Exception e)
		{
			
		}
		// very crucial be set to true after the processRead call
		selectable = true;
	}
	
	
	protected abstract void processRead(SelectionKey sk);


	/**
	 * @return the selector
	 */
	public SelectorController getSelectorController() 
	{
		return selectorController;
	}


	/**
	 * @param selectorController the selector to set
	 */
	public void setSelectorController(SelectorController selectorController) 
	{
		this.selectorController = selectorController;
	}
	
	
	public synchronized void postOp()
	{
		if (bBuffer != null)
		{
			ByteBufferUtil.cache(bBuffer);
			//bBuffer = null;
		}
	}

	public InetFilterRulesManager getOutgoingInetFilterRulesManager() 
	{
		return outgoingInetFilterRulesManager;
	}


	public void setOutgoingInetFilterRulesManager(InetFilterRulesManager inetFilterRulesManager) 
	{
		this.outgoingInetFilterRulesManager = inetFilterRulesManager;
	}




	public SSLSessionData getOutputSSLSessionData()
	{
		return outputSSLSessionData;
	}



	public void setOutputSSLSessionData(SSLSessionData outputSSLSessionData)
	{
		this.outputSSLSessionData = outputSSLSessionData;
	}



	public SSLSessionData getInputSSLSessionData()
	{
		return inputSSLSessionData;
	}



	public void setInputSSLSessionData(SSLSessionData inputSSLSessionData)
	{
		this.inputSSLSessionData = inputSSLSessionData;
	}


}
