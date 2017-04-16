package org.zoxweb.server.util;

import org.zoxweb.shared.util.SimpleQueue;

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
    extends SimpleQueue<O>
{
	private int highMark;
	private int lowMark;
	private boolean boundMode = false;
	
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
		if (highMark <= lowMark || highMark < 0 || lowMark < 0) {
			throw new IllegalArgumentException("Invalid queue parameters "
					+ " highMark " + highMark + " lowMark " + lowMark);
		}

		this.highMark = highMark;
		this.lowMark = lowMark;
	}

	/**
	 * This method will dequeue and Object, if the queue is empty it will return
	 * null.
	 */
	public synchronized O dequeue()
    {
		O ret = super.dequeue();
		if (boundMode && size() <= lowMark)
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
	 * @param toQueue
	 *            the object.
	 */
	public synchronized void queue(O toQueue)
    {
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

		super.queue(toQueue);

		if (size() == highMark)
		{
			boundMode = true;
		}

	}

	/**
	 * This method will return a string containing the size, highMark, 
	 * lowMark, and boundMode results.
	 */
	@Override
	public String toString()
    {
		return "Bounded queue size " + size() + " HighMark " + highMark
				+ " LowMark " + lowMark + " bound mode " + boundMode;
	}

	/**
	 * @return the high mark of the queue.
	 */
	public int getHighMark()
    {
		return highMark;
	}

	/**
	 * @return the low mark of the queue.
	 */
	public int getLowMark()
    {
		return lowMark;
	}
	
}