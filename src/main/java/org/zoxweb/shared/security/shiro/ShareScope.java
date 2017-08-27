/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.shared.security.shiro;

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