package org.zoxweb.shared.util;

public interface WaitTime<T> {
    long nextWait();

    T get();
}
