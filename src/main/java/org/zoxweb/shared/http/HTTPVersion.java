package org.zoxweb.shared.http;

import org.zoxweb.shared.util.GetValue;
import org.zoxweb.shared.util.SharedStringUtil;

public enum HTTPVersion
	implements GetValue<String>
{
	HTTP_1_0("HTTP/1.0"),
	HTTP_1_1("HTTP/1.1")
	;
	
	private final String value;
	HTTPVersion(String value)
	{
		this.value = value;
	}
	
	@Override
	public String getValue() 
	{
		// TODO Auto-generated method stub
		return value;
	}
	
	public String toString()
	{
		return getValue();
	}
	
	public static HTTPVersion lookup(String val)
	{
		val = SharedStringUtil.trimOrNull(val);
		
		if (val != null)
		{
			val = val.toUpperCase();
			for(HTTPVersion ret : HTTPVersion.values())
			{
				if(ret.getValue().equals(val))
				{
					return ret;
				}
			}
		}
		
		
		return null;
	}
	
}
