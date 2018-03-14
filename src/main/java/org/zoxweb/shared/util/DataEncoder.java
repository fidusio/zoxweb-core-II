package org.zoxweb.shared.util;

public interface DataEncoder<I, O>
extends Codec<I, O> 
{
	O encode(I input);
}
