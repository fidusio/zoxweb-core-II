package org.zoxweb.server.net.security;

import java.util.concurrent.Executor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.zoxweb.shared.util.SharedUtil;

public class SSLSessionDataFactory 
{
	private SSLContext sslContext;
	private volatile Executor executor;

	public SSLSessionDataFactory()
	{
		
	}
	
	public SSLSessionDataFactory(SSLContext sslContext, Executor executor)
	{
		setSSLContext(sslContext);
		setExecutor(executor);
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
	
	public Executor getExecutor() 
	{
		return executor;
	}

	public void setExecutor(Executor executor)
	{
		this.executor = executor;
	}
	
	public SSLSessionData create(boolean client)
	{
		SSLEngine sslEngine = sslContext.createSSLEngine();
		sslEngine.setUseClientMode(client);
		sslEngine.setWantClientAuth(true);
		SSLSessionData ret = new SSLSessionData(sslEngine, executor);
		
		return ret;
	}

}