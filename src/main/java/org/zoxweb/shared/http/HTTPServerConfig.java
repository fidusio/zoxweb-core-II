package org.zoxweb.shared.http;

import org.zoxweb.shared.data.PropertyDAO;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.SharedUtil;


@SuppressWarnings("serial")
public class HTTPServerConfig
extends PropertyDAO
{
  
  
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
}
