package org.zoxweb.server.io;

/**
 * The internal buffer access interface.
 */
public interface InternalBufferAccess 
{
	/**
	 * Returns the internal buffer.
	 * @return
	 */
	public byte[] getInternalBuffer();

	/**
	 * Returns the internal buffer size.
	 * @return
	 */
	public int size();
}
