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

import org.zoxweb.shared.util.Const;

public class TaskUtil
{
	private static TaskProcessor TASK_PROCESSOR = null;
	private static TaskSchedulerProcessor TASK_SCHEDULER = null;
	private static final Lock LOCK = new ReentrantLock();
	
	private static int maxTasks = 500;
	private static int threadMultiplier = 4;
	
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

	public static TaskProcessor getDefaultTaskProcessor() {
		if (TASK_PROCESSOR == null) {
			try {
				LOCK.lock();
				if (TASK_PROCESSOR == null) {
					 TASK_PROCESSOR = new TaskProcessor(maxTasks, Runtime.getRuntime().availableProcessors()*threadMultiplier, Thread.NORM_PRIORITY, true);
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
	
}