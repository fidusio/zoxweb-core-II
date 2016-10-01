package org.zoxweb.server.net.security;


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
import org.zoxweb.shared.util.Const.TimeInMillis;

import org.zoxweb.shared.util.AppointmentDefault;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;



public class IPBlockerListener implements StringTokenListener, TaskExecutor
{
	class RemoteIPInfo
	{
		@Override
		public String toString() {
			return "RemoteIPInfo [remoteHost=" + remoteHost + ", detectionStartTime=" + detectionStartTime
					+ ", lastTimeDeteted=" + lastTimeDeteted + ", attackCount=" + attackCount + ", blocked=" + blocked
					+ ", attackRate=" + attackRate + "]";
		}
		String remoteHost = null;
		long detectionStartTime = 0;
		long lastTimeDeteted = 0;
		long attackCount = 0;
		boolean blocked = false;
		float attackRate = 0;
	
		
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
				log.info(timeStamp + " we have a match:" + value );
				RemoteIPInfo ripi = ripiMap.get(value);
				if (ripi == null)
				{
					ripi = new RemoteIPInfo();
					ripi.remoteHost = value;
					ripi.detectionStartTime = timeStamp;
					ripiMap.put(value, ripi);
				}
				ripi.lastTimeDeteted = timeStamp;
				ripi.attackCount++;
				ripi.attackRate = ((ripi.lastTimeDeteted - ripi.detectionStartTime) / ripi.attackCount) * 0.001F;
				
				
				if (ripi.attackCount > minCount && ripi.attackRate < rate)
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
			String file = args[index++];
			TokenListenerManager tlm = new TokenListenerManager();
			
			
			TaskSchedulerProcessor tsp = new TaskSchedulerProcessor();
			
			
			IPBlockerListener ipbl = new IPBlockerListener( args[index++],  args[index++],  args[index++],  args[index++], 
										  Long.parseLong(args[index++]), Float.parseFloat(args[index++]), tsp);
			tlm.addEventListener(ipbl);

			tsp.queue(new FileMonitor(file, tlm, true), new AppointmentDefault(TimeInMillis.MINUTE.MILLIS), ipbl);
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("IPBlocker fileToMonitor tokenMatch parameterName executionCommand commandToken");
		}
	}


	
}
