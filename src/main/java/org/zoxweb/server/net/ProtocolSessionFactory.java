package org.zoxweb.server.net;

import java.util.logging.Logger;

import org.zoxweb.server.net.security.SSLSessionDataFactory;

public interface ProtocolSessionFactory<P extends ProtocolSessionProcessor>
{
	public P newInstance();
	
	public boolean isBlocking();
	
	
	
	public SSLSessionDataFactory getIncomingSSLSessionDataFactory();
	public void setIncomingSSLSessionDataFactory(SSLSessionDataFactory sslSessionDataFactory);
	
	
	public InetFilterRulesManager getIncomingInetFilterRulesManager();
	public void setIncomingInetFilterRulesManager(InetFilterRulesManager incomingIFRM);
	
	public InetFilterRulesManager getOutgoingInetFilterRulesManager();
	public void setOutgoingInetFilterRulesManager(InetFilterRulesManager incomingIFRM);
	
	public Logger getLogger();
	
	public void setLogger(Logger logger);
}
