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
package org.zoxweb.server.util;

import java.util.HashMap;
import java.util.UUID;

import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SetCanonicalID;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.TimeStamp;

public class MetaUtil
{
	public static final MetaUtil SINGLETON = new MetaUtil();

	private HashMap<String, NVConfigEntity> classNameToNVCE = new HashMap<String, NVConfigEntity>();

	private MetaUtil()
    {

	}
	
	public synchronized  NVConfigEntity fromClass(String className)
			throws ClassNotFoundException,
				   InstantiationException,
				   IllegalAccessException,
				   NullPointerException,
				   IllegalArgumentException
    {
		SharedUtil.checkIfNulls("Null class name", className);
		NVConfigEntity nvce = classNameToNVCE.get(className);

		if (nvce == null)
		{
			nvce = fromClass(Class.forName(className));
			classNameToNVCE.put(className, nvce);
		}
		
		return nvce;
	}

	public NVConfigEntity fromClass(Class<?> clazz) 
        throws InstantiationException, IllegalAccessException, NullPointerException, IllegalArgumentException
    {
		SharedUtil.checkIfNulls("Null class name", clazz);
		Object obj = clazz.newInstance();

		if (obj instanceof NVEntity)
		{
			NVEntity nve = (NVEntity) obj;
			return (NVConfigEntity) nve.getNVConfig();
		}
		
		throw new IllegalArgumentException("class:" + clazz.getName() + " is not derived from NVEntity");
	}
	
	public static void initCanonicalUUID(NVEntity nve)
    {
		if (nve != null)
		{
			if (nve instanceof SetCanonicalID)
			{
				SetCanonicalID sci = (SetCanonicalID) nve;

				if (sci.getCanonicalID() == null)
				{
					sci.setCanonicalID(UUID.randomUUID().toString());
				}
			}
		}
	}
	
	public static void initTimeStamp(NVEntity nve)
    {
		if (nve != null)
		{
			if (nve instanceof TimeStamp)
			{
				TimeStamp tsd = (TimeStamp) nve;

				if (tsd.getCreationTime() == 0)
				{
					tsd.setCreationTime(System.currentTimeMillis());
				}
				
				if (tsd.getLastTimeUpdated() == 0)
				{
					tsd.setLastTimeUpdated(System.currentTimeMillis());
				}
			}
		}		
	}
	
	public static void updateReadTimeStamp(NVEntity nve)
    {
		if (nve != null)
		{
			if (nve instanceof TimeStamp)
			{
				TimeStamp tsd = (TimeStamp) nve;
				tsd.setLastTimeRead(System.currentTimeMillis());
			}
		}		
	}
	
	public static void updateUpdateTimeStamp(NVEntity nve)
    {
		if (nve != null)
		{
			if (nve instanceof TimeStamp)
			{
				TimeStamp tsd = (TimeStamp) nve;
				tsd.setLastTimeUpdated(System.currentTimeMillis());
			}
		}		
	}

}