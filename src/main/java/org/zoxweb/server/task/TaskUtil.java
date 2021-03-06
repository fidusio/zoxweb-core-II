/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.server.task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;


import org.zoxweb.server.util.DefaultEvenManager;
import org.zoxweb.shared.data.events.BaseEventObject;
import org.zoxweb.shared.data.events.EventListenerManager;
import org.zoxweb.shared.util.Const;

public class TaskUtil
{
	private static TaskProcessor TASK_PROCESSOR = null;
	private static TaskSchedulerProcessor TASK_SCHEDULER = null;
	private static TaskSchedulerProcessor TASK_SIMPLE_SCHEDULER = null;
	private static EventListenerManager<BaseEventObject<?>,?> EV_MANAGER = null;
	private static final Lock LOCK = new ReentrantLock();
	
	private static int maxTasks = 500;
	private static int threadMultiplier = 4;
	private static int minTPThreadCount = 16;
	public static final long START_TIME_MILLIS = System.currentTimeMillis();
	
	public static transient final Logger LOG = Logger.getLogger(Const.LOGGER_NAME); 
	
	private TaskUtil() {
	}
	
	public static void setMaxTasksQueue(int taskQueueMaxSize) {
		if (TASK_PROCESSOR == null) {
			try {
				LOCK.lock();
				if (TASK_PROCESSOR == null && taskQueueMaxSize > 50) {
					maxTasks = taskQueueMaxSize;
				}
			} finally {
				LOCK.unlock();
			}
		}
	}

	public static void setThreadMultiplier(int multiplier) {
		if (TASK_PROCESSOR == null) {
			try {
				LOCK.lock();
				if (TASK_PROCESSOR == null && multiplier > 2) {
					threadMultiplier = multiplier;
				}
			} finally {
				LOCK.unlock();
			}
		}
	}


	public static void setMinTaskProcessorThreadCount(int minThreadCount){
		if (TASK_PROCESSOR == null) {
			try {
				LOCK.lock();
				if (TASK_PROCESSOR == null && minThreadCount > 2) {
					minTPThreadCount = minThreadCount;
				}
			} finally {
				LOCK.unlock();
			}
		}
	}

	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}


	public static TaskProcessor getDefaultTaskProcessor() {
		if (TASK_PROCESSOR == null) {
			try {
				LOCK.lock();
				if (TASK_PROCESSOR == null) {
					 int threadCount = Runtime.getRuntime().availableProcessors()*threadMultiplier;
					 if (threadCount < minTPThreadCount)
					 {
					 		threadCount = minTPThreadCount;
					 }
					 TASK_PROCESSOR = new TaskProcessor("DE",maxTasks, threadCount, Thread.NORM_PRIORITY, true);
				}
			} finally {
				LOCK.unlock();
			}
		}
		
		return TASK_PROCESSOR;
	}
	
	public static TaskSchedulerProcessor getDefaultTaskScheduler() {
		if (TASK_SCHEDULER == null) {
			try {
				LOCK.lock();
				
				if (TASK_SCHEDULER == null) {
					TASK_SCHEDULER = new TaskSchedulerProcessor(getDefaultTaskProcessor());
				}
			} finally {
				LOCK.unlock();
			}
		}
		
		return TASK_SCHEDULER;
	}

	public static EventListenerManager<BaseEventObject<?>,?> getDefaultEventManager()
	{
		if (EV_MANAGER == null) {
			try {
				LOCK.lock();

				if (EV_MANAGER == null) {
					EV_MANAGER = new DefaultEvenManager();
				}
			} finally {
				LOCK.unlock();
			}
		}

		return EV_MANAGER;
	}

	/**
	 * Return the default single threaded task scheduler
	 * @return
	 */
	public static TaskSchedulerProcessor getSimpleTaskScheduler() {
		if (TASK_SIMPLE_SCHEDULER == null) {
			try {
				LOCK.lock();

				if (TASK_SIMPLE_SCHEDULER == null) {
					TASK_SIMPLE_SCHEDULER = new TaskSchedulerProcessor();
				}
			} finally {
				LOCK.unlock();
			}
		}

		return TASK_SIMPLE_SCHEDULER;
	}
	
	public static boolean isBusy()
	{
	    return getDefaultTaskScheduler().pendingTasks() != 0 || getDefaultTaskProcessor().isBusy();
	}


	public static long waitIfBusyThenClose(long millisToSleepAndCheck)
	{
		if(millisToSleepAndCheck < 1)
			throw new IllegalArgumentException("wait time must be greater than 0 second.");
		if (TASK_SIMPLE_SCHEDULER != null)
		{
			do {
				try {
					Thread.sleep(millisToSleepAndCheck);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (TASK_SIMPLE_SCHEDULER.pendingTasks() != 0 );
			TASK_SIMPLE_SCHEDULER.close();
		}
		return waitIfBusyThenClose(getDefaultTaskProcessor(), getDefaultTaskScheduler(), millisToSleepAndCheck);
	}



	public static long waitIfBusyThenClose(TaskProcessor tp, TaskSchedulerProcessor tsp, long millisToSleepAndCheck)
	{
		if(millisToSleepAndCheck < 1)
			throw new IllegalArgumentException("wait time must be greater than 0 second.");
		do
		{
			try
			{
				Thread.sleep(millisToSleepAndCheck);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(tsp.pendingTasks() != 0 || tp.isBusy());

		long timestamp = System.currentTimeMillis();
		tsp.close();
		tp.close();


		return timestamp;
	}






	public static void close()
	{
		getDefaultTaskScheduler().close();
		getDefaultTaskProcessor().close();
		getSimpleTaskScheduler().close();
		getDefaultEventManager().close();
	}
	
}