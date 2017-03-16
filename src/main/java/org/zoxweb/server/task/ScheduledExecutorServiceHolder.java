package org.zoxweb.server.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.zoxweb.shared.util.LifeCycleMonitor;
/**
 * 
 * @author mnael
 *
 */
public class ScheduledExecutorServiceHolder extends ExecutorServiceHolder
implements ScheduledExecutorService 
{
	
	public ScheduledExecutorServiceHolder(ScheduledExecutorService ses, LifeCycleMonitor<ExecutorHolder<?>> lcm, String name, String description)
	{
		super(ses, lcm, name, description);
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		// TODO Auto-generated method stub
		return ((ScheduledExecutorService)es).schedule(command, delay, unit);
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		// TODO Auto-generated method stub
		return ((ScheduledExecutorService)es).schedule(callable, delay, unit);
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		// TODO Auto-generated method stub
		return ((ScheduledExecutorService)es).scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		// TODO Auto-generated method stub
		return ((ScheduledExecutorService)es).scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}

}
