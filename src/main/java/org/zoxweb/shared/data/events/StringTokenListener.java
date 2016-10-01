package org.zoxweb.shared.data.events;

import java.util.EventListener;

public interface StringTokenListener 
extends EventListener
{
	public void processStringTokenEvent(StringTokenEvent ste);
}
