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
package org.zoxweb.server.queue;

import java.io.IOException;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.api.APIException;
import org.zoxweb.shared.queue.QueueEvent;
import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedStringUtil;

@SuppressWarnings("serial")
public class NVEntityQueueEvent extends QueueEvent<NVEntity> 
{

	
	public NVEntityQueueEvent(Object source, byte[] data)
	{
		this(source, false, 0, data);
	}
	
	public NVEntityQueueEvent(Object source, boolean persistent, int priority, byte[] data) 
	{
		super(source, persistent, priority);
		// TODO Auto-generated constructor stub
		try {
			content = GSONUtil.fromJSON(SharedStringUtil.toString(data));
		} catch (AccessException | APIException | NullPointerException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public NVEntityQueueEvent(Object source, boolean persistent, int priority, NVEntity content) 
	{
		super(source, persistent, priority,  content);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte[] toBytes() 
	{
		try 
		{
			return SharedStringUtil.getBytes(GSONUtil.toJSON(getContent(), false, false, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
