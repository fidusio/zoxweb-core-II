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
import org.zoxweb.shared.util.SharedUtil;

/**
 * 
 * @author mnael
 *
 */
public class ExecutorHolderManager
	implements LifeCycleMonitor<ExecutorHolder<?>>,
			   AutoCloseable
{
	public static final ExecutorHolderManager SINGLETON = new ExecutorHolderManager();

	volatile private Map<String, ExecutorHolder<?>> map = new HashMap<String, ExecutorHolder<?>>();
	volatile private Lock lock = new ReentrantLock();
	
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
	
	/**
	 * Register an Executor by creating an ExecutorHolder
	 * @param exec java executor to be register mandatory
	 * @param name of the executor if null an automatic UUID name will be generated
	 * @return ExecutorHolder base class
	 * @throws NullPointerException if es or lcm null
	 * @throws IllegalArgumentException if name of executor already exist or  Executor instance of ExecutorHolder
	 */
	@SuppressWarnings("unchecked")
	public <T extends Executor> T register(Executor exec, String name)
		throws NullPointerException, IllegalArgumentException
	{
		
		SharedUtil.checkIfNulls("Executor cannot be null", exec);
		if (exec instanceof ExecutorHolder)
		{
			throw new IllegalArgumentException("Cannot resigter an ExecutorHolder: " + exec);
		}
		T ret = null;
		try
		{
			
			// do not change sequence because of inheritance
			if (exec instanceof ScheduledExecutorService)
				ret = (T) new ScheduledExecutorServiceHolder((ScheduledExecutorService)exec, this, name, null);
			else if (exec instanceof ExecutorService)
				ret = (T) new ExecutorServiceHolder((ExecutorService)exec, this, name, null);
			else	
				ret = (T) new ExecutorHolder<Executor>(exec, this, name, null);
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			throw e;
		}
		
		
		return ret;
	}
	
	
	
	public void terminate(String name)
	{
		try
		{
			lock.lock();
			ExecutorHolder<?> eh = map.get(name);
			if(eh != null)
			{
				try
				{
					eh.close();
				}
				catch(Exception e)
				{
					
				}
			}
			
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public ExecutorService createFixedThreadPool(String name, int nThreads)
			throws NullPointerException, IllegalArgumentException
	{
		return register(Executors.newFixedThreadPool(nThreads), name);
	}
	
	public ScheduledExecutorService createScheduledThreadPool(String name, int nThreads)
			throws NullPointerException, IllegalArgumentException
	{
		return register(Executors.newScheduledThreadPool(nThreads), name);
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
			map.clear();
		}
		
		finally
		{
			lock.unlock();
		}
	}
	
	
	
}
