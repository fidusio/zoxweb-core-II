package org.zoxweb.server.util;

import org.zoxweb.server.task.TaskSchedulerProcessor;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.shared.data.events.EventHandlerListener;
import org.zoxweb.shared.data.events.EventListenerManager;

import java.util.EventListener;
import java.util.EventObject;

public class DefaultEvenManager
extends EventListenerManager
{

    protected final TaskSchedulerProcessor tsp;

    public DefaultEvenManager()
    {
        this(TaskUtil.getDefaultTaskScheduler());
    }
    public DefaultEvenManager(TaskSchedulerProcessor tsp)
    {
        this.tsp = tsp;
    }
    @Override
    public void dispatch(EventObject event, boolean async)
    {
		EventListener[] all = getAllListeners();

		for (EventListener el : all)
		{
		    if (async)
            {
                tsp.queue(0, new Runnable() {
                    @Override
                    public void run() {
                        ((EventHandlerListener) el).handleEvent(event);
                    }
                });
            }
		    else
            {
                ((EventHandlerListener) el).handleEvent(event);
            }
		}
	}
}
