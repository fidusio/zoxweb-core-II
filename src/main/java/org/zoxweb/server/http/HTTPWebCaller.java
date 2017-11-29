package org.zoxweb.server.http;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import java.util.logging.Logger;

import org.zoxweb.server.http.HTTPCall;
import org.zoxweb.server.security.SSLCheckDisabler;
import org.zoxweb.server.task.RunnableTask;
import org.zoxweb.server.task.TaskEvent;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPResponseData;

public class HTTPWebCaller extends RunnableTask
{
	public static AtomicInteger TOTAL_COUNTER = new AtomicInteger();
	public static AtomicInteger SUCCESS_COUNTER = new AtomicInteger();
	public static AtomicInteger FAILED_COUNTER = new AtomicInteger();
	
	private static AtomicInteger counter = new AtomicInteger();
	
	
	public final int id = counter.incrementAndGet();
	private static final transient Logger log = Logger.getLogger(HTTPWebCaller.class.getName());
	private boolean logError;
	
	
	public HTTPWebCaller()
	{
		this(false);
	}
	
	public HTTPWebCaller(boolean logError)
	{
		this.logError = logError;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		TaskEvent event = attachedEvent();
		int index = 0;
		HTTPMessageConfigInterface hcc = (HTTPMessageConfigInterface) event.getTaskExecutorParameters()[index++];
		BiConsumer<HTTPMessageConfigInterface, HTTPResponseData> consumer = event.getTaskExecutorParameters().length > index ? (BiConsumer<HTTPMessageConfigInterface, HTTPResponseData>)event.getTaskExecutorParameters()[index++] : null;
		try
		{
			
			HTTPResponseData rd = new HTTPCall(hcc, SSLCheckDisabler.SINGLETON).sendRequest();
			log.info("[" + id +"]"+ rd.getStatus() + "," + rd.getData().length);
			if (consumer != null)
				consumer.accept(hcc, rd);
			SUCCESS_COUNTER.incrementAndGet();
		}
		catch(Exception e)
		{
			if (logError)
				e.printStackTrace();
			FAILED_COUNTER.incrementAndGet();
			log.info("[" + id +"] failed " + e);
		}
		
		
		TOTAL_COUNTER.incrementAndGet();
	}
	
	
	
	public static void waitFor(int count)
	{
		do
		{
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while (HTTPWebCaller.TOTAL_COUNTER.get() != count);
	}
	
	
	
	public static String stats()
	{
		return "Total: " +TOTAL_COUNTER.get() + " Successful: " + SUCCESS_COUNTER.get() + " Failed: " + FAILED_COUNTER.get();
	}
}