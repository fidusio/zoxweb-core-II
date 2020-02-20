package org.zoxweb.server.flow;

import org.zoxweb.server.task.TaskSchedulerProcessor;
import org.zoxweb.server.task.TaskUtil;

import java.util.concurrent.atomic.AtomicLong;


public abstract class DefaultFlowProcessor<F>
    implements FlowProcessor<F>
{



    protected TaskSchedulerProcessor tsp;
    protected AtomicLong sequence = new AtomicLong(0);

    public DefaultFlowProcessor()
    {
        this(TaskUtil.getSimpleTaskScheduler());
    }

    public DefaultFlowProcessor(TaskSchedulerProcessor tsp)
    {
        this.tsp = tsp;
    }

    public void publish(FlowEvent<F> event)
    {
        event.setSequence(sequence.getAndIncrement());
        tsp.queue(0, event, this);
    }


}
