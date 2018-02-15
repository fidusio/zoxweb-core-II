package org.zoxweb.shared.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResourceManager
{
	
	public enum Resource
		implements GetName
	{
		API_SECURITY_MANAGER("APISecurityManager"),
		DATA_STORE("DataStore"),
		API_APP_MANAGER("APIAppManager"),
		
		;

		Resource(String name)
		{
			this.name= name;
		}
		private final String name;
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return name;
		}
		
	}
	
	
	public static final ResourceManager SINGLETON = new ResourceManager();
	
	private Map<String, Object> resources = new LinkedHashMap<String, Object>();
	
	private ResourceManager()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public <V> V lookup(String name)
	{
		return (V) resources.get(name);
	}
	
	
	public <V> V lookup(GetName gn)
	{
		return lookup(gn.getName());
	}
	
	public synchronized <V> void map(String name, V res)
	{
		resources.put(name, res);
	}
	
	public synchronized <V> void map(GetName gn, V res)
	{
		map(gn.getName(), res);
	}
	
	public synchronized Object [] resources()
	{
		return resources.values().toArray();
	}
}
