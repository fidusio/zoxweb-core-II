package org.zoxweb.server.net;



import org.zoxweb.server.net.security.SSLUtil;

public interface ProtocolSessionFactory<P extends ProtocolSessionProcessor>
{
	P newInstance();
	
	boolean isBlocking();
	
	
	
	SSLUtil getSSLUtil();
	
	
	public InetFilterRulesManager getIncomingInetFilterRulesManager();
	public void setIncomingInetFilterRulesManager(InetFilterRulesManager incomingIFRM);
	
	public InetFilterRulesManager getOutgoingInetFilterRulesManager();
	public void setOutgoingInetFilterRulesManager(InetFilterRulesManager incomingIFRM);
	
	
}
