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

import org.zoxweb.shared.data.*;
import org.zoxweb.shared.util.*;

public class RangeDAO
    extends SetNameDescriptionDAO
{

    public enum Param
        implements GetNVConfig
    {
        START(NVConfigManager.createNVConfigEntity("start", "Start range", "Start Range", true, true, RangeValueDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),
        END(NVConfigManager.createNVConfigEntity("end", "End range", "End Range", true, true, RangeValueDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),

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

    public static final NVConfigEntity NVC_RANGE_DAO = new NVConfigEntityLocal(
            "range_dao",
            null,
            RangeDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            RangeDAO.class,
            SharedUtil.extractNVConfigs(RangeDAO.Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );


    public RangeDAO()
    {
        super(NVC_RANGE_DAO);
    }

    public RangeDAO(RangeValueDAO start, RangeValueDAO end)
    {
        this();
        setStart(start);
        setEnd(end);
    }

    public RangeValueDAO getStart()
    {
        return lookupValue(Param.START);
    }

    public void setStart(RangeValueDAO start)
    {
        setValue(Param.START, start);
    }

    public RangeValueDAO getEnd()
    {
        return lookupValue(Param.END);
    }

    public void setEnd(RangeValueDAO end)
    {
        setValue(Param.END, end);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        if (getStart() != null)
        {
            if (getStart().isExclusive())
            {
                sb.append("(");
            }
            else
            {
                sb.append("[");
            }

            if (getStart().getValue() > 0)
            {
                sb.append(getStart().getValue());
            }
        }

        if (getEnd() != null)
        {
            sb.append(", ");

            if (getEnd().getValue() > 0)
            {
                sb.append(getEnd().getValue());
            }

            if (getEnd().isExclusive())
            {
                sb.append(")");
            }
            else
            {
                sb.append("]");
            }
        }

        return sb.toString();
    }

}