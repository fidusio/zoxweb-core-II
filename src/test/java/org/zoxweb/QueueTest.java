package org.zoxweb;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.zoxweb.server.util.RuntimeUtil;
import org.zoxweb.shared.data.VMInfoDAO;
import org.zoxweb.shared.util.SimpleQueueInterface;
import org.zoxweb.shared.util.SimpleQueue;




public class QueueTest 
{
	public static void main(String ...args)
	{
		int limit = 50000;
		SimpleQueueInterface<Object> uQueue = new SimpleQueue<Object>();
		ConcurrentLinkedQueue<Object> clQueue = new ConcurrentLinkedQueue<Object>();
		LinkedBlockingQueue<Object> lbQueue = new LinkedBlockingQueue<Object>();
		ArrayBlockingQueue<Object> abQueue = new ArrayBlockingQueue<Object>(limit);
		VMInfoDAO startVMID = RuntimeUtil.vmSnapshot();
		Object o = new Object();
		for (int j = 0; j < 30; j++)
		{
			
			
			System.out.println("\nTest run " + j);
			for (int i = 0; i < limit; i++)
			{
				
				uQueue.queue(new Object());
				clQueue.add(o);
				lbQueue.add(o);
				abQueue.add(o);
			}
			
			
			long ts = System.nanoTime();
			while(uQueue.dequeue() !=null)
			{
				
			}
			
			ts = System.nanoTime() - ts;
			System.out.println( ts + " nanos SimpleQueue took  sec to dequeue " + limit + ":" + uQueue.size());
			
			
			ts = System.nanoTime();
			while(clQueue.poll() !=null)
			{
				
			}
			
			ts = System.nanoTime() - ts;
			System.out.println( ts + " nanos ConcurrentLinkedQueue took  sec to dequeue " + limit + ":" + clQueue.size());
			
			ts = System.nanoTime();
			while(lbQueue.poll() !=null)
			{
				
			}
			
			ts = System.nanoTime() - ts;
			System.out.println( ts + " nanos LinkedBlockingQueue took  sec to dequeue " + limit + ":" + lbQueue.size());
			
			ts = System.nanoTime();
			while(abQueue.poll() !=null)
			{
				
			}
			
			ts = System.nanoTime() - ts;
			System.out.println( ts + " nanos ArrayBlockingQueue took  sec to dequeue " + limit + ":" + abQueue.size());
		
		}
		
		
		
		
		
		
		System.out.println();
		System.out.println(startVMID);
		System.out.println(RuntimeUtil.vmSnapshot());
		
		
		uQueue.queue(new Object());
		uQueue.queue(new Object());
		uQueue.queue(o);
		uQueue.queue(new Object());
		uQueue.queue(new Object());
		
		

		System.out.println(uQueue.size() + ":" +uQueue.contains(new Object()));
		System.out.println(uQueue.size() + ":" +uQueue.contains(o));
		System.out.println(uQueue.size() + ":" +uQueue.contains(new Object()));
		System.out.println(uQueue.size() + ":" +uQueue.contains(o));
		System.out.println(uQueue.size() + ":" +uQueue.contains(new Object()));
		System.out.println(uQueue.size() + ":" +uQueue.contains(o));
		int size = uQueue.size();
		int counter = 0;
		while(uQueue.dequeue() != null)
		{
			counter++;
		}
		
		System.out.println(size+"," + counter + ","+uQueue.size());
		System.out.println(uQueue.size() + ":" + uQueue.contains(o));
		uQueue.queue(o);
		System.out.println(uQueue.size() + ":" + uQueue.contains(o));
		uQueue.dequeue();
		System.out.println(uQueue.size() + ":" + uQueue.contains(o));
		uQueue.queue(new Object());
		uQueue.queue(new Object());
		uQueue.queue(o);
		System.out.println(uQueue.size() + ":" + uQueue.contains(o));
		
	}
}
