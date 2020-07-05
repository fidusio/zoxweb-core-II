package org.zoxweb.server.util;

import java.util.function.Supplier;

public abstract class RunSupplier<T>
implements Runnable, Supplier<T>
{

    private final T param;
    public RunSupplier(T param)
    {
        this.param = param;
    }


    @Override
    public T get() {
        return param;
    }
}
