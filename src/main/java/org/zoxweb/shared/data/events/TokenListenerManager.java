package org.zoxweb.shared.data.events;

import java.util.EventListener;

public class TokenListenerManager extends EventListenerManager<StringTokenListener, StringTokenEvent> 
{

	@Override
	public void dispatch(StringTokenEvent event) 
	{
		EventListener all[] = getAllListeners();
		for (EventListener el : all)
		{
			((StringTokenListener)el).processStringTokenEvent(event);
		}
	}

}
