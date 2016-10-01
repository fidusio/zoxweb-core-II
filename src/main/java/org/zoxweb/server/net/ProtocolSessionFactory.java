package org.zoxweb.server.net;

public interface ProtocolSessionFactory<P extends ProtocolSessionProcessor> 
{
	P newInstance();
	
	boolean isBlocking();
}
