package org.zoxweb.server.http;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;

import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPMethod;
import org.zoxweb.shared.http.HTTPResponseData;

public class WSConfigurator 
extends ClientEndpointConfig.Configurator
{
	
	private String cookie()
	{
		try
		{
			HTTPMessageConfigInterface hmci = HTTPMessageConfig.createAndInit("http://localhost/fs/fs/api/login", null, HTTPMethod.POST);
			hmci.setUser("mnael@zoxweb.com");
			hmci.setPassword("W1r2l3ss");
			HTTPCall hc = new HTTPCall(hmci);
			HTTPResponseData rd = hc.sendRequest();
			List<String> c = rd.getResponseHeaders().get("Set-Cookie");
			String cookie = c.get(1).split(";")[0];
			System.out.println(cookie);
			return cookie;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	 public void beforeRequest(Map<String,List<String>> headers)
	    {
	        
	        System.out.println("This is called");
	        
			
			headers.put("Cookie", Arrays.asList(cookie()));
			
			
//			 ArrayList<String> wsKey = new ArrayList<>();
//			 wsKey.add("rrrZYU+wByMByP4zN/dHww==");
//			 headers.put("Sec-WebSocket-Key", wsKey);
			
	    }
}
