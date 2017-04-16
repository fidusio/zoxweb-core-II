package org.zoxweb.shared.data.events;

import java.util.EventListener;

/**
 * The String token listener interface.
 */
public interface StringTokenListener 
    extends EventListener
{
    /**
     * Process given StringTokenEvent.
     * @param ste
     */
	public void processStringTokenEvent(StringTokenEvent ste);

}