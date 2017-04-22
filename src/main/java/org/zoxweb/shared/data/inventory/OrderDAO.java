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

import java.util.List;

@SuppressWarnings("serial")
public class OrderDAO
    extends SetNameDescriptionDAO
{

    public enum OrderStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        DELIVERED
    }

    public enum Param
        implements GetNVConfig
    {

        ORDER_LIST(NVConfigManager.createNVConfigEntity("order_list", "List of orders per item", "Order List", false, true, OrderPerItemDAO.NVC_ORDER_PER_ITEM_DAO, NVConfigEntity.ArrayType.LIST)),
        TOTAL(NVConfigManager.createNVConfigEntity("total", "Total", "Total", true, true, MoneyValueDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),
        STATUS(NVConfigManager.createNVConfig("order_status", "Order status", "Order Status", true, false, OrderStatus.class)),


        // Customer
        // Scheduled time

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

    public static final NVConfigEntity NVC_ORDER_DAO = new NVConfigEntityLocal(
            "order_dao",
            null,
            OrderDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            OrderDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );


    public OrderDAO()
    {
        super(NVC_ORDER_DAO);
    }

    /**
     * Returns the order list.
     * @return
     */
    public List<OrderPerItemDAO> getOrderList()
    {
        return lookupValue(Param.ORDER_LIST);
    }

    /**
     * Sets the order list.
     * @param list
     */
    public void setOrderList(List<OrderPerItemDAO> list)
    {
        setValue(Param.ORDER_LIST, list);
    }


    /**
     * Returns the total.
     * @return
     */
    public MoneyValueDAO getTotal()
    {
        return lookupValue(Param.TOTAL);
    }

    /**
     * Sets the total.
     * @param total
     */
    public void setTotal(MoneyValueDAO total)
    {
        setValue(Param.TOTAL, total);
    }

    /**
     * Returns the order status.
     * @return
     */
    public OrderStatus getStatus() {
        return lookupValue(Param.STATUS);
    }

    /**
     * Sets the order status.
     * @param status
     */
    public void setStatus(OrderStatus status)
    {
        setValue(Param.STATUS, status);
    }

}