package org.zoxweb.server.net.security;



import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.zoxweb.shared.util.SharedUtil;

public class SSLSessionDataFactory 
{
	
	
	
	
	private SSLContext sslContext;
	
	
	public SSLSessionDataFactory()
	{
		
	}
	
	public SSLSessionDataFactory(SSLContext sslContext)
	{
		setSSLContext(sslContext);
	}
	
	
	
	public void setSSLContext(SSLContext sslContext)
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
		sslEngine.setWantClientAuth(true);
		SSLSessionData ret = new SSLSessionData(sslEngine);
		
		return ret;
	}
}
