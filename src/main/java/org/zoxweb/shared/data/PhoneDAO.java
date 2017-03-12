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

import org.zoxweb.shared.filters.NumberFilter;
import org.zoxweb.shared.filters.PhoneNumberFilter;
import org.zoxweb.shared.util.CanonicalID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This class defines the phone data access object used to create phone numbers 
 * associated with an account.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class PhoneDAO 
	extends SetNameDescriptionDAO 
	implements CanonicalID
{
	
	/**
	 * This enum includes the following parameters:
	 * phone type, country code, area code, number, and
	 * the extension.
	 * @author mzebib
	 *
	 */
	public enum Params
		implements GetNVConfig
	{
		PHONE_TYPE(NVConfigManager.createNVConfig("phone_type", "Phone_type", "PhoneType", false, true, false, String.class, DataConst.PHONE_TYPES)),
		COUNTRY_CODE(NVConfigManager.createNVConfig("country_code", "Country code", "CountryCode", true, true, false, String.class, DataConst.COUNTRY_CODES)),
		AREA_CODE(NVConfigManager.createNVConfig("area_code", "Area code", "AreaCode", true, true, false, String.class, NumberFilter.SINGLETON)),
		NUMBER(NVConfigManager.createNVConfig("number", "Local number", "Number", true, true, false, String.class, PhoneNumberFilter.SINGLETON)),
		EXTENSION(NVConfigManager.createNVConfig("extension", "Extension", "Extension", false, true, String.class)),
		
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
	
	public static final NVConfigEntity NVC_PHONE_DAO = new NVConfigEntityLocal(
																				"phone_dao", 
																				null, 
																				"Phone", 
																				true, 
																				false, 
																				false, 
																				false, 
																				PhoneDAO.class, 
																				SharedUtil.extractNVConfigs(Params.values()), 
																				null, 
																				false, 
																				SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																			  );
	
	
	/**
	 * The default constructor.
	 */
	public PhoneDAO()
	{
		super(NVC_PHONE_DAO);
	}
	
//	protected PhoneDAO(List<NVConfigEntity> list)
//	{
//		super(SharedUtil.merge(list, NVC_PHONE_DAO));
//	}
	
	/**
	 * Gets canonical ID.
	 */
	public String toCanonicalID()
	{
		return '+' + getCountryCode() + getAreaCode() + getNumber();
	}
	
	/**
	 * Gets the phone type.
	 * @return phone type
	 */
	public String getPhoneType() 
	{
		return lookupValue(Params.PHONE_TYPE);
	}
	
	/**
	 * Sets the phone type.
	 * @param type
	 */
	public void setPhoneType(String type) 
	{
		setValue(Params.PHONE_TYPE, type);
	}
	
	/**
	 * Gets the country code.
	 * @return country code
	 */
	public String getCountryCode() 
	{
		return lookupValue(Params.COUNTRY_CODE);
	}
	
	/**
	 * Sets the country code.
	 * @param code
	 */
	public void setCountryCode(String code) 
	{
		setValue(Params.COUNTRY_CODE, code);
	}
	
	/**
	 * Gets the area code.
	 * @return area code
	 */
	public String getAreaCode() 
	{
		return lookupValue(Params.AREA_CODE);
	}
	
	/**
	 * Sets the area code.
	 * @param area
	 */
	public void setAreaCode(String area) 
	{
		setValue(Params.AREA_CODE, area);
	}
	
	/**
	 * Gets the number.
	 * @return phone number
	 */
	public String getNumber() 
	{
		return lookupValue(Params.NUMBER);
	}
	
	/**
	 * Sets the number.
	 * @param number
	 */
	public void setNumber(String number) 
	{
		setValue(Params.NUMBER, number);
	}
	
	/**
	 * Gets the extension.
	 * @return extension
	 */
	public String getExtension() 
	{
		return lookupValue(Params.EXTENSION);
	}
	
	/**
	 * Sets the extension.
	 * @param ext
	 */
	public void setExtension(String ext) 
	{
		setValue(Params.EXTENSION, ext);
	}

}