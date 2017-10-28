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
package org.zoxweb.shared.queue;

import java.util.EventObject;

import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.ToBytes;

@SuppressWarnings("serial")
public abstract class QueueEvent<V>
    extends EventObject
    implements ToBytes
{
	
	protected V content;
	private boolean persistent;
	private int priority;
    
	public QueueEvent(Object source, boolean persistent, int priority)
    {
		super(source);
		this.persistent= persistent;
		this.priority = priority;
	}
	
	public QueueEvent(Object source, boolean persistent, int priority, V content)
    {
		this(source, persistent, priority);
		SharedUtil.checkIfNulls("Null content", content);
		this.content = content;
	}
	
	
	public V getContent()
	{
		return content;
	}
	
	public boolean isPersistent()
	{
		return persistent;
	}
	public int getPriority()
	{
		return priority;
	}

	
	
}