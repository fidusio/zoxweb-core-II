package org.zoxweb.server.http;

public class TomcatCustomProp 
implements org.apache.tomcat.util.IntrospectionUtils.PropertySource
{

	@Override
	public String getProperty(String arg) {
		if ("zw.password".equals(arg))
		{
			return "password";
		}
		
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$:"+arg);
		// TODO Auto-generated method stub
		return arg;
	}

}
