package org.zoxweb.shared.data;

import org.zoxweb.shared.util.*;

public class StatInfo
    extends PropertyDAO
{

    public enum Param
            implements GetNVConfig
    {
        APP_NAME(NVConfigManager.createNVConfig("app_name", "App name", "AppName", false, true, String.class)),
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


    public static final NVConfigEntity NVC_STAT_INFO = new NVConfigEntityLocal("stat_info",
            null,
            "StatInfo",
            true,
            false,
            false,
            false,
            StatInfo.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            PropertyDAO.NVC_PROPERTY_DAO);

    public StatInfo()
    {
        super(NVC_STAT_INFO);
    }

    protected StatInfo(NVConfigEntity nvce)
    {
        super(nvce);
    }

    public String getAppName()
    {
        return lookupValue(Param.APP_NAME);
    }

    public void setAppName(String appName)
    {
        setValue(Param.APP_NAME, appName);
    }
}
