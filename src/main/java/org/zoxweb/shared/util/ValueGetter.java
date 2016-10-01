package org.zoxweb.shared.util;

public interface ValueGetter<T,O> 
{
	O getValue(T input);
}
