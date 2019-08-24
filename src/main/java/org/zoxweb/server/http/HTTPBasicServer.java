package org.zoxweb.server.http;

import java.io.IOException;
import org.zoxweb.shared.http.HTTPServerConfig;
import org.zoxweb.shared.util.DaemonController;

public class HTTPBasicServer
  implements DaemonController
{
  private HTTPServerConfig config;
  private boolean isClosed = false;

  public HTTPBasicServer(HTTPServerConfig config)
  {
    this.config = config;
  }

  public void start()
  {
    if (config != null)
    {
      isClosed = false;
    }
  }


  @Override
  public boolean isClosed() {
    return isClosed;
  }

  @Override
  public void close() throws IOException {

    if(!isClosed()) {

      isClosed = true;
    }
  }
}
