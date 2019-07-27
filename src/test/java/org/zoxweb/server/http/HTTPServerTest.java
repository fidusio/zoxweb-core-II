package org.zoxweb.server.http;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.logging.Logger;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPStatusCode;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.SharedStringUtil;

@SuppressWarnings("restriction")
public class HTTPServerTest {


  private final static Logger log = Logger.getLogger(HTTPServerTest.class.getName());

  static class ContextHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {

      InputStream is = he.getRequestBody();
      is.close();

      NVGenericMap nvgm = new NVGenericMap();
      nvgm.add("context", he.getHttpContext().getPath());
      String json = GSONUtil.DEFAULT_GSON.toJson(nvgm);
      byte[] response = SharedStringUtil.getBytes(json);
      he.getResponseHeaders()
          .add(HTTPHeaderName.CONTENT_TYPE.getName(), HTTPMimeType.APPLICATION_JSON.getValue());
      he.getResponseHeaders().add(HTTPHeaderName.CONTENT_TYPE.getName(), "charset=utf-8");
      he.sendResponseHeaders(200, response.length);
      OutputStream os = he.getResponseBody();
      os.write(response);
      os.close();
    }
  }


  static class FileHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {
      

      String path = he.getHttpContext().getPath();
      
      URI uri = he.getRequestURI();
      log.info("path: " + path);
      log.info("URI: " +  uri.getPath());
      try {
        String filename = uri.getPath().substring(path.length(), uri.getPath().length());
        log.info("filename: " + filename);
        HTTPMimeType mime = HTTPMimeType.lookupByExtenstion(filename);
        log.info("mime type: " + mime);
        
        he.sendResponseHeaders(HTTPStatusCode.BAD_REQUEST.CODE, 0);
        he.getResponseBody().close();
      }
      catch(Exception e)
      {
        e.printStackTrace();
        he.sendResponseHeaders(HTTPStatusCode.BAD_REQUEST.CODE, 0);
      }
      
     

    }
  }


  public static void main(String... args) {
    try {
      int index = 0;
      int port = Integer.parseInt(args[index++]);
      HttpServer server = HttpServer.create(new InetSocketAddress(port), 250);
      for (; index < args.length; index++) {
        server.createContext("/" + args[index], new ContextHandler());
      }
      HttpContext hc = server.createContext("/.well-known/pki-validation/", new FileHandler());
      server.createContext("/toto", new FileHandler());
      server.setExecutor(TaskUtil.getDefaultTaskProcessor());

      log.info(hc.getPath());
      server.start();

      log.info("server started @ " + server.getAddress());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
