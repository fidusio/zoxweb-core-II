package org.zoxweb.server.http.handler;

import java.io.IOException;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.SimpleErrorMessage;
import org.zoxweb.shared.data.SimpleMessage;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPStatusCode;
import org.zoxweb.shared.util.SharedStringUtil;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HTTPHandlerUtil {

  private HTTPHandlerUtil()
  {
    
  }
  
  public static void sendErrorMessage(HttpExchange he, HTTPStatusCode hsc, String msg) throws IOException
  {
    sendSimpleMessage(he, hsc, new SimpleErrorMessage(msg, hsc.CODE, hsc.REASON));
  }
  
  public static void sendSimpleMessage(HttpExchange he, HTTPStatusCode hsc, SimpleMessage simpleMessage) throws IOException
  {
    String message = GSONUtil.toJSON(simpleMessage, false, false, false);
    byte buffer[] = SharedStringUtil.getBytes(message);
    he.getResponseHeaders()
            .add(HTTPHeaderName.CONTENT_TYPE.getName(), HTTPMimeType.APPLICATION_JSON.getValue());
    he.sendResponseHeaders(hsc.CODE, buffer.length);
    he.getResponseBody().write(buffer);
  }
}
