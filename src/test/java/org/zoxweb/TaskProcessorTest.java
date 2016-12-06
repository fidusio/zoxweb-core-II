package org.zoxweb;

//import java.util.Arrays;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


import org.zoxweb.server.task.TaskProcessor;





public class TaskProcessorTest 
implements Runnable
{

	
	AtomicInteger ai = new AtomicInteger();
	int counter = 0;
	
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		ai.addAndGet(1);
		inc();
		
		
	}
	
	protected synchronized void inc()
	{
		counter++;
	}
	
	public static void runTest(TaskProcessor tp, TaskProcessorTest td, int numberOfTasks)
	{
		long delta = System.currentTimeMillis();
		int counter = 0;
		for ( int i = 0; i <numberOfTasks ; i++)
		{
			counter++;
			//System.out.println("Adding event " + (counter));
			//tp.queueTask(new TaskEvent( tp, td,  (Object[])null));
			tp.execute(td);
			
		}
		
		
		
		
		
		while (td.counter != counter)
		{
			System.out.println("Available thread " + tp.availableExecutorThreads());
			synchronized(td)
			{
				try 
				{
					//System.out.println("Available thread " + te.availableExecutorThreads());
					td.wait(TaskProcessor.WAIT_TIME);
					//System.out.println("Available thread " + te.availableExecutorThreads());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		delta = System.currentTimeMillis() - delta;
		
		
		System.out.println("It took " + delta + " millis callback " + td + " using queue " +  tp.getQueueMaxSize() + " and " + tp.availableExecutorThreads() + " executor thread");
		System.out.println("Available thread " + tp.availableExecutorThreads() + " total " + ((TaskProcessorTest)td).counter +":" + ((TaskProcessorTest)td).ai.get()) ;
	}
	
	public static void main(String... args)
	{
		int taskQueueSize = 400;
		int threadCount = 8;
		int numberOfTasks= 20_000_000;
		
		
		if ( args.length == 3)
		{
			try
			{
				int index = 0 ; 
				taskQueueSize = Integer.parseInt( args[index++]);
				threadCount = Integer.parseInt( args[index++]);
				numberOfTasks = Integer.parseInt( args[index++]);
			}
			catch(Exception e)
			{
				
			}
		}
		
		
		
		TaskProcessor te = new TaskProcessor(taskQueueSize,  threadCount, Thread.MIN_PRIORITY, false);
		
		

		
		
		runTest(te, new TaskProcessorTest(), numberOfTasks);
		//runTest( te, new TaskProcessorTest(), numberOfTasks);
		te.close();
		
//		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		
		//UThread.sleep(20000);
		
	}

	
}
