package org.zoxweb.server.task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import org.zoxweb.server.task.TaskProcessor;
import org.zoxweb.server.task.TaskSchedulerProcessor;
import org.zoxweb.shared.util.Const;

public class TaskUtil
{
	
	private static TaskProcessor TASK_PROCESSOR = null;
	private static TaskSchedulerProcessor TASK_SCHEDULER = null;
	private static final Lock LOCK = new ReentrantLock();
	
	private static int maxTasks = 500;
	private static int threadMultiplier = 8;
	
	public static transient final Logger LOG = Logger.getLogger(Const.LOGGER_NAME); 
	
	private TaskUtil()
	{
		
	}
	
	public static void setMaxTasksQueue(int taskQueueMaxSize)
	{
		if (TASK_PROCESSOR == null)
		{
			try
			{
				LOCK.lock();
				if (TASK_PROCESSOR == null && taskQueueMaxSize > 50)
				{
					maxTasks = taskQueueMaxSize;
				}
			}
			finally
			{
				LOCK.unlock();
			}
		}
	}
	
	
	public static void setThreadMultiplier(int multiplier)
	{
		if (TASK_PROCESSOR == null)
		{
			try
			{
				LOCK.lock();
				if (TASK_PROCESSOR == null && multiplier > 2)
				{
					threadMultiplier = multiplier;
				}
			}
			finally
			{
				LOCK.unlock();
			}
		}
	}
	
	
	
	public static TaskProcessor getDefaultTaskProcessor()
	{
		if (TASK_PROCESSOR == null)
		{
			try
			{
				LOCK.lock();
				if (TASK_PROCESSOR == null)
				{
					 TASK_PROCESSOR = new TaskProcessor(maxTasks, Runtime.getRuntime().availableProcessors()*threadMultiplier, Thread.NORM_PRIORITY, true);
				}
			}
			finally
			{
				LOCK.unlock();
			}
		}
		
		return TASK_PROCESSOR;
	}
	
	public static TaskSchedulerProcessor getDefaultTaskScheduler()
	{
		if (TASK_SCHEDULER == null)
		{
			try
			{
				LOCK.lock();
				
				if (TASK_SCHEDULER == null)
				{
					TASK_SCHEDULER = new TaskSchedulerProcessor(getDefaultTaskProcessor());
				}
			}
			finally
			{
				LOCK.unlock();
			}
		}
		
		return TASK_SCHEDULER;
	}
	
}