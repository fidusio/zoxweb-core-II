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

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.*;

/**
 * Created on 4/16/17
 */
@SuppressWarnings("serial")
public class OrderPerItemDAO
    extends SetNameDescriptionDAO
{

    public enum Param
        implements GetNVConfig
    {
        ITEM(NVConfigManager.createNVConfigEntity("item", "Item", "Item", true, true, ItemDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),
        QUANTITY(NVConfigManager.createNVConfig("quantity", "Quantity", "Quantity", true, true, int.class)),

        ;

        private final NVConfig nvc;

        Param(NVConfig nvc)
        {
            this.nvc = nvc;
        }

        public NVConfig getNVConfig()
        {
            return nvc;
        }
    }

    public static final NVConfigEntity NVC_ORDER_PER_ITEM_DAO = new NVConfigEntityLocal(
            "order_per_item_dao",
            null,
            OrderPerItemDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            OrderPerItemDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );

    public OrderPerItemDAO()
    {
        super(NVC_ORDER_PER_ITEM_DAO);
    }

    /**
     * Returns the item.
     * @return
     */
    public ItemDAO getItem()
    {
        return lookupValue(Param.ITEM);
    }

    /**
     * Sets the item.
     * @param item
     */
    public void setItem(ItemDAO item)
    {
        setValue(Param.ITEM, item);
    }

    /**
     * Returns the quantity.
     * @return
     */
    public int getQuantity()
    {
        return lookupValue(Param.QUANTITY);
    }

    /**
     * Sets the quantity.
     * @param quantity
     */
    public void setQuantity(int quantity)
    {
        setValue(Param.QUANTITY, quantity);
    }

}