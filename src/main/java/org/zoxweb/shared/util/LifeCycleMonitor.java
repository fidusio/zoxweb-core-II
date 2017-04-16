package org.zoxweb.shared.util;

/**
 *
 * @param <T>
 */
public interface LifeCycleMonitor<T>
{
    /**
     *
     * @param t
     * @return
     */
	public boolean created(T t);

    /**
     *
     * @param t
     * @return
     */
	public boolean terminated(T t);

}