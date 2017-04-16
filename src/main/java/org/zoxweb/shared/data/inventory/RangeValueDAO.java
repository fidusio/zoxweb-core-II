package org.zoxweb.shared.data.inventory;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.*;

/**
 * Created on 4/16/17
 */
public class RangeValueDAO
    extends SetNameDescriptionDAO
{

    public enum LimitType {
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

        public NVConfig getNVConfig()
        {
            return nvc;
        }
    }

    public static final NVConfigEntity NVC_RANGE_VALUE_DAO = new NVConfigEntityLocal(
            "range_value_dao",
            null,
            RangeValueDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            RangeValueDAO.class,
            SharedUtil.extractNVConfigs(RangeValueDAO.Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );


    public RangeValueDAO()
    {
        super(NVC_RANGE_VALUE_DAO);
    }

    public LimitType getLimitType() {
        return lookupValue(Param.TYPE);
    }

    public void setLimitType(LimitType type) {
        setValue(Param.TYPE, type);
    }

    public int getValue() {
        return lookupValue(Param.VALUE);
    }

    public void setValue(int value) {
        setValue(Param.VALUE, value);
    }

    public boolean isExclusive() {
        return lookupValue(Param.EXCLUSIVE);
    }

    public void setExclusive(boolean exclusive) {
        setValue(Param.EXCLUSIVE, exclusive);
    }

    @Override
    public String toString() {
        if (getValue() > 0) {
            return "" + getValue();
        }

        return "";
    }

}