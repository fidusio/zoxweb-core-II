package org.zoxweb.server.http;


import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.logging.Logger;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.logging.LoggerUtil;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPStatusCode;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.SharedStringUtil;

@SuppressWarnings("restriction")
public class HTTPServerTest {

  static {
    LoggerUtil.enableDefaultLogger("org.zoxweb");
  }
  private final static Logger log = Logger.getLogger(HTTPServerTest.class.getName());

  static class ContextHandler implements HttpHandler {

    public void handle(HttpExchange he) throws IOException {
      log.info("user agent:" +  he.getRequestHeaders().entrySet());
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

    private String routDir;
    public FileHandler(String rootDir)
    {
      this.routDir = rootDir;
    }

    public void handle(HttpExchange he) throws IOException {
      

      String path = he.getHttpContext().getPath();
      
      URI uri = he.getRequestURI();
      log.info("user agent:" +  he.getRequestHeaders());
      log.info("path: " + path);
      log.info("URI: " +  uri.getPath());
      log.info("Remote IP:" + he.getRemoteAddress());
      try {
        String filename = uri.getPath().substring(path.length(), uri.getPath().length());
        log.info("filename: " + filename);
        HTTPMimeType mime = HTTPMimeType.lookupByExtenstion(filename);
        log.info("mime type: " + mime);

        if(mime != null)
        he.getResponseHeaders()
            .add(HTTPHeaderName.CONTENT_TYPE.getName(), mime.getValue());

        File file = new File(routDir, filename);
        if (!file.exists() || !file.isFile())
          throw new FileNotFoundException();


        he.sendResponseHeaders(HTTPStatusCode.OK.CODE, file.length());

        IOUtil.relayStreams(new FileInputStream(file), he.getResponseBody(), true);

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
      HttpContext hc = server.createContext("/.well-known/pki-validation/", new FileHandler("/public"));
//      hc.setAuthenticator(new Authenticator() {
//        @Override
//        public Result authenticate(HttpExchange httpExchange) {
//          return null;
//        }
//      });
      //server.createContext("/toto", new FileHandler());
      server.setExecutor(TaskUtil.getDefaultTaskProcessor());

      log.info(hc.getPath());
      server.start();

      log.info("server started @ " + server.getAddress());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
