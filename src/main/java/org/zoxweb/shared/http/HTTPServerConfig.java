package org.zoxweb.shared.http;


import org.zoxweb.shared.data.PropertyDAO;
import org.zoxweb.shared.net.ConnectionConfig;
import org.zoxweb.shared.util.*;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

import java.util.LinkedHashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class HTTPServerConfig
extends PropertyDAO
{
  public enum Param
      implements GetNVConfig
  {
    APPLICATION_CONFIG_VAR(NVConfigManager.createNVConfig("application_conf_var", "Application Config Variable", "ApplicationConfVar", false, true, String.class)),
    CONNECTIONS(NVConfigManager.createNVConfigEntity("connections", "Connections configurations", "Connections", false, true, ConnectionConfig.class, ArrayType.GET_NAME_MAP)),
    ENDPOINTS(NVConfigManager.createNVConfigEntity("endpoints", "Endpoints", "Endpoints", false, true, HTTPEndPoint.class, ArrayType.GET_NAME_MAP)),

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

  public static final NVConfigEntity NVC_HTTP_SERVER_CONFIG = new NVConfigEntityLocal("HTTP_SERVER_CONFIG",
      null,
      "HTTPServerConfig", 
      true,
      false,
      false,
      false,
      HTTPServerConfig.class,
      SharedUtil.extractNVConfigs(Param.values()),
      null,
      false,
      PropertyDAO.NVC_PROPERTY_DAO);


  NVEntityGetNameMap con;

  public HTTPServerConfig()
  {
    super(NVC_HTTP_SERVER_CONFIG);
  }


  @SuppressWarnings("unchecked")
  public ConnectionConfig[] getConnectionConfigs()
  {
    ArrayValues<NVEntity> av = (ArrayValues<NVEntity>) lookup(Param.CONNECTIONS);
    return (ConnectionConfig[])av.values(new ConnectionConfig[0]);
  }

  public HTTPEndPoint[] getEndPoints()
  {
    ArrayValues<NVEntity> av = (ArrayValues<NVEntity>) lookup(Param.ENDPOINTS);
    return (HTTPEndPoint[])av.values(new HTTPEndPoint[0]);
  }

  public String getApplicationConfVar()
  {
    return lookupValue(Param.APPLICATION_CONFIG_VAR);
  }


  public void setApplicationConfVar(String appConVar)
  {
    setValue(Param.APPLICATION_CONFIG_VAR, appConVar);
  }




}
