/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
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
package org.zoxweb.shared.accounting;

import java.math.BigDecimal;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.RenderableValue;
import org.zoxweb.shared.util.SharedUtil;

/**
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class MoneyValueDAO
	extends SetNameDescriptionDAO
	implements RenderableValue<String>
{

	/**
	 * Default currency set to U.S. dollar (USD).
	 */
	public static final Currency DEFAULT_CURRENCY = Currency.USD;
	
	public enum Params
		implements GetNVConfig
	{
		CURRENCY(NVConfigManager.createNVConfig("currency", "Currency", "Currency", true, true, Currency.class)),
		VALUE(NVConfigManager.createNVConfig("money_value", "Value", "Value", true, true, BigDecimal.class)),
		
		;
		
		private final NVConfig cType;
		
		Params(NVConfig c)
		{
			cType = c;
		}
		
		@Override
		public NVConfig getNVConfig()
		{
			return cType;
		}

	}
	
	public static final NVConfigEntity NVC_MONEY_VALUE_DAO = new NVConfigEntityLocal(
																						"money_value_dao", 
																						null, 
																						"MoneyValueDAO", 
																						true, 
																						false, 
																						false, 
																						false, 
																						MoneyValueDAO.class, 
																						SharedUtil.extractNVConfigs(Params.values()), 
																						null, 
																						true, 
																						SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																					);
	
	
	/**
	 * The default constructor.
	 */
	public MoneyValueDAO()
	{
		super(NVC_MONEY_VALUE_DAO);
	}
	
	/**
	 * This constructor instantiates MoneyValueDAO based on given amount (BigDecimal) and currency (Currency).
	 * @param amount
	 * @param currency
	 */
	public MoneyValueDAO(BigDecimal amount, Currency currency)
	{
		this();
		setValue(amount);
		setCurrency(currency);
	}
	
	/**
	 * This constructor instantiates MoneyValueDAO given the amount (String).
	 * The default currency is applied.
	 * @param amount
	 */
	public MoneyValueDAO(String amount)
	{
		this(new BigDecimal(amount), DEFAULT_CURRENCY);
	}
	
	/**
	 * This constructor instantiates MoneyValueDAO given the amount (long).
	 * The default currency is applied.
	 * @param amount
	 */
	public MoneyValueDAO(long amount)
	{
		this(new BigDecimal(amount), DEFAULT_CURRENCY);
	}
	
	/**
	 * This constructor instantiates MoneyValueDAO given the amount (float).
	 * The default currency is applied.
	 * @param amount
	 */
	public MoneyValueDAO(float amount)
	{
		this(BigDecimal.valueOf(amount), DEFAULT_CURRENCY);
	}
	
	/**
	 * This constructor instantiates MoneyValueDAO based on given currency (Currency).
	 * @param currency
	 */
	public MoneyValueDAO(Currency currency)
	{
		this(null, currency);
	}
	
	/**
	 * Returns the currency.
	 * @return currency
	 */
	public Currency getCurrency() 
	{
		return lookupValue(Params.CURRENCY);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			
			if (obj instanceof MoneyValueDAO)
			{
				MoneyValueDAO mvd = (MoneyValueDAO) obj;
				if (mvd.getCurrency() == getCurrency() && mvd.getValue().equals(getValue()))
				{
					return true;
				}
				
			}
			else if (obj instanceof BigDecimal)
			{
				return getValue().equals(obj);
			}
			
		}
		
		return false;
	}
	/**
	 * Sets the currency.
	 * @param currency
	 */
	public void setCurrency(Currency currency)
	{
		setValue(Params.CURRENCY, currency);
	}
	
	/**
	 * Returns the value.
	 * @return value
	 */
	public BigDecimal getValue()
	{
		BigDecimal ret = lookupValue(Params.VALUE);
		if (ret != null)
		{
		    ret = ret.setScale(2);
        }

		return ret;
	}
	
	/**
	 * Sets the value.
	 * @param value
	 */
	public void setValue(BigDecimal value)
	{
		setValue(Params.VALUE, value);
	}
	
	/**
	 * Returns the value as String.
	 */
	@Override
	public String toValue() 
	{
		return toString();
	}

    /**
     * Returns a string representation of the value and currency.
     * Examples:
     * Value = 100.00 and currency = USD ($), returns $100.00,
     * Value = -100.00 and currency = USD ($), returns -$100.00
     */
    @Override
    public String toString()
    {
        String ret = null;

        if (getValue() != null && getCurrency() != null)
        {

            if (getValue().signum() < 0)
            {
                ret = "-" + getCurrency().getValue() + getValue().abs();
            }
            else
            {
                ret = getCurrency().getValue() + getValue();
            }
        }

        return ret;
    }
}