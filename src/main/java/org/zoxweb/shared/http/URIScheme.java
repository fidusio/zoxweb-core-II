package org.zoxweb.shared.http;

import org.zoxweb.shared.util.GetNameValue;

public enum URIScheme
implements GetNameValue<Integer>
{
	
	// WARNING: it is crucial to define https before http otherwise the match will never detect https
	HTTPS("https", 443),
	HTTP("http", 80),
	FTP("ftp", 23),
	FILE("file", -1),
	MAIL_TO("mailto", -1),
	DATA("data", -1),
	
	
	
	;
	private String name;
	private int defaultPort;
	
	URIScheme(String name, int port)
	{
		this.name= name;
		this.defaultPort = port;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
	public static URIScheme match(String uri)
	{
		if (uri != null)
		{
			uri = uri.toLowerCase().trim();

			for (URIScheme us: URIScheme.values())
			{
				if (uri.startsWith(us.getName()))
                {
                    return us;
                }
			}
		}
		
		return null;
	}
	@Override
	public Integer getValue() {
		// TODO Auto-generated method stub
		return defaultPort;
	}
}
