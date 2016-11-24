package org.zoxweb.shared.util;

/**
 * Define a value getter interface based on input and output type
 * @param <I>
 * @param <O>
 */
public interface ValueGetter<I,O>
{
	O getValue(I input);
}
