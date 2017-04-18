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
package org.zoxweb.shared.data.inventory;

import org.zoxweb.shared.accounting.MoneyValueDAO;
import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.*;

@SuppressWarnings("serial")
public class ItemDAO
    extends SetNameDescriptionDAO
{
    public enum Param
        implements GetNVConfig
    {
        DISPLAY_NAME(NVConfigManager.createNVConfig("display_name", "Item display name", "Display Name", true, true, String.class)),
        PRICE_RANGE(NVConfigManager.createNVConfigEntity("price_range", "Price range", "Price Range", true, true, PriceRangeDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),
        AVAILABLE_QUANTITY(NVConfigManager.createNVConfig("available_quantity", "Available quantity", "Available Quantity", true, true, int.class)),
        UNIT_PRICE(NVConfigManager.createNVConfigEntity("unit_price", "Unit price", "Unit Price", true, true, MoneyValueDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),

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

    public static final NVConfigEntity NVC_ITEM_DAO = new NVConfigEntityLocal(
            "item_dao",
            null,
            ItemDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            ItemDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );


    public ItemDAO()
    {
        super(NVC_ITEM_DAO);
    }

    public String getDisplayName() {
        return lookupValue(Param.DISPLAY_NAME);
    }

    public void setDisplayName(String displayName)
    {
        setValue(Param.DISPLAY_NAME, displayName);
    }

    public PriceRangeDAO getPriceRange() {
        return lookupValue(Param.PRICE_RANGE);
    }

    public void setPriceRange(PriceRangeDAO priceRange)
    {
        setValue(Param.PRICE_RANGE, priceRange);
    }

    public int getAvailableQuantity() {
        return lookupValue(Param.AVAILABLE_QUANTITY);
    }

    public void setAvailableQuantity(int availableQuantity)
    {
        setValue(Param.AVAILABLE_QUANTITY, availableQuantity);
    }

    public MoneyValueDAO getUnitPrice() {
        return lookupValue(Param.UNIT_PRICE);
    }

    public void setUnitPrice(MoneyValueDAO unitPrice)
    {
        setValue(Param.UNIT_PRICE, unitPrice);
    }

}