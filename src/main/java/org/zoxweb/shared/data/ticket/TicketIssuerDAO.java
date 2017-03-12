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

import java.util.Date;

import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.data.CanonicalIDDAO;
import org.zoxweb.shared.data.PhoneDAO;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.EmailID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

/**
 * The TicketIssuerDAO class creates a ticket issue object based on ticket issuer's name, contact information, and incident details including
 * location, summary, and detailed description.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class TicketIssuerDAO
	extends CanonicalIDDAO
	implements EmailID
{

	public enum Params
		implements GetNVConfig
	{
		FIRST_NAME(NVConfigManager.createNVConfig("first_name", "The issuer's first name.", "First Name", false, true, String.class)),
		LAST_NAME(NVConfigManager.createNVConfig("last_name", "The issuer's last name.", "Last Name", false, true, String.class)),
		EMAIL(NVConfigManager.createNVConfig("email", "The issuer's email address.", "Email", true, true, false, String.class, FilterType.EMAIL)),
		PHONE_NUMBER(NVConfigManager.createNVConfigEntity("phone_number", "The issuer's phone number.", "Phone", false, true, PhoneDAO.class, ArrayType.NOT_ARRAY)),
		POSITION(NVConfigManager.createNVConfig("position", "The issuer's position at associated company.", "Position", false, true, String.class)),
		
		COMPANY(NVConfigManager.createNVConfig("company", "Name of associated company.", "Company", false, true, String.class)),
		INCIDENT_LOCATION(NVConfigManager.createNVConfig("incident_location", "The location of the incident.", "Incident Location", true, true, String.class)),
		INCIDENT_ADDRESS(NVConfigManager.createNVConfigEntity("incident_address", "The address of the incident.", "Address", false, true, AddressDAO.class, ArrayType.NOT_ARRAY)),		
		DUE_DATE(NVConfigManager.createNVConfig("due_date", "Date when the issue should be completed.", "DueDate", false, false, Date.class)),
	
		ASSIGNEE(NVConfigManager.createNVConfig("assignee", "The assignee's email address.", "Assignee", true, true, false, String.class, FilterType.EMAIL)),
		
		NAME(NVConfigManager.createNVConfig("name", "Summary of incident.", "Name", true, true, String.class)),
		DESCRIPTION(NVConfigManager.createNVConfig("description", "Detailed description of incident.", "Description", true, true, String.class)),
		
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
	
	public static final NVConfigEntity NVC_TICKET_ISSUER_DAO = new NVConfigEntityLocal
																					(
																						"ticket_issuer_dao", 
																						null, 
																						"Ticket Issuer", 
																						true, 
																						false, 
																						false, 
																						false, 
																						TicketIssuerDAO.class, 
																						SharedUtil.extractNVConfigs(Params.values()), 
																						null, 
																						false, 
																						CanonicalIDDAO.NVC_CANONICAL_ID_DAO
																					);
	
	/**
	 * The default constructor.
	 */
	public TicketIssuerDAO()
	{
		super(NVC_TICKET_ISSUER_DAO);
	}
	
	/**
	 * This constructor instantiates TicketIssuerDAO object based on given NVConfigEntity.
	 * @param nvce
	 */
	protected TicketIssuerDAO(NVConfigEntity nvce)
	{
		super(nvce);
	}

	/**
	 * Gets first name.
	 * @return first name 
	 */
	public String getFirstName() 
	{
		return lookupValue(Params.FIRST_NAME);
	}
	
	/**
	 * Sets first name.
	 * @param firstName
	 */
	public void setFirstName(String firstName) 
	{
		setValue(Params.FIRST_NAME, firstName);
	}
	
	/**
	 * Gets last name.
	 * @return last name
	 */
	public String getLastName() 
	{
		return lookupValue(Params.LAST_NAME);
	}
	
	/**
	 * Sets last name.
	 * @param lastName
	 */
	public void setLastName(String lastName) 
	{
		setValue(Params.LAST_NAME, lastName);
	}
	
	/**
	 * Gets email address.
	 * @return email id
	 */
	public String getEmailID() 
	{
		return lookupValue(Params.EMAIL);
	}
	
	/**
	 * Sets email address.
	 * @param email
	 */
	public void setEmailID(String email) 
	{
		setValue(Params.EMAIL, email);
	}
	
	/**
	 * Gets phone number.
	 * @return phone number
	 */
	public PhoneDAO getPhoneNumber() 
	{
		return lookupValue(Params.PHONE_NUMBER);
	}
	
	/**
	 * Sets phone number.
	 * @param phone
	 */
	public void setPhoneNumber(PhoneDAO phone) 
	{
		setValue(Params.PHONE_NUMBER, phone);
	}
	
	/**
	 * Gets issuer's position at associated company.
	 * @return position
	 */
	public String getPosition() 
	{
		return lookupValue(Params.POSITION);
	}
	
	/**
	 * Sets issuer's position at associated company.
	 * @param position
	 */
	public void setPosition(String position) 
	{
		setValue(Params.POSITION, position);
	}
	
	/**
	 * Gets company.
	 * @return company
	 */
	public String getCompany() 
	{
		return lookupValue(Params.COMPANY);
	}
	
	/**
	 * Sets company.
	 * @param company
	 */
	public void setCompany(String company) 
	{
		setValue(Params.COMPANY, company);
	}
	
	/**
	 * Gets incident location.
	 * @return incident location.
	 */ 
	public String getIncidentLocation() 
	{
		return lookupValue(Params.INCIDENT_LOCATION);
	}
	
	/**
	 * Sets incident location.
	 * @param location
	 */
	public void setIncidentLocation(String location) 
	{
		setValue(Params.INCIDENT_LOCATION, location);
	}
	
	/**
	 * Gets incident address.
	 * @return  incident address.
	 */
	public AddressDAO getIncidentAddress() 
	{
		return lookupValue(Params.INCIDENT_ADDRESS);
	}
	
	/**
	 * Sets incident address.
	 * @param address
	 */
	public void setIncidentAddress(AddressDAO address) 
	{
		setValue(Params.INCIDENT_ADDRESS, address);
	}
	
	/**
	 * Gets due date.
	 * @return due date.
	 */
	public long getDueDate() 
	{
		return lookupValue(Params.DUE_DATE);
	}
	
	/**
	 * Sets due date.
	 * @param date
	 */
	public void setDueDate(long date) 
	{
		setValue(Params.DUE_DATE, date);
	}
	
	/**
	 * Gets assignee.
	 * @return assignee.
	 */
	public String getAssignee() 
	{
		return lookupValue(Params.ASSIGNEE);
	}
	
	/**
	 * Sets assignee.
	 * @param assignee
	 */
	public void setAssignee(String assignee) 
	{
		setValue(Params.ASSIGNEE, assignee);
	}
	
	/**
	 * Gets summary of issue.
	 */
	public String getName() 
	{
		return lookupValue(Params.NAME);
	}
	
	/**
	 * Sets summary of issue.
	 * @param name
	 */
	public void setName(String name) 
	{
		setValue(Params.NAME, name);
	}
	
	/**
	 * Gets description of issue.
	 */
	public String getDescription() 
	{
		return lookupValue(Params.DESCRIPTION);
	}
	
	/**
	 * Sets description of issue.
	 * @param description
	 */
	public void setDescription(String description) 
	{
		setValue(Params.DESCRIPTION, description);
	}
	
}