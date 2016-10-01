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
package org.zoxweb.shared.data.ticket;

import java.math.BigDecimal;

import org.zoxweb.shared.data.CanonicalIDDAO;
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
public class TicketResolutionDAO
	extends CanonicalIDDAO
{
	
	public enum Params
		implements GetNVConfig
	{
		
		STATUS(NVConfigManager.createNVConfig("status", "The ticket status.", "Status", true, true, String.class)),
		TIME_SPENT(NVConfigManager.createNVConfig("time_spent", "Time spent on ticket.", "TimeSpent", false, true, BigDecimal.class)),
		DESCRIPTION(NVConfigManager.createNVConfig("description", "Detailed description of ticket resolution.", "Description", true, true, String.class)),
		
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

	public static final NVConfigEntity NVC_TICKET_RESOLUTION_DAO = new NVConfigEntityLocal(
																							"ticket_resolution_dao", 
																							null, 
																							"Ticket Resolution", 
																							true, 
																							false, 
																							false, 
																							false, 
																							TicketResolutionDAO.class, 
																							SharedUtil.extractNVConfigs(Params.values()), 
																							null, 
																							false, 
																							CanonicalIDDAO.NVC_CANONICAL_ID_DAO
																						);

	/**
	 * The default constructor.
	 */
	public TicketResolutionDAO()
	{
		super(NVC_TICKET_RESOLUTION_DAO);
	}

	/**
	 * This constructor instantiates TicketResolutionDAO object based on given NVConfigEntity.
	 * @param nvce
	 */
	protected TicketResolutionDAO(NVConfigEntity nvce)
	{
		super(nvce);
	}
	
	/**
	 * Gets ticket resolution status.
	 * @return
	 */
	public String getStatus()
	{
		return lookupValue(Params.STATUS);
	}
	
	/**
	 * Sets ticket resolution status.
	 * @param status
	 */
	public void setStatus(String status)
	{
		setValue(Params.STATUS, status);
	}
	
	/**
	 * Gets time spent on ticket resolution.
	 * @return
	 */
	public BigDecimal getTimeSpent()
	{
		return lookupValue(Params.TIME_SPENT);
	}
	
	/**
	 * Sets time spent on ticket resolution.
	 * @param time
	 */
	public void setTimeSpent(BigDecimal time)
	{
		setValue(Params.TIME_SPENT, time);
	}
	
	/**
	 * Gets resolution description.
	 */
	public String getDescription()
	{
		return lookupValue(Params.DESCRIPTION);
	}
	
	/**
	 * Sets resolution description.
	 * @param description
	 */
	public void setDescription(String description)
	{
		setValue(Params.DESCRIPTION, description);
	}

}