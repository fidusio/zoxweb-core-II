package org.zoxweb;

import org.zoxweb.server.task.ExecutorHolderManager;
import org.zoxweb.server.task.TaskProcessor;

public class ExecutorHolderTest 
{

	public static void main(String ...args)
	{
		try
		{
			ExecutorHolderManager.SINGLETON.createCachedThreadPool("marwan");
			System.out.println(ExecutorHolderManager.SINGLETON.size());
			ExecutorHolderManager.SINGLETON.createFixedThreadPool("nael", 5);
			System.out.println(ExecutorHolderManager.SINGLETON.size());
			
			ExecutorHolderManager.SINGLETON.createScheduledThreadPool("imad", 5);
			System.out.println(ExecutorHolderManager.SINGLETON.size());
			
			ExecutorHolderManager.SINGLETON.register(new TaskProcessor(1000), "eerabi");
			
			System.out.println(ExecutorHolderManager.SINGLETON.size());
			
			ExecutorHolderManager.SINGLETON.close();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ExecutorHolderManager.SINGLETON.close();
		}
	}
}
