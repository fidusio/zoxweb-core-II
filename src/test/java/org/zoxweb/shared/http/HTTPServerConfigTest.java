package org.zoxweb.shared.http;

import java.io.IOException;
import org.junit.Test;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.util.GSONUtil;

public class HTTPServerConfigTest {

  private static final String jsonConfigFile = "http_server_config.json";
  @Test
  public void valueChecks() throws IOException {
    HTTPServerConfig hsc = GSONUtil.fromJSON(IOUtil.inputStreamToString(HTTPServerConfig.class.getClassLoader().getResourceAsStream(jsonConfigFile), true), HTTPServerConfig.class);
    System.out.println(hsc);
    System.out.println(hsc.getConnectionConfigs());
  }

}
