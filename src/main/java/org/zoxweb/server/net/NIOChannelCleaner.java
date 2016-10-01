package org.zoxweb.server.net;

import java.nio.channels.SelectionKey;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.task.RunnableTask;
import org.zoxweb.server.task.TaskSchedulerProcessor;
import org.zoxweb.server.task.TaskSchedulerProcessor.TaskSchedulerAppointment;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.shared.util.AppointmentDefault;
import org.zoxweb.shared.util.Const.TimeInMillis;

public class NIOChannelCleaner
	extends RunnableTask
{

	private static final transient Logger log = Logger.getLogger(NIOChannelCleaner.class.getName());
	private Set<SelectionKey> set = new HashSet<SelectionKey>();
	private long sleepTime = -1;

	private TaskSchedulerAppointment tsa = null;
	private long runCall = 0;
	
	
	public static final NIOChannelCleaner DEFAULT = new NIOChannelCleaner(TaskUtil.getDefaultTaskScheduler(), TimeInMillis.SECOND.MILLIS*10);
	
	public NIOChannelCleaner(TaskSchedulerProcessor tsp, long sleepTime)
	{
		this.sleepTime = sleepTime;
		tsa = tsp.queue(this, new AppointmentDefault(sleepTime), this);
		log.info("started");
	}
	
	@Override
	public void run() 
	{
		runCall++;
		//int totalPurged = 
				purge();
		
		// wait again
		tsa.setDelayInMillis(sleepTime);
		if(runCall % 4 == 0)
		{
			//System.gc1();
			//log.info("total purged:" + totalPurged + " total pending:" + set.size() + " runCall:" + runCall + " availableThread:" + TaskUtil.getDefaultTaskProcessor().availableExecutorThreads());
		}
	}
	
	
	public SelectionKey add(SelectionKey sk)
	{
		if (sk != null)
		{
			synchronized(set)
			{
				if (set.add(sk))
				{
					return sk;
				}
			}
		}
		
		return null;
	}
	
	public SelectionKey remove(SelectionKey sk)
	{
		if (sk != null)
		{
			synchronized(set)
			{
				if (set.remove(sk))
				{
					sk.attach(null);
					return sk;
				}
			}
		}
		
		return null;
	}
	
	protected synchronized int purge()
	{
		SelectionKey toCheck[] = null;
		synchronized(set)
		{
			toCheck = set.toArray(new SelectionKey[set.size()]);
		}
		int counter = 0;
		for (SelectionKey sk : toCheck)
		{
			if (!sk.isValid() || !sk.channel().isOpen())
			{
				try
				{
					IOUtil.close(sk.channel());
					if (sk.attachment() != null && sk.attachment() instanceof ProtocolSessionProcessor)
					{
						((ProtocolSessionProcessor)sk.attachment()).postOp();
						//IOUtil.close((AutoCloseable)sk.attachment());
					}
						
					if (remove(sk) != null)
					{
						counter++;
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	
		return counter;
	}

}
