package org.zoxweb.shared.util;

import java.util.LinkedHashMap;

public class GenericProperties
{
	private LinkedHashMap<String, Object> properties = new LinkedHashMap<String, Object>(); 
	
	public <V> void setProperty(String name, V value) 
	{
		// TODO Auto-generated method stub
		properties.put(name, value);	
	}
	
	
	@SuppressWarnings("unchecked")
	public <V> V getProperty(String name) {
		// TODO Auto-generated method stub
		return (V)properties.get(name);
	}
	
	public void clear()
	{
		properties.clear();
	}
	

	public String[] getNames()
	{
		return properties.keySet().toArray(new String[0]);
	}
}