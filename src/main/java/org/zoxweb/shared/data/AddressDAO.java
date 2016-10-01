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

import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * The AddressDAO class defines the address data access object used to create an address object.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class AddressDAO 
	extends SetNameDescriptionDAO
{
	
	public enum Params
		implements GetNVConfig
	{
		STREET(NVConfigManager.createNVConfig("street", "Street address", "Street", true, true, String.class)),
		CITY(NVConfigManager.createNVConfig("city", "City", "City", true, true, String.class)),
		STATE_PROVINCE(NVConfigManager.createNVConfig("state_province", "State or province", "State/Province", false, true, String.class)),
		COUNTRY(NVConfigManager.createNVConfig("country", "Country", "Country", true, true, false, String.class, DataConst.COUNTRIES)),
		ZIP_POSTAL_CODE(NVConfigManager.createNVConfig("zip_postal_code", "ZIP/Postal code", "Zip/PostalCode", false, true, String.class)),
		
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
	
	public static final NVConfigEntity NVC_ADDRESS_DAO = new NVConfigEntityLocal(
																					"address_dao", 
																					null , 
																					"Address", 
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
	public AddressDAO()
	{
		super(NVC_ADDRESS_DAO);
	}
	
	/**
	 * Gets the street.
	 * @return
	 */
	public String getStreet() 
	{
		return lookupValue(Params.STREET);
	}
	
	/**
	 * Sets the street.
	 * @param street
	 */
	public void setStreet(String street) 
	{
		setValue(Params.STREET, street);
	}
	
	/**
	 * Gets the city.
	 * @return
	 */
	public String getCity() 
	{
		return lookupValue(Params.CITY);
	}
	
	/**
	 * Sets the city.
	 * @param city
	 */
	public void setCity(String city) 
	{
		setValue(Params.CITY, city);
	}
	
	/**
	 * Gets the state or province.
	 * @return
	 */
	public String getStateOrProvince() 
	{
		return lookupValue(Params.STATE_PROVINCE);
	}
	
	/**
	 * Sets the state or province.
	 * @param state
	 */
	public void setStateOrProvince(String state) 
	{
		setValue(Params.STATE_PROVINCE, state);
	}
	
	/**
	 * Gets the country.
	 * @return
	 */
	public String getCountry() 
	{
		return lookupValue(Params.COUNTRY);
	}
	
	/**
	 * Sets the country.
	 * @param country
	 */
	public void setCountry(String country) 
	{
		setValue(Params.COUNTRY, country);
	}
	
	/**
	 * Gets the ZIP or postal code.
	 * @return
	 */
	public String getZIPOrPostalCode() 
	{
		return lookupValue(Params.ZIP_POSTAL_CODE);
	}
	
	/**
	 * Sets the ZIP or postal code.
	 * @param code
	 */
	public void setZIPOrPostalCode(String code) 
	{
		setValue(Params.ZIP_POSTAL_CODE, code);
	}
	
}