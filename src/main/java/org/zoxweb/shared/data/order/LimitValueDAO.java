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

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.*;

/**
 * Created on 4/16/17
 */
@SuppressWarnings("serial")
public class LimitValueDAO
    extends SetNameDescriptionDAO
{

    public enum LimitType
    {
        NUMBER,
        OPEN_VALUE,

        ;
    }

    public enum Param
        implements GetNVConfig
    {
        TYPE(NVConfigManager.createNVConfig("limit_type", "Limit type", "LimitType", true, true, LimitType.class)),
        VALUE(NVConfigManager.createNVConfig("value", "Value", "Value", true, true, int.class)),
        EXCLUSIVE(NVConfigManager.createNVConfig("exclusive", "Inclusive (default) or exclusive", "Exclusive", false, true, boolean.class)),

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

    public static final NVConfigEntity NVC_LIMIT_VALUE_DAO = new NVConfigEntityLocal(
            "limit_value_dao",
            null,
            LimitValueDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            LimitValueDAO.class,
            SharedUtil.extractNVConfigs(LimitValueDAO.Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );


    public LimitValueDAO()
    {
        super(NVC_LIMIT_VALUE_DAO);
    }

    public LimitValueDAO(int value)
    {
        this();
        setValue(value);
    }
    
    public LimitValueDAO(int value, boolean exclusive)
    {
        this();
        setValue(value);
        setExclusive(exclusive);
    }
    
    public LimitValueDAO(LimitType limitType) {
        this();
        setLimitType(limitType);
    }

    /**
     * Returns the limit type.
     * @return
     */
    public LimitType getLimitType()
    {
        return lookupValue(Param.TYPE);
    }

    /**
     * Sets the limit type.
     * @param type
     */
    public void setLimitType(LimitType type)
    {
        setValue(Param.TYPE, type);
    }

    /**
     * Returns the value.
     * @return
     */
    public int getValue()
    {
        return lookupValue(Param.VALUE);
    }

    /**
     * Sets the value.
     * @param value
     */
    public void setValue(int value)
    {
        setValue(Param.VALUE, value);
    }

    /**
     * Checks if value is exclusive.
     * @return
     */
    public boolean isExclusive()
    {
        return lookupValue(Param.EXCLUSIVE);
    }

    /**
     * Sets value as exclusive.
     * @param exclusive
     */
    public void setExclusive(boolean exclusive)
    {
        setValue(Param.EXCLUSIVE, exclusive);
    }

    @Override
    public String toString()
    {
        return Integer.toString(getValue());
    }

}