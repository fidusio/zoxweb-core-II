package org.zoxweb.shared.data.shiro;

import java.util.Map;

import org.zoxweb.shared.data.NVEntityAccessInfo;
import org.zoxweb.shared.util.CRUD;
import org.zoxweb.shared.util.NVEntity;

public class ShareScope 
{
	private NVEntity containerNVE;
	private Map<String, NVEntityAccessInfo> map;
	

	public ShareScope(NVEntity containerNVE, Map<String, NVEntityAccessInfo> map)
	{
		this.containerNVE = containerNVE;
		this.map = map;
	}	
	
	public NVEntity getContainerNVEntity()
	{
		return containerNVE;
	}
	
	public Map<String, NVEntityAccessInfo> getAccessInfoMap()
	{
		return map;
	}

	public boolean isPermitted(String refID, CRUD crud)
	{
		NVEntityAccessInfo toCheck = map.get(refID);
		
		if (toCheck != null)
		{
			return toCheck.isPermitted(crud);
		}
		
		return false;
	}

	public boolean isPermitted(NVEntity nve, CRUD crud)
	{
		return isPermitted(nve.getReferenceID(), crud);
	}
	
}