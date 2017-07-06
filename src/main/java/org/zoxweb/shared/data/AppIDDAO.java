package org.zoxweb.shared.data;

import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.*;

/**
 * Created on 7/3/17
 */
@SuppressWarnings("serial")
public class AppIDDAO
    extends SetNameDescriptionDAO
    implements DomainID<String>
{

    public enum Param
        implements GetNVConfig
    {
        APP_ID(NVConfigManager.createNVConfig("app_id", "App ID","AddID", true, false, String.class)),
        DOMAIN_ID(NVConfigManager.createNVConfig("domain_id", "The domain ID", "Domain ID", true, true, false, String.class, FilterType.DOMAIN))

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

    public static final NVConfigEntity NVC_APP_ID_DAO = new NVConfigEntityLocal(
            "app_id_dao",
            "AppIDDAO" ,
            AppIDDAO.class.getSimpleName(),
            true, false,
            false, false,
            AppIDDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );

    /**
     * The default constructor.
     */
    public AppIDDAO()
    {
        super(NVC_APP_ID_DAO);
    }

    public AppIDDAO(String appID, String domainID)
    {
        this();
        setAppID(appID);
        setDomainID(domainID);
    }


    public String getAppID()
    {
        return lookupValue(Param.APP_ID);
    }

    public void setAppID(String appID)
    {
        setValue(Param.APP_ID, appID);
    }


    @Override
    public String getDomainID()
    {
        return lookupValue(Param.DOMAIN_ID);
    }

    @Override
    public void setDomainID(String domainID)
    {
        setValue(Param.DOMAIN_ID, domainID);
    }

}