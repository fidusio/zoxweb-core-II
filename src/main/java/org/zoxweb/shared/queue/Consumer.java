package org.zoxweb.shared.queue;

public interface Consumer<T> {
    void consume(T event);
}
