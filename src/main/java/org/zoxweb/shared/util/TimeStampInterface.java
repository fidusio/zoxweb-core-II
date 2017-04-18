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

/**
 * The timestamp interface.
 */
public interface TimeStampInterface
    extends Serializable
{

	/**
	 * Returns the time when the file was created or uploaded into a system or domain (milliseconds).
	 * @return creation time in milliseconds
	 */
	public long getCreationTime();
	
	/**
	 * Sets the time when the file was created or uploaded into a system or domain (milliseconds).
	 * @param ts creation time in milliseconds
	 */
	public void setCreationTime(long ts);
	
	/**
	 * Returns the last time the file was updated (milliseconds).
	 * @return last time updated in milliseconds
	 */
	public long getLastTimeUpdated();
	
	/**
	 * Sets the last time the file was updated (milliseconds).
	 * @param ts last time updated in milliseconds
	 */
	public void setLastTimeUpdated(long ts);
	
	/**
	 * Returns the last time the file was read (milliseconds).
	 * @return last time read in milliseconds
	 */
	public long getLastTimeRead();
	
	/**
	 * Sets the last time the file was read (milliseconds).
	 * @param ts last time read in milliseconds
	 */
	public void setLastTimeRead(long ts);

}