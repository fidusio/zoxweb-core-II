package org.zoxweb.shared.util;

public interface LifeCycleMonitor<T>
{
	
	public boolean created(T t);
	
	public boolean terminated(T t);
}
