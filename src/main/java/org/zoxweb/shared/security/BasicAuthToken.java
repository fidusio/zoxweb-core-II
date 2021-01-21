package org.zoxweb.shared.security;


import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.*;

public class BasicAuthToken
        extends SetNameDescriptionDAO
        implements SubjectID<String>
{
    public enum Param
            implements GetNVConfig
    {
        USER(NVConfigManager.createNVConfig("user", "User name", "User", true, true, String.class)),
        PASSWORD(NVConfigManager.createNVConfig("password", "Password", "Password", true, true, String.class)),
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

    public static final NVConfigEntity NVC_BASIC_AUTH_TOKEN = new NVConfigEntityLocal(
            "basic_auth_token",
            null ,
            BasicAuthToken.class.getSimpleName(),
            true,
            false,
            false,
            false,
            BasicAuthToken.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );

    public BasicAuthToken()
    {
        super(NVC_BASIC_AUTH_TOKEN);
    }

    public String getUser()
    {
        return lookupValue(Param.USER);
    }
    public void setUser(String user)
    {
        setValue(Param.USER, user);
    }

    public String getPassword()
    {
        return lookupValue(Param.PASSWORD);
    }

    public void setPassword(String password)
    {
        setValue(Param.PASSWORD, password);
    }

    @Override
    public String getSubjectID()
    {
        return getUser();
    }

    @Override
    public void setSubjectID(String subjectID)
    {
        setUser(subjectID);
    }
}
