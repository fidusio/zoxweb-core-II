package org.zoxweb.server.http;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.zoxweb.server.io.IOUtil;

import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.util.DataHandler;
import org.zoxweb.shared.util.SharedStringUtil;



@ClientEndpoint
public class WebSocketClient {
    Session userSession = null;
    private DataHandler<WebSocketClient, String> dataHandler = null;
    private AutoCloseable closeHandler;
    
    
    
    public WebSocketClient(HTTPMessageConfig hmc, DataHandler<WebSocketClient, String> dataHandler, AutoCloseable closeHandler)
        throws DeploymentException, IOException, URISyntaxException
    {
      
      this.closeHandler  = closeHandler;
      setDataHandler(dataHandler);
      String endpoint = SharedStringUtil.concat(hmc.getURL(), hmc.getURI(), "/");
      WebSocketContainer container = ContainerProvider
          .getWebSocketContainer();
      container.connectToServer(this, new URI(endpoint));
      
      
    }
    

    public WebSocketClient(URI endpointURI, DataHandler<WebSocketClient, String> dataHandler, AutoCloseable closeHandler)
        throws DeploymentException, IOException
    {    
      this.closeHandler  = closeHandler;
      setDataHandler(dataHandler);
      WebSocketContainer container = ContainerProvider
          .getWebSocketContainer();
      container.connectToServer(this, endpointURI);
       
    }

    /**
     * Callback hook for Connection open events.
     * 
     * @param userSession
     *            the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     * 
     * @param userSession
     *            the userSession which is getting closed.
     * @param reason
     *            the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason)
    {
        IOUtil.close(closeHandler);
        //this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a
     * client send a message.
     * 
     * @param message
     *            The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (dataHandler != null)
          dataHandler.handleData(this, message);
    }

    /**
     * register message handler
     * 
     * @param message
     */
    public void setDataHandler(DataHandler<WebSocketClient, String> dh) {
        this.dataHandler = dh;
    }

    /**
     * Send a message.
     * 
     * @param user
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
    
    /*
    public static void main(String ...args)
    {
      
      
      TaskUtil.getDefaultTaskProcessor();
      try
      {
        int index = 0;
        WebSocketClient wsc = new WebSocketClient(new URI(args[index]),
            new DataHandler<WebSocketClient, String>()
            {

              @Override
              public void handleData(WebSocketClient source, String data) {
                // TODO Auto-generated method stub
                System.out.println(data);
              }
          
            },
            new Closeable() {

              @Override
              public void close() throws IOException {
                // TODO Auto-generated method stub
                TaskUtil.getDefaultTaskProcessor().close();
              }
              
            }
            );
        
        for (;index < args.length; index++)
        {
          wsc.sendMessage(args[index]);
        }
      }
      
      catch(Exception e)
      {
        e.printStackTrace();
        TaskUtil.getDefaultTaskProcessor().close();
      }
      
      
    }
    */
}