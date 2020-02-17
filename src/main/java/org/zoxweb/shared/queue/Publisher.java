package org.zoxweb.shared.queue;

public interface Publisher<T>
{
    void publish(T event);
}
