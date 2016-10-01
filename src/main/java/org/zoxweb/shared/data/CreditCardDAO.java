/*
 * Copyright (c) 2012-May 28, 2014 ZoxWeb.com LLC.
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

import org.zoxweb.shared.filters.ChainedFilter;
import org.zoxweb.shared.filters.CreditCardNumberFilter;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This class defines the credit card data access object used to create 
 * credit card information.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class CreditCardDAO 
	extends SetNameDescriptionDAO
{
	
	/**
	 * This enum includes the following parameters:
	 * card type, card holder name, card number, expiration date,
	 * and security code.
	 * @author mzebib
	 *
	 */
	public enum Params
		implements GetNVConfig
	{
		CARD_TYPE(NVConfigManager.createNVConfig("credit_card_type", "Type of credit card", "CreditCardType", true, true, CreditCardType.class)),
		CARD_HOLDER_NAME(NVConfigManager.createNVConfig("card_holder_name", "Name of card holder", "CardHolderName", true, true, String.class)),
//		CARD_NUMBER(NVConfigManager.createNVConfig("card_number", "Credit card number", "CardNumber", true, true, false, String.class, CreditCardNumberFilter.SINGLETON)),
		@SuppressWarnings("unchecked")
		CARD_NUMBER(NVConfigManager.createNVConfig("card_number", "Credit card number", "CardNumber", true, true, false, String.class, new ChainedFilter(CreditCardNumberFilter.SINGLETON, FilterType.ENCRYPT_MASK))),
		EXPIRATION_DATE(NVConfigManager.createNVConfig("expiration_date", "Card expiration date", "ExpirationDate", true, true, Date.class)),
		SECURITY_CODE(NVConfigManager.createNVConfig("security_code", "Card security code", "SecurityCode", true, true, String.class)),
		
		;
		
		private final NVConfig cType;
		
		Params( NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}
	}
	
	public static final NVConfigEntity NVC_CREDIT_CARD_DAO = new NVConfigEntityLocal(
																						"credit_card_dao", 
																						null , 
																						"CreditCardDAO", 
																						true, 
																						false, 
																						false, 
																						false, 
																						CreditCardDAO.class, 
																						SharedUtil.extractNVConfigs(Params.values()), 
																						null, 
																						false, 
																						SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																					);
	
	
	/**
	 * This is the default constructor.
	 */
	public CreditCardDAO()
	{
		super( NVC_CREDIT_CARD_DAO);
	}
		
//	/**
//	 * This constructor instantiates CreditCardDAO based on list of NVConfigEntity type.
//	 * @param list
//	 */
//	protected CreditCardDAO(List<NVConfigEntity> list)
//	{
//		super(SharedUtil.merge(list, NVC_CREDIT_CARD_DAO));
//	}
		
	
	/**
	 * Gets the credit card type.
	 * @return
	 */
	public CreditCardType getCardType() 
	{
		return lookupValue(Params.CARD_TYPE);
	}
	
	/**
	 * Sets the credit card type.
	 * @param type
	 */
	public void setCardType(CreditCardType type) 
	{
		setValue(Params.CARD_TYPE, type);
	}
	
	/**
	 * Gets the card holder name.
	 * @return
	 */
	public String getCardHolderName() 
	{
		return lookupValue(Params.CARD_HOLDER_NAME);
	}
	
	/**
	 * Sets the card holder name.
	 * @param name
	 */
	public void setCardHolderName(String name) 
	{
		setValue(Params.CARD_HOLDER_NAME, name);
	}
	
	/**
	 * Gets the card number.
	 * @return
	 */
	public String getCardNumber()
	{	
		return lookupValue(Params.CARD_NUMBER);
	}

	/**
	 * Sets the card number.
	 * @param number
	 */
	public void setCardNumber(String number)
	{
//		setValue(CreditCard.CARD_NUMBER, CreditCardNumberFilter.SINGLETON.validate(number));
		setValue(Params.CARD_NUMBER, number);
	}
		
	/**
	 * Gets the expiration date.
	 * @return
	 */
	public long getExpirationDate()
	{
		return lookupValue(Params.EXPIRATION_DATE);
	}
	
	/**
	 * Sets the expiration date.
	 * @param date
	 */
	public void setExpirationDate(long date) 
	{
		setValue(Params.EXPIRATION_DATE, date);				
	}
	
	/**
	 * Gets the security code.
	 * @return
	 */
	public String getSecurityCode()
	{
		return lookupValue(Params.SECURITY_CODE);
	}
	
	/**
	 * Sets the security code.
	 * @param code
	 */
	public void setSecurityCode(String code)
	{
		setValue(Params.SECURITY_CODE, CreditCardNumberFilter.validateCVV(getCardNumber(), code));				
	}
	
}