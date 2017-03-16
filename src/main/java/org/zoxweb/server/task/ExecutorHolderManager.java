package org.zoxweb.server.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.zoxweb.shared.util.LifeCycleMonitor;


public class ExecutorHolderManager
	implements LifeCycleMonitor<ExecutorHolder<?>>,
			   AutoCloseable
{
	public static final ExecutorHolderManager SINGLETON = new ExecutorHolderManager();

	private Map<String, ExecutorHolder<?>> map = new HashMap<String, ExecutorHolder<?>>();
	private Lock lock = new ReentrantLock();
	
	private ExecutorHolderManager()
	{
		
	}
	
	
	public ExecutorService createCachedThreadPool(String name)
	{
		
		ExecutorService ret = null;
		try
		{
			ret = new ExecutorServiceHolder(Executors.newCachedThreadPool(), this, name, null);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			throw e;
		}
		
		
		return ret;
		
	}
	
	public Executor register(Executor exec, String name)
	{
		ExecutorHolder<Executor> ret = null;
		try
		{
			ret = new ExecutorHolder<Executor>(exec, this, name, null);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			throw e;
		}
		
		
		return ret;
	}
	
	public ExecutorService createFixedThreadPool(String name, int nThreads)
	{
		
		ExecutorServiceHolder ret = null;
		try
		{
			ret = new ExecutorServiceHolder(Executors.newFixedThreadPool(nThreads), this, name, null);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			throw e;
		}
		
		
		return ret;
		
	}
	
	public ScheduledExecutorService createScheduledThreadPool(String name, int nThreads)
	{
		
		ScheduledExecutorServiceHolder ret = null;
		try
		{
			ret = new ScheduledExecutorServiceHolder(Executors.newScheduledThreadPool(nThreads), this, name, null);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			throw e;
		}
		
		
		return ret;
		
	}
	
	
	
	public int size()
	{
		return map.size();
	}

	@Override
	public boolean created(ExecutorHolder<?> t) 
	{
		try
		{
			lock.lock();
			if (map.get(t.getName()) == null)
			{
				map.put(t.getName(), t);
				return true;
			}
		}
		finally
		{
			lock.unlock();
		}
		
		return false;
	
	}

	@Override
	public boolean terminated(ExecutorHolder<?> t) 
	{
		try
		{
			lock.lock();
			if (map.remove(t.getName()) != null)
			{	
				return true;
			}
		}
		finally
		{
			lock.unlock();
		}
		
		return false;
	}


	@Override
	public void close()
	{
		// TODO Auto-generated method stub
		try
		{
			lock.lock();
			for (ExecutorHolder<?>ev : map.values().toArray(new ExecutorHolder<?>[0]))
			{
				ev.close();
			}
		}
		
		finally
		{
			lock.unlock();
		}
	}
	
	
	
}
