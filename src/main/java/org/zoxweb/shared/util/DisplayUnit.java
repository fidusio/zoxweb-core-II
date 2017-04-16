package org.zoxweb.shared.util;

public enum DisplayUnit
    implements GetName
{

	EM("EM"),
	PERCENT("%"),
	PIXEL("px")
	
	;

	private String name;
	
	DisplayUnit(String name)
    {
		this.name = name;
	}
	
	@Override
	public String getName()
    {
		return name;
	}

}