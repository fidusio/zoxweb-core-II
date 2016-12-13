/*
 * Copyright (c) 2012-2015 ZoxWeb.com LLC.
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

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public abstract class RunnableTask
implements TaskExecutor, Runnable
{

	static class RunnableTaskContainer extends RunnableTask 
	{

		@Override
		public void run() 
		{
			TaskEvent te = attachedEvent();
			((Runnable)te.getTaskExecutorParameters()[0]).run();
		}

	}
	
	
	protected TaskEvent te;
	
	/**
	 * @see org.zoxweb.server.task.TaskExecutor#executeTask(org.zoxweb.server.task.TaskEvent)
	 */
	@Override
	public void executeTask(TaskEvent event) 
	{
		this.te = event;
		run();
		
	}

	/**
	 * @see org.zoxweb.server.task.TaskExecutor#finishTask(org.zoxweb.server.task.TaskEvent)
	 */
	@Override
	public void finishTask(TaskEvent event) 
	{
		// TODO Auto-generated method stub
		
	}
	
	protected TaskEvent attachedEvent()
	{
		return te;
	}
	
	

}
