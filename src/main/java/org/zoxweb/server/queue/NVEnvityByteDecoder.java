package org.zoxweb.server.queue;

import org.zoxweb.shared.util.ValueGetter;

public class NVEnvityByteDecoder implements ValueGetter<byte[], NVEntityQueueEvent> {

	@Override
	public NVEntityQueueEvent getValue(byte[] input) 
	{
		// TODO Auto-generated method stub
		return new NVEntityQueueEvent(this, input);
	}

}
