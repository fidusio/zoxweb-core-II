package org.zoxweb.shared.data.events;

import java.util.EventListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;

public abstract class EventListenerManager<L extends EventListener,E extends EventObject>
{
	protected Set<L> set = new HashSet<L>();
	
	
	public synchronized void addEventListener(L listener)
	{
		if (listener != null)
        {
            set.add(listener);
        }
	}
	
	
	public synchronized void removeEventListener(L listener)
	{
		if (listener != null)
        {
            set.remove(listener);
        }
	}
	
	public synchronized EventListener[] getAllListeners()
	{
		return set.toArray(new EventListener[0]);
	}
	
	public abstract void dispatch(E event);

}