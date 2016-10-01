/*
 * Copyright (c) 2012-2014 ZoxWeb.com LLC.
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
package org.zoxweb.shared.util;

import java.io.Serializable;

/**
 * 
 * 
 */
@SuppressWarnings("serial")
public class AppointmentDefault
	implements Appointment, Serializable
{
	
	private long delay;
	private long expiration;
	
	/**
	 * Create an appointment now
	 */
	public AppointmentDefault()
	{
		this(0);
	}
	
	public AppointmentDefault(long delayInMillis)
	{
		setDelayInMillis(delayInMillis);
	}
	
	
	/**
	 * @see org.zoxweb.shared.util.Appointment#getDelayInMillis()
	 */
	@Override
	public long getDelayInMillis() 
	{
		// TODO Auto-generated method stub
		return delay;
	}

	/**
	 * @see org.zoxweb.shared.util.Appointment#setDelayInMillis(long)
	 */
	@Override
	public synchronized void setDelayInMillis(long delayInMillis) 
	{
		// TODO Auto-generated method stub
		delay = delayInMillis;
		expiration = System.currentTimeMillis() + delay;
		
	}

	/**
	 * @see org.zoxweb.shared.util.Appointment#getExpirationInMillis()
	 */
	@Override
	public synchronized long getExpirationInMillis() 
	{
		// TODO Auto-generated method stub
		return expiration;
	}

	/**
	 * @see org.zoxweb.shared.util.Appointment#cancel()
	 */
	@Override
	public boolean cancel() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
