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
package org.zoxweb.shared.data.events;

import java.util.EventObject;

/**
 *
 */
@SuppressWarnings("serial")
public class StringTokenEvent 
    extends EventObject
{
	private final String token;
	private final long timeStamp;

	public StringTokenEvent(Object source, String token)
	{
		super(source);
		this.token = token;
		timeStamp = System.currentTimeMillis();
	}

    /**
     * Returns the token.
     * @return
     */
	public String getToken()
	{
		return token;
	}

	/**
	 * @return the timestamp in millis when the event was created
	 */
	public final long getTimeStamp()
	{
		return timeStamp;
	}
	
}
