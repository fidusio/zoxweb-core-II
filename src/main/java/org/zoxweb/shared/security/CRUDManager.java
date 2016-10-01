/*
 * Copyright (c) 2012-Oct 16, 2014 ZoxWeb.com LLC.
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

import org.zoxweb.shared.util.CRUD;

/**
 * The CRUD manager interface.
 * @author mzebib
 *
 */
public interface CRUDManager
	extends CRUDOperations
{
	/**
	 * Adds CRUD to given resource ID.
	 * @param resourceID
	 * @param crud
	 */
	public void addCRUD(String resourceID, CRUD crud);
	
	/**
	 * Removes CRUD from given resource ID.
	 * @param resourceID
	 * @param crud
	 */
	public void removeCRUD(String resourceID, CRUD crud);
	
	
	
}
