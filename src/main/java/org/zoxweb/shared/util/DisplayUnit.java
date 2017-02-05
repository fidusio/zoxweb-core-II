package org.zoxweb.shared.util;

import org.zoxweb.shared.util.GetName;

public enum DisplayUnit
	implements GetName {

	EM("EM"),
	PERCENT("%"),
	PIXEL("px")
	
	;

	String name;
	
	DisplayUnit(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

}