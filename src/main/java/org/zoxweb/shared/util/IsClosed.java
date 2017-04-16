package org.zoxweb.shared.util;

/**
 *
 */
public interface IsClosed
	extends AutoCloseable
{

	/**
	 * Checks if closed.
	 * @return true if closed
	 */
	public boolean isClosed();
}
