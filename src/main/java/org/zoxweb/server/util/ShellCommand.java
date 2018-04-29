package org.zoxweb.server.util;

import java.io.IOException;

import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.shared.data.RuntimeResultDAO;
import org.zoxweb.shared.util.Appointment;
import org.zoxweb.shared.util.AppointmentDefault;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.Const.TimeInMillis;
import org.zoxweb.shared.util.SharedStringUtil;

public class ShellCommand 
{
	
	static class RuntimeCommand
	implements Runnable
	{
		
		private int repeatCount;
		private long delay;
		private String command;
		private long executionCounter = 0;
		
		public RuntimeCommand(int repeatCount, long delay, String command)
		{
			SharedUtil.checkIfNulls("Command cannot be null", SharedStringUtil.trimOrNull(command));
			if (repeatCount < 0 || delay < 0)
			{
				throw new IllegalArgumentException("Invalid dealy or repeatCount value");
			}
				
			this.repeatCount = repeatCount;
			this.delay = delay;
			this.command = command;
			Appointment a = new AppointmentDefault(delay);
			TaskUtil.getDefaultTaskScheduler().queue(a, this);
			
		}
		

		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			executionCounter++;
			try 
			{
				RuntimeResultDAO rr = RuntimeUtil.runAndFinish(command);
				System.out.println(rr.getOutputData());
			} 
			catch (InterruptedException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			if (repeatCount == executionCounter)
			{
				System.exit(0);
			}
			Appointment a = new AppointmentDefault(delay);
			TaskUtil.getDefaultTaskScheduler().queue(a, this);
		}
		
	}
	
	
	public static void main(String ...args)
	{
		try
		{
		
			int repeatCount = 0;
			long delay = 0;
			String command = null;
			
			for (int i=0; i < args.length; i++)
			{
				String arg = args[i];
				switch(arg)
				{
				case "-r":
				case "-R":
					try
					{
						repeatCount = Integer.parseInt(args[i+1]);
						i++;
					}
					catch(NumberFormatException e)
					{
						repeatCount = 0;
					}
					break;
				case "-d":
				case "-D":
					delay = TimeInMillis.toMillis(args[++i]);
					break;
				default:
					command = arg;
				}
				if (command != null)
					break;
			}
			
			new RuntimeCommand(repeatCount, delay, command);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Usage: [-r counter] [-d delay_time] command");
		}
	}
}
