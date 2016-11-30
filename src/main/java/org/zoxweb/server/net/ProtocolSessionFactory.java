package org.zoxweb.server.net;

import javax.net.ssl.SSLContext;

public interface ProtocolSessionFactory<P extends ProtocolSessionProcessor>
{
	P newInstance();
	
	boolean isBlocking();
	
	
	
	SSLContext getSSLContext();
	
	
}
