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
package org.zoxweb.shared.data.order;

import org.zoxweb.shared.accounting.MoneyValueDAO;
import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.data.order.LimitValueDAO.LimitType;
import org.zoxweb.shared.util.*;

/**
 *
 */
@SuppressWarnings("serial")
public class PriceDAO
    extends SetNameDescriptionDAO
{

    public enum Param
        implements GetNVConfig
    {
        RANGE(NVConfigManager.createNVConfigEntity("range", "Range", "Range", true, true, RangeDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),
        PRICE(NVConfigManager.createNVConfigEntity("price", "Price", "Price", true, true, MoneyValueDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),

        ;

        private final NVConfig nvc;

        Param(NVConfig nvc)
        {
            this.nvc = nvc;
        }
        
        @Override
        public NVConfig getNVConfig()
        {
            return nvc;
        }
    }

    public static final NVConfigEntity NVC_PRICE_DAO = new NVConfigEntityLocal(
            "price_dao",
            null,
            PriceDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            PriceDAO.class,
            SharedUtil.extractNVConfigs(PriceDAO.Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );


    public PriceDAO()
    {
        super(NVC_PRICE_DAO);
    }

    public PriceDAO(RangeDAO range, MoneyValueDAO price)
    {
        this();
        setRange(range);
        setPrice(price);
    }

    /**
     * Returns the range.
     * @return
     */
    public RangeDAO getRange()
    {
        return lookupValue(Param.RANGE);
    }

    /**
     * Sets the range.
     * @param range
     */
    public void setRange(RangeDAO range)
    {
        setValue(Param.RANGE, range);
    }

    /**
     * Returns the price
     * @return
     */
    public MoneyValueDAO getPrice()
    {
        return lookupValue(Param.PRICE);
    }

    /**
     * Sets the price.
     * @param price
     */
    public void setPrice(MoneyValueDAO price)
    {
        setValue(Param.PRICE, price);
    }
    
	public boolean isWithinRange(int quantity) 
	{
		if (getRange() != null) 
		{
			LimitValueDAO rangeStart = getRange().getStart();
			LimitValueDAO rangeEnd = getRange().getEnd();
			
			boolean higherThanStart = false;
			boolean lowerThanEnd = false;
			
			if (rangeStart.getLimitType() != null && rangeStart.getLimitType() == LimitType.OPEN_VALUE)
			{
				higherThanStart = true;
			} 
			else 
			{
				if (rangeStart.isExclusive()) 
				{
					higherThanStart = quantity > rangeStart.getValue();
				} else {
					higherThanStart = quantity >= rangeStart.getValue();
				}
			}
		
			if (rangeEnd.getLimitType() != null && rangeEnd.getLimitType() == LimitType.OPEN_VALUE) 
			{
				lowerThanEnd = true;
			} 
			else 
			{
				if (rangeEnd.isExclusive())
				{
					lowerThanEnd = quantity < rangeEnd.getValue();
				} else {
					lowerThanEnd = quantity <= rangeEnd.getValue();
				}
			}
			
			
			if (higherThanStart && lowerThanEnd) 
			{
				return true;
			}
		}
		
		return false;
	}
    
    @Override
    public String toString() {
        return getRange() + " : " + getPrice();
    }

}