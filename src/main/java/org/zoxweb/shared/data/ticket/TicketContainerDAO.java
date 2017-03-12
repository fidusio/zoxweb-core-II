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

import org.zoxweb.shared.accounting.BillingItemsContainerDAO;
import org.zoxweb.shared.data.NVEntityContainerDAO;
import org.zoxweb.shared.data.ResolutionStatus;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

/**
 * 
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class TicketContainerDAO
	extends NVEntityContainerDAO
{

	public enum Params
		implements GetNVConfig
	{
		STATUS(NVConfigManager.createNVConfig("status", "The status of the ticket.", "Status", false, true, ResolutionStatus.class)),
		ISSUER(NVConfigManager.createNVConfigEntity("issuer", "The ticket issuer.", "Issuer", true, true, TicketIssuerDAO.NVC_TICKET_ISSUER_DAO, ArrayType.NOT_ARRAY)),
		BILLING_ITEMS_CONTAINER(NVConfigManager.createNVConfigEntity("billing_items_container", "The billing items container.", "BillingItemsContainer", false, true, BillingItemsContainerDAO.class, ArrayType.NOT_ARRAY)),

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
	
	public static final NVConfigEntity NVC_TICKET_CONTAINER_DAO = new NVConfigEntityLocal
																						(
																							"ticket_container_dao", 
																							null, 
																							"Ticket", 
																							true, 
																							false, 
																							false, 
																							false, 
																							TicketContainerDAO.class, 
																							SharedUtil.extractNVConfigs(Params.values()), 
																							null, 
																							false, 
																							NVEntityContainerDAO.NVC_NVENTITY_CONTAINER_DAO
																						);

	/**
	 * The default constructor.
	 */
	public TicketContainerDAO()
	{
		super(NVC_TICKET_CONTAINER_DAO);
	}
	
	/**
	 * This constructor instantiates TicketContainerDAO object based on given NVConfigEntity.
	 * @param nvce
	 */
	protected TicketContainerDAO(NVConfigEntity nvce)
	{
		super(nvce);
	}
	
	public String getName()
	{
		return getIssuerInfo().getName();
	}
	
	public void setName(String name)
	{
		getIssuerInfo().setName(name);
	}
	
	public ResolutionStatus getStatus()
	{
		return lookupValue(Params.STATUS);
	}
	
	public void setStatus(ResolutionStatus status)
	{
		setValue(Params.STATUS, status);
	}

	public TicketIssuerDAO getIssuerInfo() 
	{
		return lookupValue(Params.ISSUER);
	}
	
	public void setIssuerInfo(TicketIssuerDAO issuer) 
	{
		if (issuer != null)
		{
			issuer.setCanonicalID(getCanonicalID());
		}
		
		setValue(Params.ISSUER, issuer);
	}

	
	public BillingItemsContainerDAO getBillingItemsContainerDAO() 
	{
		return lookupValue(Params.BILLING_ITEMS_CONTAINER);
	}

	public void setBillingItemsContainerDAO(BillingItemsContainerDAO container) 
	{
		if (container != null)
		{
			container.setCanonicalID(getCanonicalID());
		}
		
		setValue(Params.BILLING_ITEMS_CONTAINER, container);
	}
	
	/**
	 * Adds TicketResolutionDAO to container.
	 * A TicketContainerDAO object can contain multiple TicketResolution objects.
	 * @param ticketResolutionToAdd
	 * @return TicketResolutionDAO
	 */
	public synchronized TicketResolutionDAO addTicketResolutionDAO(TicketResolutionDAO ticketResolutionToAdd)
	{
		if (ticketResolutionToAdd != null && contains(ticketResolutionToAdd.getReferenceID()) == null)
		{
			ticketResolutionToAdd.setCanonicalID(getCanonicalID());
			
			return (TicketResolutionDAO) getContent().add(ticketResolutionToAdd);
		}
		
		return null;
	}
	
	/**
	 * Removes TicketResolutionDAO from container.
	 * 
	 * @param ticketResolutionToRemove
	 * @return TicketResolutionDAO
	 */
	public synchronized TicketResolutionDAO removeTicketResolutionDAO(TicketResolutionDAO ticketResolutionToRemove)
	{
		if (ticketResolutionToRemove != null)
		{
			return (TicketResolutionDAO) getContent().remove(ticketResolutionToRemove);
		}
		
		return null;
	}
	
}