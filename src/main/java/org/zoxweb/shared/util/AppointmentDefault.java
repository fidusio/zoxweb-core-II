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
package org.zoxweb.shared.util;

import java.io.Serializable;

import org.zoxweb.shared.util.Const.TimeInMillis;

@SuppressWarnings("serial")
public class AppointmentDefault
    implements Appointment, Serializable
{
	
	private long delay;
	private long expiration;
	private long expirationInMicros;

	public AppointmentDefault()
    {
		this(0);
	}
	
	public AppointmentDefault(String time)
	{
		this(TimeInMillis.toMillis(time));
	}
	
	public AppointmentDefault(long delayInMillis)
    {
		setDelayInNanos(delayInMillis, 0);
	}
	
	public AppointmentDefault(long delayInMillis, long nanoOffset)
	{
			setDelayInNanos(delayInMillis, nanoOffset);
	}

	@Override
	public long getDelayInMillis()
    {
		return delay;
	}

	@Override
	public  void setDelayInMillis(long delayInMillis)
    {	
	  setDelayInNanos(delayInMillis, 0);
	}
	
	
	public synchronized void setDelayInNanos(long delayInMillis, long nanoOffset)
	{
	  delay = delayInMillis;
	  expiration = System.currentTimeMillis() + delay;
	  //expirationInNanos = (expiration*1000000) + (nanoOffset%1000000);
	  expirationInMicros = (expiration*1000) + (nanoOffset%1000000)/1000;
	}

	@Override
	public synchronized long getExpirationInMillis()
    {
		return expiration;
	}

	@Override
	public boolean cancel()
    {
		return false;
	}

	@Override
	public synchronized long getExpirationInMicros()
	{
		// TODO Auto-generated method stub
		return expirationInMicros;
	}

}