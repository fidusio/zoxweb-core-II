package org.zoxweb.shared.util;

public interface IsClosed
	extends AutoCloseable {

	/**
	 * Checks if closed.
	 * @return
	 */
	public boolean isClosed();
}
