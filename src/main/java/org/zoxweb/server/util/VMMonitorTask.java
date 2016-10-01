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
package org.zoxweb.server.util;

import java.util.logging.Logger;

import org.zoxweb.server.task.TaskEvent;
import org.zoxweb.server.task.TaskExecutor;
import org.zoxweb.server.task.TaskSchedulerProcessor;
import org.zoxweb.shared.data.VMInfoDAO;
import org.zoxweb.shared.util.Appointment;
import org.zoxweb.shared.util.AppointmentDefault;
import org.zoxweb.shared.util.Const;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class VMMonitorTask
	implements TaskExecutor
{
	
	private Logger log;
	private Appointment appointment;
	private TaskSchedulerProcessor tsp;
	
	
	private Const.SizeInBytes sib = Const.SizeInBytes.B;
	
	public VMMonitorTask()
	{
		
	}
	
	public VMMonitorTask(Const.SizeInBytes sib)
	{
		if ( sib != null)
		{
			this.sib = sib;
		}
	}

	/**
	 * @see org.zoxweb.server.task.TaskExecutor#executeTask(org.zoxweb.server.task.TaskEvent)
	 */
	@Override
	public void executeTask(TaskEvent event) 
	{
		Object[] params = event.getTaskExecutorParameters();
		if(params != null)
		{
			int i = 0;
			if (params.length > i)
			{
				log = (Logger) params[i++];
				if (params.length > i)
				{
					appointment = (Appointment) params[i++];
					if (params.length > i)
					{
						tsp = (TaskSchedulerProcessor) params[i++];
					}
				}
			}
		}
		
		if (log != null)
		{
			VMInfoDAO vmid = RuntimeUtil.vmSnapshot();
			
			log.info("Values in:" + sib + ", Used mem:"+ (vmid.getUsedMemory()/sib.LENGTH) + ", Max-mem:" + (vmid.getMaxMemory()/sib.LENGTH) + 
					 ", Free-mem:"+ (vmid.getFreeMemory()/sib.LENGTH) + ", Total-mem:" + (vmid.getTotalMemory()/sib.LENGTH));
		}
		
		
	}

	/**
	 * @see org.zoxweb.server.task.TaskExecutor#finishTask(org.zoxweb.server.task.TaskEvent)
	 */
	@Override
	public void finishTask(TaskEvent event)
	{
		// TODO Auto-generated method stub
		if(tsp != null && appointment != null)
		{
			Appointment apt = new AppointmentDefault(appointment.getDelayInMillis());
			tsp.queue(this, apt, this, log, apt, tsp);
		}
	}

	

}
