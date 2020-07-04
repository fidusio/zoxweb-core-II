package org.zoxweb.server.util;

import java.util.function.Supplier;

public abstract class SuppliedRun<T>
implements Runnable, Supplier<T>
{

    private final T param;
    public SuppliedRun(T param)
    {
        this.param = param;
    }


    @Override
    public T get() {
        return param;
    }
}
