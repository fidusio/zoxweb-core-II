package org.zoxweb.shared.protocol;

import org.zoxweb.shared.util.GetName;

public enum MessageMarker
implements GetName
{
	START,
	END,
	HEADER_START,
	HEADER_END,
	CONTENT_START,
	CONTENT_END;

	
	private final String name;
	MessageMarker()
	{
		this.name= name().toLowerCase();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public String toString()
	{
		return getName();
				
	}
}
