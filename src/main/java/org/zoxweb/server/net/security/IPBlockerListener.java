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
package org.zoxweb.server.net.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.zoxweb.server.io.FileMonitor;
import org.zoxweb.server.task.TaskEvent;
import org.zoxweb.server.task.TaskExecutor;
import org.zoxweb.server.task.TaskSchedulerProcessor;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.RuntimeUtil;
import org.zoxweb.shared.data.events.StringTokenEvent;
import org.zoxweb.shared.data.events.StringTokenListener;
import org.zoxweb.shared.data.events.TokenListenerManager;
import org.zoxweb.shared.security.IPBlockerConfig;
import org.zoxweb.shared.util.Const.TimeInMillis;
import org.zoxweb.shared.util.AppCreator;
import org.zoxweb.shared.util.AppointmentDefault;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class IPBlockerListener
        implements StringTokenListener, TaskExecutor
{
	class RemoteIPInfo
	{
		@Override
		public String toString() {
			return "RemoteIPInfo [remoteHost=" + remoteHost + ", detectionStartTime=" + detectionStartTime
					+ ", lastTimeDetected=" + lastTimeDetected + ", attackCount=" + attackCount + ", blocked=" + blocked
					+ ", attackRate=" + attackRate + " per min]";
		}
		String remoteHost = null;
		long detectionStartTime = 0;
		long lastTimeDetected = 0;
		long attackCount = 0;
		boolean blocked = false;
		float attackRate = 0;
	
		
	}
	
	
	
	public static class Creator
		implements AppCreator<IPBlockerListener, IPBlockerConfig>
	{
		private IPBlockerConfig ipBlockerConfig = null;
		
		@Override
		public void setAppConfig(IPBlockerConfig appConfig) {
			// TODO Auto-generated method stub
			ipBlockerConfig = appConfig;
		}

		@Override
		public IPBlockerConfig getAppConfig() {
			// TODO Auto-generated method stub
			return ipBlockerConfig;
		}

		@Override
		public IPBlockerListener createApp() throws NullPointerException, IllegalArgumentException, IOException {
			// TODO Auto-generated method stub
			TokenListenerManager tlm = new TokenListenerManager();
			
			IPBlockerListener ipbl = new IPBlockerListener(ipBlockerConfig.getAuthToken(),  ipBlockerConfig.getAuthValue(),  
					ipBlockerConfig.getCommand(),  ipBlockerConfig.getCommandToken(), 
										  ipBlockerConfig.getTriggerCount(), ipBlockerConfig.getRate(), TaskUtil.getDefaultTaskScheduler());
			tlm.addEventListener(ipbl);

			TaskUtil.getDefaultTaskScheduler().queue(new FileMonitor(ipBlockerConfig.getAuthFile(), tlm, true), new AppointmentDefault(TimeInMillis.MINUTE.MILLIS), ipbl);
			return ipbl;
		}
		
	}
	
	private static final transient Logger log = Logger.getLogger(IPBlockerListener.class.getName());
	
	
	private TaskSchedulerProcessor tsp = null;
	
	private String tokenMatch;
	

	private String parameterName;
	private String executionCommand;
	private String commandToken;
	private long minCount;
	private float rate;
	private long clearTimeout = TimeInMillis.MINUTE.MILLIS*10;
	
	private Map<String, RemoteIPInfo> ripiMap = new HashMap<String, RemoteIPInfo>();
	
	
	public IPBlockerListener()
	{
		
	}

	
	public IPBlockerListener(String tokenMatch, String parameterName, String executionCommand, String commandToken, long minCount, float rate , TaskSchedulerProcessor tsp)
	{
		this.tokenMatch = tokenMatch;
		this.parameterName = parameterName;
		this.executionCommand = executionCommand;
		this.commandToken = commandToken;
		this.minCount = minCount;
		this.rate = rate;
		this.tsp = tsp;
	}
	
	private TaskSchedulerProcessor getScheduler()
	{
		if (tsp != null)
			return tsp;
		
		return TaskUtil.getDefaultTaskScheduler();
	}
	
	
	public void clearTimeouts()
	{
		RemoteIPInfo all[] = ripiMap.values().toArray(new RemoteIPInfo[0]);
		for(RemoteIPInfo toCheck: all)
		{
			// removed temporarly
			//if(!toCheck.blocked)
			{
				if (System.currentTimeMillis() - toCheck.detectionStartTime > clearTimeout)
				{
					ripiMap.remove(toCheck.remoteHost);
					log.info(toCheck + " was removed from check list");
				}
			}
		}
	}
	
	@Override
	public void processStringTokenEvent(StringTokenEvent ste) 
	{
		String token = ste.getToken();
		long timeStamp = ste.getTimeStamp();
		
		
		if (SharedStringUtil.contains(token, tokenMatch, true))
		{
			List<NVPair> results = SharedUtil.toNVPairs(token, "=", " ");
			NVPair parameter = SharedUtil.lookup(results, parameterName);
			String value = SharedUtil.getValue(parameter);
			if (!SharedStringUtil.isEmpty(value))
			{
				value = value.toLowerCase();
				//log.info(timeStamp + " we have a match:" + value );
				RemoteIPInfo ripi = ripiMap.get(value);
				if (ripi == null)
				{
					ripi = new RemoteIPInfo();
					ripi.remoteHost = value;
					ripi.detectionStartTime = timeStamp;
					ripiMap.put(value, ripi);
				}
				ripi.lastTimeDetected = timeStamp;
				ripi.attackCount++;
				
				
				ripi.attackRate = ripi.lastTimeDetected > ripi.detectionStartTime ? ((ripi.attackCount*TimeInMillis.MINUTE.MILLIS) / ((ripi.lastTimeDetected - ripi.detectionStartTime))) : 0;
				
				log.info(ripi + " minCount: " + minCount + " rate: " + rate);
				
				if (ripi.attackCount > minCount && ripi.attackRate >= rate)
				{
					log.info("we must block:" + ripi);
					String command = SharedStringUtil.embedText(executionCommand, commandToken, value);
					log.info("we will execute:" + command);
					try 
					{
						if (!ripi.blocked)
						{
							RuntimeUtil.runAndFinish(command);
							ripi.blocked = true;
						}
					} 
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				
				
			}
		}

	}
	
	
	public String getTokenMatch()
	{
		return tokenMatch;
	}

	public void setTokenMatch(String tokenMatch)
	{
		this.tokenMatch = tokenMatch;
	}


	public String getParameterName() {
		return parameterName;
	}


	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}


	public String getExecutionCommand() {
		return executionCommand;
	}


	public void setExecutionCommand(String executionCommand) {
		this.executionCommand = executionCommand;
	}


	public String getCommandToken() {
		return commandToken;
	}


	public void setCommandToken(String commandToken) {
		this.commandToken = commandToken;
	}

	@Override
	public void executeTask(TaskEvent event) 
	{
		// TODO Auto-generated method stub
		clearTimeouts();
		getScheduler().queue(new AppointmentDefault(TimeInMillis.MINUTE.MILLIS), event);
		
	}


	@Override
	public void finishTask(TaskEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String ...args)
	{
		try
		{
			int index = 0;
		
			
			
			IPBlockerConfig ipbc = new IPBlockerConfig();
			ipbc.setAuthFile(args[index++]);
			ipbc.setAuthToken(args[index++]);
			ipbc.setAuthValue(args[index++]);
			ipbc.setCommand(args[index++]);
			ipbc.setCommandToken(args[index++]);
			ipbc.setTriggerCount(Long.parseLong(args[index++]));
			ipbc.setRate(Float.parseFloat(args[index++]));
			Creator c = new Creator();
			c.setAppConfig(ipbc);
			c.createApp();
			
			
//			TokenListenerManager tlm = new TokenListenerManager();
//			
//			IPBlockerListener ipbl = new IPBlockerListener(args[index++],  args[index++],  args[index++],  args[index++], 
//										  Long.parseLong(args[index++]), Float.parseFloat(args[index++]), TaskUtil.getDefaultTaskScheduler());
//			tlm.addEventListener(ipbl);
//
//			TaskUtil.getDefaultTaskScheduler().queue(new FileMonitor(file, tlm, true), new AppointmentDefault(TimeInMillis.MINUTE.MILLIS), ipbl);
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("IPBlocker fileToMonitor tokenMatch parameterName executionCommand commandToken");
		}
	}


	
}
