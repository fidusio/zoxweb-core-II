package org.zoxweb;

import java.util.concurrent.Executor;
import java.util.logging.Logger;

import org.zoxweb.server.task.ExecutorHolder;
import org.zoxweb.server.task.ExecutorHolderManager;
import org.zoxweb.server.task.TaskProcessor;

public class ExecutorHolderTest 
{
	private static final transient Logger log = Logger.getLogger(ExecutorHolderTest.class.getName());

	@SuppressWarnings("unchecked")
	public static void main(String ...args)
	{
		try
		{
			ExecutorHolderManager.SINGLETON.createCachedThreadPool("marwan");
			log.info("" + ExecutorHolderManager.SINGLETON.size());
			ExecutorHolderManager.SINGLETON.createFixedThreadPool("nael", 5);
			log.info("" + ExecutorHolderManager.SINGLETON.size());
			
			ExecutorHolderManager.SINGLETON.createScheduledThreadPool("imad", 5);
			log.info("" + ExecutorHolderManager.SINGLETON.size());
			
			
			ExecutorHolder<Executor> ret = (ExecutorHolder<Executor>)ExecutorHolderManager.SINGLETON.register(new TaskProcessor(1000), "eerabi");
			
			log.info("" + ExecutorHolderManager.SINGLETON.size());
			ExecutorHolderManager.SINGLETON.terminate(ret.getName());
			log.info("" + ExecutorHolderManager.SINGLETON.size());
			
			
			
			
			ExecutorHolderManager.SINGLETON.close();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ExecutorHolderManager.SINGLETON.close();
		}
	}
}
