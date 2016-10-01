/*
 * Copyright (c) 2012-2014 ZoxWeb.com LLC.
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
import java.util.EventObject;

import org.zoxweb.shared.util.ReferenceID;


/**
 * This event is used to encapsulate the content of a Task Execution, it is mainly used by the TaskProcessor object.
 * @author mnael
 *
 */
@SuppressWarnings("serial")
public class TaskEvent
extends EventObject
implements ReferenceID<String>
{
	private final TaskExecutor te;
	private final Object[] params;
	private Object executionResult = null;
	private String refID = null;
	

	
	
	
	/**
	 * Create a task event
	 * @param source generating the event
	 * @param te the implementation that will execute the event
	 * @param tel the implementation that will be notified after executing the task 
	 * @param taskExecutorparams the task executor parameters
	 * @param id is a reference identifier
	 */
	public TaskEvent(Object source, TaskExecutor te, Object... taskExecutorparams)
	{
		super(source);
		this.te = te;
		this.params = taskExecutorparams;
		
	}
	
	/**
	 * Return the TaskExecutor object, this method is used exclusively by the ExecutorThread to  
	 * execute of the task.
	 * @return
	 */
	protected TaskExecutor getTaskExecutor()
	{
		return te;
	}
	
	
	/**
	 * This method return the parameters that will used inside the TaskExecutor.executeTask() method
	 * @return
	 */
	public Object[] getTaskExecutorParameters()
	{
		return params;
	}
	
	/**
	 * This method will return the result of the execution.
	 * @return
	 */
	public Object getExecutionResult() 
	{
		return executionResult;
	}

	/**
	 * This method sets the result of the execution it could be exception or return object,
	 * it is up to the TaskExecutor.executeTask() implementation to set this value or not and 
	 * the interpretation of the result is up to the TaskTerminationListener.taskFinished() method to interpret
	 * @param executionResult
	 */
	public void setExecutionResult(Object executionResult) 
	{
		this.executionResult = executionResult;
	}
	
	/**
	 * This is custom reference id that context dependent
	 * @return string id
	 */
	public String getReferenceID() 
	{
		return refID;
	}

	/**
	 * Gets the custom id 
	 * @param customRef
	 */
	public void setReferenceID(String customRef) 
	{
		this.refID = customRef;
	}	
}
