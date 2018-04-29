package org.zoxweb.shared.util;

import java.util.EventListener;

public interface TaskListener<T, R>
	extends EventListener
{
	public void started(T t);
	public void executionResult(int status, R result);
	public void terminated(T t);
	
}
