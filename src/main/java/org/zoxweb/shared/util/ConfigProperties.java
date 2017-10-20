package org.zoxweb.shared.util;

public interface ConfigProperties 
{
	
	
	ArrayValues<GetNameValue<String>> getProperties();
	
	void setProperties(ArrayValues<GetNameValue<String>> aValues);
	
}
