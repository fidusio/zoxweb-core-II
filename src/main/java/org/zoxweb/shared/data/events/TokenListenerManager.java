package org.zoxweb.shared.data.events;

import java.util.EventListener;

/**
 * The token listener manager class.
 */
public class TokenListenerManager extends EventListenerManager<StringTokenListener, StringTokenEvent> 
{

	@Override
	public void dispatch(StringTokenEvent event) 
	{
		EventListener[] all = getAllListeners();

		for (EventListener el : all)
		{
			((StringTokenListener)el).processStringTokenEvent(event);
		}
	}

}
