package org.zoxweb.server.http;


import java.net.URI;


import javax.websocket.ClientEndpoint;

import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.zoxweb.server.io.IOUtil;


@ClientEndpoint(configurator = WSConfigurator.class)
public class WebSocketTest  {

    private static Object waitLock = new Object();
    
    
    
    @OnMessage
    public void onMessage(String message) 
    {

       System.out.println("Received msg: "+message);        

    }
    
   
    

	private static void  wait4TerminateSignal()

	{

		synchronized(waitLock)

		{
			try {
			

				waitLock.wait();

			} catch (InterruptedException e) {    

			}
		}
	}

	public static void main(String[] args) {
	
		WebSocketContainer container=null;//
		
		Session session=null;
		
		  try{
		
		   //Tyrus is plugged via ServiceLoader API. See notes above
		
		   container = ContainerProvider.getWebSocketContainer();
		   
		  
		  
		   
		   
		
		//WS1 is the context-root of my web.app 
		
		//ratesrv is the  path given in the ServerEndPoint annotation on server implementation
		URI uri = URI.create("ws://localhost/fs/fs/fs-websocket");
//		ArrayList<String> vals = new ArrayList<>();
//		vals.add("8A0C54190C227A7B1A1057EC43D08A9E");
//		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
//		map.put("JSESSIONID", vals);
		
		//CookieManager.getDefault().put(uri, map);
		
		session=container.connectToServer(WebSocketTest.class, uri); 
		
		   wait4TerminateSignal();
		
		  } catch (Exception e) {
		
		   e.printStackTrace();
		
		  }
		
		  finally
		  {
			  IOUtil.close(session);
		  } 
	
	 }

}