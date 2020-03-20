package org.zoxweb.shared.data.events;

import java.util.EventListener;
import java.util.EventObject;

public interface EventHandlerListener<E extends EventObject>
    extends EventListener
{
    void handleEvent(E event);
}
