package org.zoxweb.server.flow;

import org.zoxweb.shared.queue.Publisher;

import java.util.function.Consumer;

public interface FlowProcessor
 extends Consumer<FlowEvent>, Publisher<FlowEvent>
{
}
