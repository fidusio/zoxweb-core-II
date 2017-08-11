package org.zoxweb.shared.data;

import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.filters.AppIDNameFilter;
import org.zoxweb.shared.util.AppID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.SubjectID;

/**
 * Created on 7/3/17
 */
@SuppressWarnings("serial")
public class AppIDDAO
    extends SetNameDescriptionDAO
    implements AppID<String>, SubjectID<String>
{

    public enum Param
        implements GetNVConfig
    {
        APP_ID(NVConfigManager.createNVConfig("app_id", "App ID","AddID", true, false, String.class)),
        DOMAIN_ID(NVConfigManager.createNVConfig("domain_id", "Domain ID", "Domain ID", true, true, false, String.class, FilterType.DOMAIN)),
        SUBJECT_ID(NVConfigManager.createNVConfig("subject_id", "Subject ID", "Subject ID", true, false, true, String.class, AppIDNameFilter.SINGLETON)),

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

    public AppIDDAO(String domainID, String appID)
    {
        this();
        setDomainAppID(domainID, appID);
    }

    @Override
    public String getDomainID()
    {
        return lookupValue(Param.DOMAIN_ID);
    }

    @Override
    public void setDomainID(String domainID)
            throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getAppID()
    {
        return lookupValue(Param.APP_ID);
    }

    @Override
    public void setAppID(String appID)
            throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getSubjectID()
    {
        return lookupValue(Param.SUBJECT_ID);
    }

    @Override
    public void setSubjectID(String subjectID)
            throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    public synchronized void setDomainAppID(String domainID, String appID) {
        setValue(Param.DOMAIN_ID, domainID);
        setValue(Param.APP_ID, appID);
        setValue(Param.SUBJECT_ID, getDomainID() + ":" + getAppID());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj != null && obj instanceof AppIDDAO) {
            AppIDDAO appIDDAO = (AppIDDAO) obj;

            if (SharedStringUtil.equals(getDomainID(), appIDDAO.getDomainID(), true)
                    && SharedStringUtil.equals(getAppID(), appIDDAO.getAppID(), true)) {
                return true;
            }
        }


        return false;
    }

    @Override
    public int hashCode() {
        if (getDomainID() != null && getAppID() != null) {
            return 31 * getDomainID().hashCode() + getAppID().hashCode();
        }

        if (getDomainID() != null) {
            return getDomainID().hashCode();
        }

        if (getAppID() != null) {
            return getAppID().hashCode();
        }

        return super.hashCode();
    }


}