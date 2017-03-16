package org.zoxweb.server.task;

import java.util.Collection;
import java.util.List;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import org.zoxweb.shared.util.LifeCycleMonitor;


public class ExecutorServiceHolder
extends ExecutorHolder<ExecutorService>
implements ExecutorService
{

	
	public ExecutorServiceHolder(ExecutorService es, LifeCycleMonitor<ExecutorHolder<?>> lcm)
	{
		this(es, lcm, null, null);
	}
	
	
	public ExecutorServiceHolder(ExecutorService es, LifeCycleMonitor<ExecutorHolder<?>> lcm, String name)
	{
		this(es, lcm, name, null);
		
	}
	
	public ExecutorServiceHolder(ExecutorService es, LifeCycleMonitor<ExecutorHolder<?>> lcm, String name, String description)
	{
		super(es, lcm, name, description);
	}
	
	
	

	@Override
	public void shutdown() 
	{
		super.close();
		es.shutdown();
		
		
		
	}

	@Override
	public List<Runnable> shutdownNow() {
		// TODO Auto-generated method stub
		super.close();
		return es.shutdownNow();
		
	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return es.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return es.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return es.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		// TODO Auto-generated method stub
		return es.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		// TODO Auto-generated method stub
		return es.submit(task, result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		// TODO Auto-generated method stub
		return es.submit(task);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		// TODO Auto-generated method stub
		return es.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return es.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return es.invokeAny(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return es.invokeAny(tasks, timeout, unit);
	}

	
	public void close()
	{
		shutdown();
	}

	

}
