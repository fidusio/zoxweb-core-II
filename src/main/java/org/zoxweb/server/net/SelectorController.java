package org.zoxweb.server.net;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.zoxweb.shared.util.SharedUtil;


/**
 * This class is used to allow the Selector object to be used in multi-threaded environment.
 * @author mnael
 *
 */
public class SelectorController 
{
	
	
	//private static final transient Logger log = Logger.getLogger(SelectorController.class.getName());
	
	private final Selector selector;



	private final Lock selectLock = new ReentrantLock();
	
	private final Lock lock = new ReentrantLock();
	
	
	/**
	 * Create a Selector Controller
	 * @param selector
	 */
	public SelectorController(Selector selector)
	{
		this.selector = selector;
	}
	
	/**
	 * Register the socket channel with the selector by applying the following procedure:
	 * <ol>
	 * <li> invoke lock on the general lock
	 * <li> wakeup the selector
	 * <li> invoke lock on the select lock
	 * <li> register the channel with the selector
	 * <li> unlock the general lock
	 * <li> unlock the select lock
	 * <li> return the selection key
	 * </ol>
	 * @param ch
	 * @param ops
	 * @return
	 * @throws IOException
	 */
	public SelectionKey register(AbstractSelectableChannel ch, int ops) throws IOException
	{
		return register(null, ch, ops, null, false);
	}
	
	

	/**
	 * Register the socket channel with the selector by applying the following procedure:
	 * <ol>
	 * <li> invoke lock on the general lock
	 * <li> wakeup the selector
	 * <li> invoke lock on the select lock
	 * <li> register the channel with the selector
	 * <li> unlock the general lock
	 * <li> unlock the select lock
	 * <li> return the selection key
	 * </ol>
	 * @param niocc
	 * @param ch
	 * @param ops
	 * @param attachement
	 * @param blocking
	 * @return
	 * @throws IOException
	 */
	public SelectionKey register(NIOChannelCleaner niocc, AbstractSelectableChannel ch, int ops, Object attachement, boolean blocking) throws IOException
	{
		SelectionKey ret;
		try
		{
			// block the select lock just in case
			lock.lock();
			// wakeup the selector
			selector.wakeup();
			// invoke the main lock
			selectLock.lock();
			SharedUtil.getWrappedValue(ch).configureBlocking(blocking);
			ret = SharedUtil.getWrappedValue(ch).register(selector, ops, attachement);
			if (niocc != null)
			{
				niocc.add(ret);
			}
		}
		finally
		{
			lock.unlock();
			selectLock.unlock();
			
		}
		
		return ret;
	}

	
	/**
	 * Blocking select
	 * @return number of selection match
	 * @throws IOException
	 */
	public int select() throws IOException
	{
		return select(0);
	}
	
	/**
	 * 
	 * @param timeout
	 * @return
	 * @throws IOException
	 */
	public int select(long timeout) throws IOException
	{
		try
		{
			// we must lock
			lock.lock();
			// and immediately unlock caused by a very fast thread;
			lock.unlock();
			selectLock.lock();
			return selector.select(timeout);
		}
		finally
		{
			selectLock.unlock();
		}
	}
	
	public void cancelSelectionKey(SelectionKey sk)
	{
		
		if (sk != null)
		{
			try
			{
				// block the select lock just in case
				lock.lock();
				// wakeup the selector
				selector.wakeup();
				// invoke the main lock
				selectLock.lock();
				sk.cancel();
				
			}
			finally
			{
				lock.unlock();
				selectLock.unlock();
			}
		}
		
	}
	
	

	
	public Selector getSelector()
	{
		return selector;
	}

}
