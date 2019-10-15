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
package org.zoxweb.server.task;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.zoxweb.shared.util.*;
import org.zoxweb.server.task.RunnableTask.RunnableTaskContainer;

public class TaskSchedulerProcessor
    implements Runnable, DaemonController, GetNVProperties {

	public final class TaskSchedulerAppointment
			implements Appointment {

		private Appointment appointment;
		private TaskEvent taskEvent;

		private TaskSchedulerAppointment(Appointment ts, TaskEvent te) {
			SharedUtil.checkIfNulls("TaskScheduler can't be null", ts);
			appointment = ts;
			this.taskEvent = te;
		}

		@Override
		public long getDelayInMillis() {
			return appointment.getDelayInMillis();
		}

		@Override
		public void setDelayInMillis(long delayInMillis) 
		{
		  setDelayInNanos(delayInMillis, System.nanoTime());
		}

		@Override
		public long getExpirationInMillis() {
			return appointment.getExpirationInMillis();
		}

		@Override
		public boolean cancel() {
			return remove(this);
		}

		@Override
		public void setDelayInNanos(long delayInMillis, long nanoOffset)
		{
			// TODO Auto-generated method stub
			cancel();
			appointment.setDelayInNanos(delayInMillis, nanoOffset);
			queue(this);
		}

		@Override
		public long getExpirationInMicros()
		{
			// TODO Auto-generated method stub
			return appointment.getExpirationInMicros();
		}

//		@Override
//		public void run() {
//
//		}
//
//		@Override
//		public boolean cancel(boolean mayInterruptIfRunning) {
//			return false;
//		}
//
//		@Override
//		public boolean isCancelled() {
//			return false;
//		}
//
//		@Override
//		public boolean isDone() {
//			return false;
//		}
//
//		@Override
//		public Object get() throws InterruptedException, ExecutionException {
//			return  taskEvent.getExecutionResult();
//		}
//
//		@Override
//		public Object get(long timeout, TimeUnit unit)
//				throws InterruptedException, ExecutionException, TimeoutException {
//			return null;
//		}
//
//
//		public boolean isPeriodic() {
//			return false;
//		}
//
//		public long getDelay(TimeUnit unit) {
//			switch(unit)
//			{
//
//				case NANOSECONDS:
//					return getExpirationInMillis();
//				case MICROSECONDS:
//					return TimeUnit.NANOSECONDS.toMicros(getExpirationInMillis());
//				case MILLISECONDS:
//					return TimeUnit.NANOSECONDS.toMillis(getExpirationInMillis());
//				case SECONDS:
//					return TimeUnit.NANOSECONDS.toSeconds(getExpirationInMillis());
//				case MINUTES:
//					return TimeUnit.NANOSECONDS.toMinutes(getExpirationInMillis());
//				case HOURS:
//					return TimeUnit.NANOSECONDS.toHours(getExpirationInMillis());
//				case DAYS:
//					return TimeUnit.NANOSECONDS.toDays (getExpirationInMillis());
//				default:
//					throw new IllegalArgumentException(unit + " not supported");
//			}
//		}


//		public int compareTo(Delayed o) {
//			return 0;
//		}
	}
	
	private TaskProcessor taskProcessor = null;
	private boolean live = true;
	private static final long DEFAULT_TIMEOUT = Const.TimeInMillis.MILLI.MILLIS*500;
	private static final AtomicLong TSP_COUNTER = new AtomicLong(0);
	private static final AtomicLong instanceCounter = new AtomicLong();
	private long counterID = instanceCounter.incrementAndGet();
	volatile private ConcurrentSkipListSet<TaskSchedulerAppointment> queue = null;
	
	public TaskSchedulerProcessor() {
		this(Appointment.EQUAL_COMPARATOR, null);
	}

	public TaskSchedulerProcessor(TaskProcessor tp) {
		this(Appointment.EQUAL_COMPARATOR, tp);
	}

	private TaskSchedulerProcessor(Comparator<Appointment> tsc, TaskProcessor tp) {
		SharedUtil.checkIfNulls("TaskSchedulerComparator can't be null", tsc);
		queue =  new ConcurrentSkipListSet<TaskSchedulerAppointment>(tsc);
		taskProcessor = tp;	
		new Thread(this, "TSP-" + TSP_COUNTER.incrementAndGet()).start();
	}

	public void close() {
		if (live) {
			synchronized(this) {
				// check to avoid double penetration
				if (live) {
					live = false;
					notify();
				}
			}

			synchronized(queue) {
				queue.notify();
			}
		}
	}

	@Override
	public boolean isClosed() {
		return !live;
	}
	
	public Appointment queue(Object source,  Appointment a, TaskExecutor te, Object... params) {
		TaskEvent tEvent = new TaskEvent(source, te, params);

		if (a == null) {
			a = new AppointmentDefault();
		}

		return queue(new TaskSchedulerAppointment(a, tEvent));
	}
	
	public Appointment queue(Object source,  long timeInMillis, TaskExecutor te, Object... params) {
      Appointment a = new AppointmentDefault(timeInMillis, System.nanoTime());
	  TaskEvent tEvent = new TaskEvent(source, te, params);

     
      return queue(new TaskSchedulerAppointment(a, tEvent));
  }

	public Appointment queue(Appointment a, TaskEvent te) {
		if (a == null) {
			a = new AppointmentDefault(0, System.nanoTime());
		}

		return queue(new TaskSchedulerAppointment(a, te));
	}
	
	
//	public Appointment queue(Appointment a, Runnable command)
//	{
//		if (command != null)
//			return queue(a, new TaskEvent(this, new RunnableTaskContainer(command),(Object[]) null));
//
//		return null;
//	}

	/**
	 * Execute a runnable i
	 * @param delayInMillis
	 * @param command
	 * @return
	 */
	public Appointment queue(long delayInMillis, Runnable command)
    {
        if (command != null)
            return queue(new AppointmentDefault(delayInMillis, System.nanoTime()), new TaskEvent(this, new RunnableTaskContainer(command),(Object[]) null));
        
        return null;
    }
	
	
	
	
	

	private TaskSchedulerAppointment queue(TaskSchedulerAppointment te) {
		if (!live) {
			throw new IllegalArgumentException("TaskSchedulerProcessor is dead");
		}
		
		synchronized(queue) {
			while(!queue.add(te)) {
				te.appointment.setDelayInNanos(te.appointment.getDelayInMillis(), System.nanoTime() + 1);
			}

			queue.notify();
		}
		
		return te;
	}
	
	private TaskSchedulerAppointment dequeue() {
		synchronized(queue) {
			return queue.pollFirst();
		}
	}
	
	public boolean remove(Appointment tsa) {
		synchronized (queue) {
			return queue.remove(tsa);
		}
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(live) {
			long timeToWait = 0;
			
			do {
				TaskSchedulerAppointment tSchedulerEvent = null;

				synchronized(queue) {
					long delay = waitTime();

					if (delay <= 0) {
						tSchedulerEvent = dequeue();
					} else {
						timeToWait = delay;
					}	
				}
				
				if (tSchedulerEvent != null) {
					if (taskProcessor != null) {
						taskProcessor.queueTask(tSchedulerEvent.taskEvent);
					} else {
						// we need to execute task locally
						try {
							tSchedulerEvent.taskEvent.getTaskExecutor().executeTask(tSchedulerEvent.taskEvent);
						} catch( Throwable e) {
							e.printStackTrace();
						}
						
						try {
							tSchedulerEvent.taskEvent.getTaskExecutor().finishTask(tSchedulerEvent.taskEvent);
						} catch( Throwable e) {
							e.printStackTrace();
						}
					}
				}

			} while(timeToWait == 0);

			// wait time for the wakeup
			synchronized(queue) {
				timeToWait = waitTime();
				if (live && timeToWait > 0) {
					
					try {
						queue.wait(timeToWait);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public int pendingTasks()
	{
		return queue.size();
	}
	
	public long waitTime()
	{
		long delay  = DEFAULT_TIMEOUT;
		try
		{
			TaskSchedulerAppointment tSchedulerEvent = queue.first();
			delay = tSchedulerEvent.getExpirationInMillis() - System.currentTimeMillis();
		} catch(NoSuchElementException e) {

		}
		return delay;
	}

	@Override
	public NVGenericMap getProperties()
	{
		NVGenericMap ret = new NVGenericMap();
		ret.setName("task_scheduler");
		ret.add(new NVLong("instance_id", counterID));
		ret.add(new NVInt("pending_tasks", queue.size()));
		ret.add("current_wait", Const.TimeInMillis.toString(waitTime()));
		return ret;
	}
}