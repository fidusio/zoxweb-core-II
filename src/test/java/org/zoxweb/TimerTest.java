package org.zoxweb;

import org.zoxweb.server.util.ServerUtil;

public class TimerTest 
{

	public static void runTest(long nanos)
	{
		long delta = System.nanoTime();
		
		
		
		long error = ServerUtil.delay(nanos);
		
		
		delta = System.nanoTime() - delta;
		
		System.out.println("Total time in nanos:" + delta + " requeted time:" + nanos + " error:" + (error));
	
	}
	
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		for (int i=0; i < 3; i++)
		for (String param : args)
		{
			try
			{
				runTest(Long.parseLong(param));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

}
