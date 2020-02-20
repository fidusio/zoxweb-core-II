package org.zoxweb.server.flow;

import org.zoxweb.shared.queue.Publisher;

import java.io.Closeable;
import java.util.function.Consumer;

public interface FlowProcessor<F>
 extends Consumer<FlowEvent<F>>, Publisher<FlowEvent<F>>, Closeable
{
}
