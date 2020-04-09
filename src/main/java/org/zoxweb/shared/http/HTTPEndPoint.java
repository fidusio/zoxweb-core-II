package org.zoxweb.shared.http;

import org.zoxweb.shared.data.DataConst;
import org.zoxweb.shared.data.PropertyDAO;
import org.zoxweb.shared.data.TimeStampDAO;
import org.zoxweb.shared.util.*;

import java.util.Arrays;
import java.util.List;

public class HTTPEndPoint
extends PropertyDAO
{
    public enum Param
            implements GetNVConfig
    {
        BEAN(NVConfigManager.createNVConfig("bean", "Bean class name", "Bean", false, true, String.class)),
        PERMISSIONS(NVConfigManager.createNVConfig("permissions", "Permission tokens", "Permissions", false, true, NVStringList.class)),
        ROLES(NVConfigManager.createNVConfig("roles", "Role tokens", "Roles", false, true, NVStringList.class)),
        PATHS(NVConfigManager.createNVConfig("paths", "Paths", "Paths", false, true, NVStringList.class)),
        METHODS(NVConfigManager.createNVConfig("methods", "HTTP Methods", "Methods", false, true, HTTPMethod[].class)),
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



    /**
     * This NVConfigEntity type constant is set to an instantiation of a NVConfigEntityLocal object based on DataContentDAO.
     */
    public static final NVConfigEntity NVC_HTTP_END_POINT = new NVConfigEntityLocal("http_end_point",
            null,
            "HTTPEndPoint",
            true,
            false,
            false,
            false,
            HTTPEndPoint.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            PropertyDAO.NVC_PROPERTY_DAO);


    public HTTPEndPoint()
    {
        super(NVC_HTTP_END_POINT);
    }

    public String getName()
    {
        String ret = lookupValue(DataConst.DataParam.NAME);
        if(ret == null)
            ret = getBean();
        return ret;
    }

    public String getBean()
    {
        return lookupValue(Param.BEAN);
    }

    public void setBean(String beanClassName)
    {
        setValue(Param.BEAN, beanClassName);
    }

    public String[] getPermissions()
    {
        return ((NVStringList)lookup(Param.PERMISSIONS)).getValues();
    }

    public void setPermissions(String ...permissions)
    {
        ((NVStringList)lookup(Param.PERMISSIONS)).setValues(permissions);
    }

    public String[] getRoles()
    {
        return ((NVStringList)lookup(Param.ROLES)).getValues();
    }

    public void setRoles(String ...roles)
    {
        ((NVStringList)lookup(Param.ROLES)).setValues(roles);
    }


    public String[] getPaths()
    {
        return ((NVStringList)lookup(Param.PATHS)).getValues();
    }

    public void setPaths(String ...paths)
    {
        ((NVStringList)lookup(Param.PATHS)).setValues(paths);
    }

    public HTTPMethod[] getMethods()
    {
        List<Enum<?>> value =(List<Enum<?>>)lookup(Param.METHODS).getValue();

        return (HTTPMethod[]) value.toArray(new HTTPMethod[0]);
    }

    public void setMethods(HTTPMethod ...methods)
    {
        List<Enum<?>> value =(List<Enum<?>>)lookup(Param.METHODS).getValue();
        value.clear();
        value.addAll(Arrays.asList(methods));
    }
}
