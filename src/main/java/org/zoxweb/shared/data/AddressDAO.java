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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The AddressDAO class defines the address data access object used to create an address object.
 * @author mzebib
 *
 */
@Entity
@Table(name = "address")
@SuppressWarnings("serial")
public class AddressDAO 
	extends SetNameDescriptionDAO {

	public static final String COLUMN_STREET = "street";
	public static final String COLUMN_CITY = "city";
	public static final String COLUMN_STATE = "state_province";
	public static final String COLUMN_COUNTRY = "country";
	public static final String COLUMN_ZIP_CODE = "zip_postal_code";

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
	 * The default constructor creates an AddressDAO instance.
	 */
	public AddressDAO() {
		super(NVC_ADDRESS_DAO);
	}
	
	/**
	 * Returns the street.
	 * @return the street
	 */
	@Column (name = COLUMN_STREET)
	public String getStreet() {
		return lookupValue(Params.STREET);
	}
	
	/**
	 * Sets the street.
	 * @param street
	 */
	public void setStreet(String street) {
		setValue(Params.STREET, street);
	}
	
	/**
	 * Returns the city.
	 * @return the city
	 */
	@Column (name = COLUMN_CITY)
	public String getCity() {
		return lookupValue(Params.CITY);
	}
	
	/**
	 * Sets the city.
	 * @param city
	 */
	public void setCity(String city) {
		setValue(Params.CITY, city);
	}
	
	/**
	 * Returns the state or province.
	 * @return the province
	 */
	@Column (name = COLUMN_STATE)
	public String getStateOrProvince() {
		return lookupValue(Params.STATE_PROVINCE);
	}
	
	/**
	 * Sets the state or province.
	 * @param state
	 */
	public void setStateOrProvince(String state) {
		setValue(Params.STATE_PROVINCE, state);
	}
	
	/**
	 * Returns the country.
	 * @return the country
	 */
	@Column (name = COLUMN_COUNTRY)
	public String getCountry() {
		return lookupValue(Params.COUNTRY);
	}
	
	/**
	 * Sets the country.
	 * @param country
	 */
	public void setCountry(String country) {
		setValue(Params.COUNTRY, country);
	}
	
	/**
	 * Returns the ZIP or postal code.
	 * @return the zip or postal code depending on the country
	 */
	@Column (name = COLUMN_ZIP_CODE)
	public String getZIPOrPostalCode() {
		return lookupValue(Params.ZIP_POSTAL_CODE);
	}
	
	/**
	 * Sets the ZIP or postal code.
	 * @param code
	 */
	public void setZIPOrPostalCode(String code) {
		setValue(Params.ZIP_POSTAL_CODE, code);
	}
	
}