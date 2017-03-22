package org.zoxweb.shared.queue;

import java.util.EventListener;


public interface QueueListener<E extends QueueEvent>
extends EventListener
{
	public void incomingEvent(E event);		
}
