package org.zoxweb.shared.http;

import java.util.List;
import org.zoxweb.shared.data.PropertyDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.NVIntList;
import org.zoxweb.shared.util.SharedUtil;


@SuppressWarnings("serial")
public class HTTPServerConfig
extends PropertyDAO
{
  public enum Param
      implements GetNVConfig
  {
    BACK_LOG(NVConfigManager.createNVConfig("ports_back_log", "Port backlog", "Backlog", false, true, int.class)),
    HTTP_PORTS(NVConfigManager.createNVConfig("http_ports", "List of http ports", "HTTPPorts", false, true, int[].class)),
    HTTPS_PORTS(NVConfigManager.createNVConfig("https_ports", "List of https ports", "HTTPSPorts", false, true, int[].class)),
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

  public HTTPServerConfig()
  {
    super(NVC_HTTP_SERVER_CONFIG);
  }


  public int getPortsBacklog()
  {
    return lookupValue(Param.BACK_LOG);
  }

  public void setPortsBacklog(int backlog)
  {
    setValue(Param.BACK_LOG, backlog);
  }

  public int[] getHTTPPorts()
  {
    NVIntList ret  = (NVIntList)lookup(Param.HTTP_PORTS);
    return ret.getValues();
  }
  public void setHTTPPorts(int[] ports)
  {
    NVIntList ret  = (NVIntList)lookup(Param.HTTP_PORTS);
    ret.setValues(ports);
  }

  public int[] getHTTPSPorts()
  {
    NVIntList ret  = (NVIntList)lookup(Param.HTTPS_PORTS);
    return ret.getValues();
  }

  public void setHTTPSPorts(int[] ports)
  {
    NVIntList ret  = (NVIntList)lookup(Param.HTTPS_PORTS);
    ret.setValues(ports);
  }


}
