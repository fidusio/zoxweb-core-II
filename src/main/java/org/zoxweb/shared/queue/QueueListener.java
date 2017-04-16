package org.zoxweb.shared.queue;

import java.util.EventListener;

/**
 *
 * @param <E>
 */
public interface QueueListener<E extends QueueEvent>
    extends EventListener
{
    /**
     * Process given event.
     * @param event
     */
	public void incomingEvent(E event);		

}