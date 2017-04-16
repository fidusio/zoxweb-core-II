package org.zoxweb.shared.data.events;

import java.util.EventObject;

/**
 *
 */
@SuppressWarnings("serial")
public class StringTokenEvent 
    extends EventObject
{
	private final String token;
	private final long timeStamp;

	public StringTokenEvent(Object source, String token)
	{
		super(source);
		this.token = token;
		timeStamp = System.currentTimeMillis();
	}

    /**
     * Returns the token.
     * @return
     */
	public String getToken()
	{
		return token;
	}

	/**
	 * @return the timestamp in millis when the event was created
	 */
	public final long getTimeStamp()
	{
		return timeStamp;
	}
	
}
