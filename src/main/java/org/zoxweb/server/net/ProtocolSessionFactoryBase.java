package org.zoxweb.server.net;

import java.util.logging.Logger;

import org.zoxweb.server.net.security.SSLSessionDataFactory;

public abstract class ProtocolSessionFactoryBase<P extends ProtocolSessionProcessor>
	implements ProtocolSessionFactory<P>
{

	private InetFilterRulesManager incomingInetFilterRulesManager;
	private InetFilterRulesManager outgoingInetFilterRulesManager;
	protected SSLSessionDataFactory incomingSSLSessionFactory = null;
	private Logger logger;

	
//	protected ProtocolSessionFactoryBase(InetFilterRulesManager incoming, InetFilterRulesManager outgoing, SSLUtil sslUtil)
//	{
//		setIncomingInetFilterRulesManager(incoming);
//		setOutgoingInetFilterRulesManager(outgoing);
//		this.sslUtil = sslUtil;
//	}
	
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SSLSessionDataFactory getIncomingSSLSessionDataFactory() {
		// TODO Auto-generated method stub
		return incomingSSLSessionFactory;
	}
	
	
	public void setIncomingSSLSessionDataFactory(SSLSessionDataFactory sslsdf) {
		// TODO Auto-generated method stub
		incomingSSLSessionFactory = sslsdf;
	}

	@Override
	public InetFilterRulesManager getIncomingInetFilterRulesManager() {
		// TODO Auto-generated method stub
		return incomingInetFilterRulesManager;
	}

	@Override
	public void setIncomingInetFilterRulesManager(InetFilterRulesManager incomingIFRM) {
		// TODO Auto-generated method stub
		incomingInetFilterRulesManager = incomingIFRM;
	}
	
	
	@Override
	public InetFilterRulesManager getOutgoingInetFilterRulesManager() {
		// TODO Auto-generated method stub
		return outgoingInetFilterRulesManager;
	}

	@Override
	public void setOutgoingInetFilterRulesManager(InetFilterRulesManager incomingIFRM) {
		// TODO Auto-generated method stub
		outgoingInetFilterRulesManager = incomingIFRM;
	}

}
