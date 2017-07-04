package org.zoxweb.shared.data;

import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.*;

/**
 * Created on 7/3/17
 */
public class AppIDDAO
    extends SetNameDescriptionDAO
    implements DomainID<String>
{

    public static final NVConfig NVC_APP_ID = NVConfigManager.createNVConfig("app_id", "App ID","AddID", true, false, String.class);

    public static final NVConfig NVC_DOMAIN_ID =  NVConfigManager.createNVConfig("domain_id", "The domain url identifier", "Domain ID", true, true, false, String.class, FilterType.DOMAIN);

    public static final NVConfigEntity NVC_APP_ID_DAO = new NVConfigEntityLocal(
            "app_id_dao",
            "AppIDDAO" ,
            AppIDDAO.class.getSimpleName(),
            true, false,
            false, false,
            AppIDDAO.class,
            SharedUtil.toNVConfigList(NVC_APP_ID, NVC_DOMAIN_ID),
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
        return lookupValue(NVC_APP_ID);
    }

    public void setAppID(String appID)
    {
        setValue(NVC_APP_ID, appID);
    }


    @Override
    public String getDomainID()
    {
        return lookupValue(NVC_DOMAIN_ID);
    }

    @Override
    public void setDomainID(String domainID)
    {
        setValue(NVC_DOMAIN_ID, domainID);
    }

}