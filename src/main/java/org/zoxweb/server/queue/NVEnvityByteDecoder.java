package org.zoxweb.server.queue;

import org.zoxweb.shared.util.DataDecoder;

public class NVEnvityByteDecoder implements DataDecoder<byte[], NVEntityQueueEvent> {

	@Override
	public NVEntityQueueEvent decode(byte[] input) 
	{
		// TODO Auto-generated method stub
		return new NVEntityQueueEvent(this, input);
	}

}
