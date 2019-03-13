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
package org.zoxweb.server.util;

import org.zoxweb.shared.util.ArrayQueue;


/**
 * The UQueueBound class is a bounded queue with a high and low marks. The
 * purpose of this type of queue is not to overflow the system by forcing the
 * queuing thread to wait when the high mark is reached till the dequeuing
 * thread reaches the low mark. <br>
 * Note: this class must be used by 2 threads simultaneously a
 * <code>queuing</code> thread and a <code>dequeuing</code>thread. The
 * <code>dequeuing</code> thread must be running continuously otherwise the
 * <code>queuing</code> will be pending till the dequeuing process starts
 * again.
 */
public class BoundedSimpleQueue<O>
    extends ArrayQueue<O>
{

	private int lowMark;
	private boolean boundMode = false;


	public BoundedSimpleQueue(int highMark)
	{
		this(highMark/2, highMark);
	}
	
	/**
	 * Create a bounded queue based on a lowMark and highMark parameters.
	 * 
	 * @param lowMark
	 *            of the queue size.
	 * @param highMark
	 *            of the queue size.
	 * @exception IllegalArgumentException
	 *                if the lowMark >= highMark or lowMark < 0 or highMark < 0.
	 */
	public BoundedSimpleQueue(int lowMark, int highMark)
        throws IllegalArgumentException
    {
    	super(highMark);
		if (highMark <= lowMark || highMark < 0 || lowMark < 0) {
			throw new IllegalArgumentException("Invalid queue parameters "
					+ " highMark " + highMark + " lowMark " + lowMark);
		}


		this.lowMark = lowMark;
	}

	/**
	 * This method will dequeue and Object, if the queue is empty it will return
	 * null.
	 */
	public synchronized O dequeue()
    {
		O ret = int_dequeue();
		if (boundMode && size <= lowMark)
		{
			boundMode = false;
			notifyAll();
		}
		return ret;
	}

	/**
	 * This method will add an object to the queue if the high mark is
	 * reached the calling thread will block till the size of the queue = to the
	 * lowMark.
	 * 
	 * @param toQueue the object.
	 */
	public synchronized boolean queue(O toQueue)
    {
		if(toQueue == null)
			throw new NullPointerException("Can't queue a null object");
		if (boundMode && toQueue != null)
		{
			try
            {
				while (boundMode)
					wait(300);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

		int_queue(toQueue);

		if (size == array.length)
		{
			boundMode = true;
		}
		return true;

	}

	/**
	 * This method will return a string containing the size, highMark, 
	 * lowMark, and boundMode results.
	 */
	@Override
	public String toString()
    {
		return "Bounded queue size " + size() + " Capacity " + array.length
				+ " LowMark " + lowMark + " bound mode " + boundMode;
	}


	/**
	 * @return the low mark of the queue.
	 */
	public int getLowMark()
    {
		return lowMark;
	}
	
}