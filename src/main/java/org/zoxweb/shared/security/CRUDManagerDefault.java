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
package org.zoxweb.shared.security;

import java.util.HashMap;
import java.util.HashSet;

import org.zoxweb.shared.util.CRUD;

/**
 * This default class implements the CRUD manager interface.
 * @author mzebib
 *
 */
public class CRUDManagerDefault 
	implements CRUDManager
{
	
	private HashMap<String, HashSet<CRUD>> map = new HashMap<String, HashSet<CRUD>>();

	/**
	 * Checks if given resource ID has permission for CRUD operation.
	 * @param resourceID
	 * @param crud
	 * @return true if permission granted
	 */
	@Override
	public boolean hasPermission(String resourceID, CRUD crud) 
	{
		if (resourceID != null && crud != null)
		{
			HashSet<CRUD> set = map.get(resourceID);
			
			if (set != null)
			{
				return set.contains(crud);
			}
			
		}
		
		return false;
	}


	/**
	 * Adds CRUD to given resource ID.
	 * @param resourceID
	 * @param crud
	 */
	@Override
	public synchronized void addCRUD(String resourceID, CRUD crud) 
	{
		if (resourceID != null && crud != null)
		{
			HashSet<CRUD> set = map.get(resourceID);
		
			if (set == null)
			{
				set = new HashSet<CRUD>();
				map.put(resourceID, set);
			}
			
			set.add(crud);
		}
	}

	/**
	 * Removes CRUD from given resource ID.
	 * @param resourceID
	 * @param crud
	 */
	@Override
	public synchronized void removeCRUD(String resourceID, CRUD crud) 
	{
		if (resourceID != null && crud != null)
		{
			HashSet<CRUD> set = map.get(resourceID);
		
			if (set != null)
			{
				set.remove(crud);	
			}
			
		}
	}

}
