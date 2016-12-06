package org.zoxweb.server.net;

import org.zoxweb.server.net.security.SSLUtil;

public abstract class ProtocolSessionFactoryBase<P extends ProtocolSessionProcessor>
	implements ProtocolSessionFactory<P>
{

	private InetFilterRulesManager incomingInetFilterRulesManager;
	private InetFilterRulesManager outgoingInetFilterRulesManager;
	protected SSLUtil sslUtil;

	
//	protected ProtocolSessionFactoryBase(InetFilterRulesManager incoming, InetFilterRulesManager outgoing, SSLUtil sslUtil)
//	{
//		setIncomingInetFilterRulesManager(incoming);
//		setOutgoingInetFilterRulesManager(outgoing);
//		this.sslUtil = sslUtil;
//	}
	
	
	@Override
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SSLUtil getSSLUtil() {
		// TODO Auto-generated method stub
		return sslUtil;
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
