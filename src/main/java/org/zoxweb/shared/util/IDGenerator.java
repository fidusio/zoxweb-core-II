package org.zoxweb.shared.util;

public interface IDGenerator<I>
	extends GetName
{
	
	/**
	 * Generate a unique random ID.
	 * @return a new ID.
	 */
	I generateID();
	
}
