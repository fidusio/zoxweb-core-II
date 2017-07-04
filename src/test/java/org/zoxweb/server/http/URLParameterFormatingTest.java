package org.zoxweb.server.http;

import org.junit.Before;
import org.junit.Test;
import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.http.HTTPParameterFormatter;
import org.zoxweb.shared.util.NVPair;

public class URLParameterFormatingTest
{
	HTTPMessageConfig hcc = new HTTPMessageConfig();
	
	@Before
	public void setUp()
	{
		hcc.getParameters().add(new NVPair("address", "www.yahoo.com"));
		hcc.getParameters().add(new NVPair("port", "343"));
		hcc.getParameters().add(new NVPair("widget", "tata"));
		hcc.getParameters().add(new NVPair("a", "a"));
		hcc.getParameters().add(new NVPair("z", "z"));
		hcc.getParameters().add(new NVPair("k", "k"));
		hcc.getParameters().add(new NVPair((String)null, "v"));
	}
	
	@Test
	public void parametersURL()
	{
	
		StringBuilder sb = new StringBuilder("batata&");
	
		System.out.println(HTTPParameterFormatter.URL_ENCODED.format(sb, hcc.getParameters().values()));
		
	}
	
	@Test
	public void parametersURI()
	{
	
		StringBuilder sb = new StringBuilder("batata/");
	
		System.out.println(HTTPParameterFormatter.URI_REST_ENCODED.format(sb, hcc.getParameters().values()));
		
	}
	
	
	@Test
	public void all()
	{
		for(HTTPParameterFormatter hpe : HTTPParameterFormatter.values())
		{
			System.out.println(hpe.format(null, hcc.getParameters().values()));
		}
		System.out.println("===============================================================================");
	}
}
