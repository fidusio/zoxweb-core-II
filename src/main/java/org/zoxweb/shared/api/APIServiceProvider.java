/*
 * Copyright (c) 2012-May 27, 2014 ZoxWeb.com LLC.
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
package org.zoxweb.shared.api;

import org.zoxweb.shared.util.CanonicalID;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.SetDescription;
import org.zoxweb.shared.util.SetName;

/**
 * The API service provider interface.
 * @author mzebib
 *
 */
public interface APIServiceProvider<SP>
	extends SetDescription, 
			SetName, 
			CanonicalID
{
	/**
	 * This method gets the store configuration parameters
	 * @return APIConfigInfo
	 */
	public APIConfigInfo getAPIConfigInfo();
	
	/**
	 * This method sets the store configuration parameters
	 * @param configInfo object
	 */
	public void setAPIConfigInfo(APIConfigInfo configInfo);
	
	

	/**
	 * This method connects to the data store
	 * @return native connection 
	 * @throws APIException in case of connection issues
	 */
	public SP connect() 
			throws APIException;
	
	/**
	 * Create a new connection
	 * @return native connection 
	 * @throws APIException in case of connection issues
	 */
	public SP newConnection()
			throws APIException;
	
	/**
	 * This method shuts down the data store.
	 * @throws APIException in case of closure issue
	 */
	public void close() 
			throws APIException;
	
	/**
	 * This method checks if the store is active.
	 * @return true if active
	 */
	public boolean isProviderActive();
	
	
	/**
	 * This method returns the exception handler.
	 * @returnAPIExceptionHandler
	 */
	public APIExceptionHandler getAPIExceptionHandler();
	
	/**
	 * This method sets the exception handler.
	 * @param exceptionHandler object to map native exceptions to APIException
	 */
	public void setAPIExceptionHandler(APIExceptionHandler exceptionHandler);
	
	/**
	 * Lookup the property type based on the GetName property.
	 * @param propertyName looking for 
	 * @return typed value
	 */
	public <T> T lookupProperty(GetName propertyName);
	
	
	
	/**
	 * This method will return last time it was used or accessed
	 * @return time in millis last time accessed
	 */
	public long lastTimeAccessed();
	/**
	 * This method will return the delta between NOW and last time the object was used
	 * @return time in millis for inactivity
	 */
	public long inactivityDuration();
	
	/**
	 * @return true if the current api instance is busy
	 */
	public boolean isBusy();
	
	
	
	
	
}
