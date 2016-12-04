package org.zoxweb.server.net;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

import org.zoxweb.server.io.ByteBufferUtil;

import org.zoxweb.server.net.security.SSLSessionData;
import org.zoxweb.server.task.RunnableTask;
import org.zoxweb.server.task.TaskEvent;
//import org.zoxweb.server.task.TaskEvent;
import org.zoxweb.shared.util.GetDescription;
import org.zoxweb.shared.util.GetName;

public abstract class ProtocolSessionProcessor
	extends RunnableTask
	implements GetName, GetDescription, Closeable
{

	protected volatile boolean selectable = true;
	private volatile SelectorController selectorController;
	private volatile InetFilterRulesManager inetFilterRulesManager;
	private volatile int defaultReadBufferSize = ByteBufferUtil.DEFAULT_BUFFER_SIZE;
	protected volatile ByteBuffer bBuffer = null;
	private volatile SSLSessionData outputSSLSessionData;
	private volatile SSLSessionData inputSSLSessionData;
	
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
	
	
	@Override
	public void finishTask(TaskEvent event) 
	{
		selectable = true;
	}
	
	public void run()
	{
		processRead((SelectionKey) getTaskEvent().getTaskExecutorParameters()[0]);
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
	 * @param selector the selector to set
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

	public InetFilterRulesManager getInetFilterRulesManager() 
	{
		return inetFilterRulesManager;
	}


	public void setInetFilterRulesManager(InetFilterRulesManager inetFilterRulesManager) 
	{
		this.inetFilterRulesManager = inetFilterRulesManager;
	}




	public SSLSessionData getOutputSSLSessionData() {
		return outputSSLSessionData;
	}



	public void setOutputSSLSessionData(SSLSessionData outputSSLSessionData) {
		this.outputSSLSessionData = outputSSLSessionData;
	}



	public SSLSessionData getInputSSLSessionData() {
		return inputSSLSessionData;
	}



	public void setInputSSLSessionData(SSLSessionData inputSSLSessionData) {
		this.inputSSLSessionData = inputSSLSessionData;
	}


}
