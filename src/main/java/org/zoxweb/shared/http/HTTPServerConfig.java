package org.zoxweb.shared.http;

import java.util.List;
import org.zoxweb.shared.data.PropertyDAO;
import org.zoxweb.shared.net.ConnectionConfig;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;
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

    CONNECTIONS(NVConfigManager.createNVConfigEntity("connections", "Protocol schemes", "Schemes", false, true, ConnectionConfig.class, ArrayType.GET_NAME_MAP)),

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


  public ArrayValues<ConnectionConfig> getConnectionConfigs()
  {
    return (ArrayValues<ConnectionConfig>) lookup(Param.CONNECTIONS);
  }



}
