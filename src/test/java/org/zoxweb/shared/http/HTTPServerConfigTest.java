package org.zoxweb.shared.http;


import java.io.IOException;
import java.util.Arrays;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.util.ArrayValues;

public class HTTPServerConfigTest {

  private static String ENV_VAR;
  @BeforeAll
  public static void init()
  {
    ENV_VAR = System.getenv("ENV_VAR");
  }

  @Test
  public void valueChecks() throws IOException {
    System.out.println(System.getProperties());
    HTTPServerConfig hsc = GSONUtil.fromJSON(IOUtil.inputStreamToString(ENV_VAR), HTTPServerConfig.class);
    System.out.println(hsc);
    System.out.println(hsc.getConnectionConfigs());

    ArrayValues<HTTPEndPoint> endPoints = hsc.getEndPoints();
    for (HTTPEndPoint ep : endPoints.values(new HTTPEndPoint[0]))
    {
      System.out.println(ep.getMethods().getClass() + " " + Arrays.toString(ep.getMethods()));
    }

  }


}
