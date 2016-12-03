package org.zoxweb.server.net.security;



import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.zoxweb.shared.util.SharedUtil;

public class SSLUtil 
{
	
	
	
	
	private final SSLContext sslContext;
	
	public SSLUtil(SSLContext sslContext)
	{
		SharedUtil.checkIfNulls("Can't have a null sllContext", sslContext);
		this.sslContext = sslContext;
	}
	
	
	
	
	public SSLContext getSSLContext()
	{
		return sslContext;
	}
	
	
	public SSLSessionData create(boolean client)
	{
		SSLEngine sslEngine = sslContext.createSSLEngine();
		sslEngine.setUseClientMode(client);
		SSLSessionData ret = new SSLSessionData(sslEngine);
		
		return ret;
	}
}
