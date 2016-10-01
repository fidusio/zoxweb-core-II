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

/**
 * The application ID interface is used to set an ID for each application running under a 
 * particular domain ID.
 * @author mzebib
 *
 */
public interface ApplicationID<T>
{
	/**
	 * Gets the application ID.
	 * @return
	 */
	public T getApplicationID();
	
	/**
	 * Sets the application ID.
	 * @param applicationID
	 */
	public void setApplicationID(T applicationID);
}
