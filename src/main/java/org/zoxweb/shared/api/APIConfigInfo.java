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
package org.zoxweb.shared.api;

import java.util.List;

import org.zoxweb.shared.util.AccountID;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.CanonicalID;
import org.zoxweb.shared.util.TimeStamp;
import org.zoxweb.shared.util.UserID;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.ReferenceID;
import org.zoxweb.shared.util.SetDescription;
import org.zoxweb.shared.util.SetName;

/**
 * The API configuration information interface.
 * @author mzebib
 *
 */
public interface APIConfigInfo
	extends ReferenceID<String>,
			AccountID<String>, 
			UserID<String>, 
			SetName, 
			SetDescription,
        TimeStamp,
			CanonicalID
{
	
	public enum OAuthVersion
	{
		NONE,
		OAUTH_1,
		OAUTH_2
	}
	
	
	
	/**
	 * @return returns the API type name.
	 */
	public String getAPITypeName();

	
	/**
	 * This method sets the API type name.
	 * @param name
	 */
	public void setAPITypeName(String name);
	
	/**
	 * @return service types.
	 */
	public APIServiceType[] getServiceTypes();
	
	/**
	 * This method sets service types.
	 * @param serviceType
	 */
	public void setServiceTypes(APIServiceType[] serviceType);

	/**
	 * @return the version.
	 */
	public String getVersion();
	
	/**
	 * This method sets the version.
	 * @param version
	 */
	public void setVersion(String version);
	
	/**
	 * @return the configuration parameters.
	 */
	public ArrayValues<NVPair> getConfigParameters();
	
	/**
	 * This method sets the configuration parameters.
	 * @param configParams
	 */
	public void setConfigParameters(List<NVPair> configParams);
	
	/**
	 * This method sets the configuration parameters.
	 * @param configParams
	 */
	public void setConfigParameters(ArrayValues<NVPair> configParams);
	
	/**
	 * The default location where to store or get information, this parameter is optional and depend on the  
	 * the API 
	 * @return the default location
	 */
	public String getDefaultLocation();
	/**
	 * Set the default location
	 * 
	 * @param location
	 */
	public void setDefaultLocation(String location);
	
	/**
	 * This method checks if the specified service type is supported.
	 * @param type
	 * @return true is the type is supported.
	 */
	public boolean isServiceTypeSupported(APIServiceType type);
	
	/**
	 * @return status
	 */
	public APIConfigStatus getStatus();
	
	/**
	 * Set the current status.
	 * @param status
	 */
	public void setStatus(APIConfigStatus status);
	
	/**
	 * @return the oauth version
	 */
	public OAuthVersion getOAuthVersion();
	
	/**
	 * Set the oauth version
	 * @param version
	 */
	public void setOAuthVersion(OAuthVersion version);
	

}
