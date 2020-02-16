package org.zoxweb.server.task;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SupplierConsumerTask<T>
    implements TaskExecutor
{
    public SupplierConsumerTask(){}

    @Override
    public void executeTask(TaskEvent event) {
        Supplier<T> s = (Supplier<T>) event.getTaskExecutorParameters()[0];
        Consumer<T> c = (Consumer<T>) event.getTaskExecutorParameters()[1];
        c.accept(s.get());
    }

    @Override
    public void finishTask(TaskEvent event) {

    }
}
