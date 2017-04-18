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

import org.junit.Test;

import org.zoxweb.shared.util.CRUD;

public class CRUDManagerDefaultTest {

	@Test
	public void test1HasPermission() {
		CRUDManagerDefault crudManager = new CRUDManagerDefault();
		
		String resourceID = "10101";
		CRUD crud = CRUD.CREATE;
		crudManager.addCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.CREATE));
	}
	
	@Test
	public void test2HasPermission() {
		CRUDManagerDefault crudManager = new CRUDManagerDefault();
		
		String resourceID = "10102";
		CRUD crud = CRUD.READ;
		crudManager.addCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.CREATE));
	}
	
	@Test
	public void test3HasPermission() {
		CRUDManagerDefault crudManager = new CRUDManagerDefault();
		
		String resourceID = "10103";
		CRUD crud = CRUD.UPDATE;
		crudManager.addCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.UPDATE));
	}
	
	@Test
	public void test4HasPermission() {
		CRUDManagerDefault crudManager = new CRUDManagerDefault();
		
		String resourceID = "10104";
		CRUD crud = CRUD.DELETE;
		crudManager.addCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.UPDATE));
	}
	
	@Test
	public void test5HasPermission() {
		CRUDManagerDefault crudManager = new CRUDManagerDefault();
		
		String resourceID = "10105";
		CRUD crud = CRUD.CREATE;
		crudManager.addCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.CREATE));
		
		crudManager.removeCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.CREATE));
	}
	
	@Test
	public void test6HasPermission() {
		CRUDManagerDefault crudManager = new CRUDManagerDefault();
		
		String resourceID = "10106";
		CRUD crud = CRUD.CREATE;
		crudManager.addCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.CREATE));
		
		resourceID = "10106";
		crud = CRUD.UPDATE;
		crudManager.addCRUD(resourceID, crud);
		
		System.out.println("Resource ID: " + resourceID + " Has " + crud + " Permission: " + crudManager.hasPermission(resourceID, CRUD.CREATE));
	}
	

}