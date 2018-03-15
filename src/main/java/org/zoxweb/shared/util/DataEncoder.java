package org.zoxweb.shared.util;

public interface DataEncoder<EI, EO>
	extends Codec 
{
	EO encode(EI input);
}
