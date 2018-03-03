package org.zoxweb.server.queue;

import org.zoxweb.shared.util.ValueDecoder;

public class NVEnvityByteDecoder implements ValueDecoder<byte[], NVEntityQueueEvent> {

	@Override
	public NVEntityQueueEvent decode(byte[] input) 
	{
		// TODO Auto-generated method stub
		return new NVEntityQueueEvent(this, input);
	}

}
