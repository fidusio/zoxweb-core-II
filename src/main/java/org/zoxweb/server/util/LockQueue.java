/*
 * Copyright (c) 2012-2015 ZoxWeb.com LLC.
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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.SimpleQueue;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class LockQueue 
{
	private SimpleQueue<Lock> queue = new SimpleQueue<Lock>();
	
	
	
	
	public LockQueue()
	{
		
	}
	
	public LockQueue(int size)
	{
		for (int i=0; i < size; i++)
		{
			queue.queue(new ReentrantLock());
		}
	}
	
	
	
	
	public Lock nextLock()
	{
		Lock ret = null;
		
		while((ret = queue.dequeue()) == null)
		{
			synchronized(this)
			{
				try
				{
					wait(Const.TimeInMillis.MILLI.MILLIS*500);
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ret.lock();
		return ret;
	}
	
	public void addLock(Lock lock)
	{
		SharedUtil.checkIfNulls("Can't add null lock", lock);
		lock.unlock();
		queue.queue(lock);
		synchronized(this)
		{
			notifyAll();
		}
	

		
	}
	
	
	
}
