/*
 * Copyright (c) 2012-2016 ZoxWeb.com LLC.
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
package org.zoxweb.shared.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.SharedStringUtil;

/**
 * This class defines the application configuration data access object.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class ApplicationConfigDAO
	implements Serializable
{
	
	public final static String DEFAULT_APPLICATION_CONF_FILENAME = "ApplicationConf.json";
	public final static String DEFAULT_APPLICATION_ENV_VAR = "APPLICATION_CONF_VAR";
	
	/**
	 * The application property container
	 */
	private HashMap<String, String> properties = new HashMap<String, String>();
	
	/**
	 * This enum includes the application configuration variables.
	 * @author mzebib
	 *
	 */
	public enum ApplicationDefaultParam
		implements	GetNameValue<String>
	{
		APPLICATON_NAME("APPLICATON_NAME", ""),
		APPLICATION_DESCRIPTION("APPLICATION_DESCRIPTION", ""),
		APPLICATION_URL("APPLICATION_URL", ""),
		APPLICATION_VERSION_RESOURCE("APPLICATION_VERSION_RESOURCE", ""),
		FORWARD_URL("FORWARD_URL", ""),
		
		CONF_DIR("CONF_DIR", "conf"),
		CACHE_DIR("CACHE_DIR", "data/cache"),
		DATA_DIR("DATA_DIR", "data"),
		PUBLIC_DIR("PUBLIC_DIR", "public"),
		TEMP_DIR("TEMP_DIR", "temp"),
		SSL_DIR("SSL_DIR", "ssl"),
		VAR_DIR("VAR_DIR", "var"),
		SERVER_INFO("SERVER_INFO", ""),
		SYSTEM_ID("SYSTEM_ID", ""),
		
		;
		
		private String name;
		private String value;
		
		ApplicationDefaultParam(String name, String value)
		{
			this.name = name.toLowerCase();
			this.value = value;
		}
		
		@Override
		public String getValue() 
		{
			return value;
		}

		@Override
		public String getName() 
		{
			return name;
		}
	}
	
	/**
	 * The default constructor.
	 */
	public ApplicationConfigDAO()
	{
		for (ApplicationDefaultParam p : ApplicationDefaultParam.values())
		{
			set(p.getName(), p.getValue());
		}
	}
	
	/**
	 * Looks up value based on application parameter.
	 * @param p
	 * @return the value in string 
	 */
	public String lookupValue(Enum<?> p)
	{
		if (p instanceof GetName)
		{
			String ret = lookupValue(((GetName) p).getName());
			if (ret != null)
				return ret;
		}
		
		if (p != null)
			return lookupValue(p.name());
		
		return null;
	}
	
	/**
	 * Looks up value by name.
	 * @param name
	 * @return the value 
	 */
	public String lookupValue(String name)
	{
		name = SharedStringUtil.toLowerCase(name);
		if (!SharedStringUtil.isEmpty(name))
			return properties.get(name);

		return null;
	}
	
	/**
	 * Sets property name and value.
	 * @param name
	 * @param value
	 */
	public synchronized void set(String name, String value)
	{
		name = SharedStringUtil.toLowerCase(name);
		if (!SharedStringUtil.isEmpty(name))
			properties.put( name, value);
	}
	
	/**
	 * Sets properties.
	 * @param prop
	 */
	public synchronized void setProperties(Map<String, String> prop)
	{
		properties.clear();
		
		for (Map.Entry<String, String> entry : prop.entrySet())
		{
			set(entry.getKey(), entry.getValue());
		}
			
		//this.properties = prop;
	}
	
	/**
	 * Gets properties.
	 * @return the properties map
	 */
	public Map<String, String> getProperties()
	{
		return properties;
	}
	
	public String toString()
	{
		return "" + properties;
	}
	
}