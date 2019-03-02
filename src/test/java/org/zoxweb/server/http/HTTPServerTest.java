package org.zoxweb.server.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.zoxweb.server.task.TaskUtil;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.SharedStringUtil;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

@SuppressWarnings("restriction")
public class HTTPServerTest {


  private final static Logger LOG = Logger.getLogger(HTTPServerTest.class.getName());

  static class  ContextHandler implements HttpHandler
  {
      public void handle(HttpExchange he) throws IOException {

          InputStream is = he.getRequestBody();
          is.close();


          NVGenericMap nvgm = new NVGenericMap();
          nvgm.add("context", he.getHttpContext().getPath());
          String json  = GSONUtil.DEFAULT_GSON.toJson(nvgm);
          byte[] response = SharedStringUtil.getBytes(json);
          he.getResponseHeaders().add(HTTPHeaderName.CONTENT_TYPE.getName(), HTTPMimeType.APPLICATION_JSON.getValue());
          he.getResponseHeaders().add(HTTPHeaderName.CONTENT_TYPE.getName(), "charset=utf-8");
          he.sendResponseHeaders(200, response.length);
          OutputStream os = he.getResponseBody();
          os.write(response);
          os.close();
      }
  }

  public static void main(String ...args)
  {
      try
      {
          int index = 0;
          int port = Integer.parseInt(args[index++]);
          HttpServer server = HttpServer.create(new InetSocketAddress(port), 250);
          for (; index < args.length; index++) {
              server.createContext("/"+args[index], new ContextHandler());
          }
          server.setExecutor(TaskUtil.getDefaultTaskProcessor());
          server.start();

          System.out.println("server started @ " + server.getAddress());

      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
}
