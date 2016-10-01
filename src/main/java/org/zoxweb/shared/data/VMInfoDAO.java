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

import java.util.Date;



import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * 
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class VMInfoDAO 
	extends SetNameDescriptionDAO 
{
	
	public enum Params
		implements GetNVConfig
	{
		CORE_COUNT(NVConfigManager.createNVConfig("core_count", "Number of available CPU cores", "CoreCount", true, true, Integer.class)),
		FREE_MEMORY(NVConfigManager.createNVConfig("free_memory", "Free memory", "FreeMemory", true, true, Long.class)),
		TOTAL_MEMORY(NVConfigManager.createNVConfig("total_memory", "Total memory", "TotalMemory", true, true, Long.class)),
		USED_MEMORY(NVConfigManager.createNVConfig("used_memory", "Used memory", "UsedMemory", true, true, Long.class)),
		MAX_MEMORY(NVConfigManager.createNVConfig("max_memory", "Maximum memory", "MaxMemory", true, true, Long.class)),
		TIME_STAMP(NVConfigManager.createNVConfig("time_stamp", "Time stamp", "TimeStamp", true, true, Date.class)),
		
		;
		
		private final NVConfig cType;
		
		Params(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}
	}
	
	public static final NVConfigEntity NVC_VMINFO_DAO = new NVConfigEntityLocal(
																					"vm_info_dao", 
																					null, 
																					"VMInfoDAO", 
																					true, 
																					false, 
																					false, 
																					false, 
																					AddressDAO.class, 
																					SharedUtil.extractNVConfigs(Params.values()), 
																					null, 
																					false, 
																					SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);
	
	/**
	 * The default constructor.
	 */
	public VMInfoDAO()
	{
		super(NVC_VMINFO_DAO);
	}
	
	/**
	 * Gets core count.
	 * @return
	 */
	public int getCoreCount()
	{
		return lookupValue(Params.CORE_COUNT);
	}
	
	/**
	 * Sets core count.
	 * @param cc
	 */
	public void setCoreCount(int cc)
	{
		setValue(Params.CORE_COUNT, cc);
	}
	
	/**
	 * Gets free memory.
	 * @return
	 */
	public long getFreeMemory()
	{
		return lookupValue(Params.FREE_MEMORY);
	}
	
	/**
	 * Sets free memory.
	 * @param freeMemory
	 */
	public void setFreeMemory(long freeMemory)
	{
		setValue(Params.FREE_MEMORY, freeMemory);
	}
	
	/**
	 * Gets max memory.
	 * @return
	 */
	public long getMaxMemory()
	{
		return lookupValue(Params.MAX_MEMORY);
	}
	
	/**
	 * Sets max memory.
	 * @param maxMemory
	 */
	public void setMaxMemory(long maxMemory)
	{
		setValue(Params.MAX_MEMORY, maxMemory);
	}
	
	/**
	 * Gets used memory.
	 * @return
	 */
	public long getUsedMemory()
	{
		return lookupValue(Params.USED_MEMORY);
	}
	
	/**
	 * Sets used memory.
	 * @param usedMemory
	 */
	public void setUsedMemory(long usedMemory)
	{
		setValue(Params.USED_MEMORY, usedMemory);
	}
	
	/**
	 * Gets time stamp.
	 * @return
	 */
	public Date getTimeStamp()
	{
		return new Date((Long) lookupValue(Params.TIME_STAMP));
	}
	
	/**
	 * Sets time stamp.
	 * @param date
	 */
	public void setTimeStamp(Date date)
	{
		setValue(Params.TIME_STAMP, date.getTime());
	}
	
	/**
	 * Gets total memory.
	 * @return
	 */
	public long getTotalMemory()
	{
		return lookupValue(Params.TOTAL_MEMORY);
	}
	
	/**
	 * Sets total memory.
	 * @param totalMemory
	 */
	public void setTotalMemory(long totalMemory)
	{
		setValue(Params.TOTAL_MEMORY, totalMemory);
	}
	
}