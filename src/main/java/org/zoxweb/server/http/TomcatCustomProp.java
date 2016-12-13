package org.zoxweb.server.http;

public class TomcatCustomProp 
implements org.apache.tomcat.util.IntrospectionUtils.PropertySource
{

	@Override
	public String getProperty(String arg) {
		
		if ("cus.ks.password".equals(arg))
		{
			return "password";
		}
		
		
		
		// TODO Auto-generated method stub
		return arg;
	}

}
