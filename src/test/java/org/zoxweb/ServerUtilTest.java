/*
 * Copyright (c) 2012-Oct 29, 2015 ZoxWeb.com LLC.
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
package org.zoxweb;

import java.util.ArrayList;
import java.util.List;

import org.zoxweb.server.util.ServerUtil;
import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.data.PhoneDAO;
import org.zoxweb.shared.util.NVEntity;

public class ServerUtilTest {

	public static void main(String[] args) {
		List<NVEntity> list1 = new ArrayList<NVEntity>();
		list1.add(new PhoneDAO());
		list1.add(new AddressDAO());
		
		System.out.println("List<NVEntity> of type NVEntity: " + ServerUtil.areAllInstancesMatchingType(list1, NVEntity.class));
		System.out.println("List<NVEntity> of type AddressDAO: " + ServerUtil.areAllInstancesMatchingType(list1, AddressDAO.class));
		
		List<Object> list2 = new ArrayList<Object>();
		list2.add(new PhoneDAO());
		list2.add(new AddressDAO());
		list2.add("Hello");
		
		System.out.println("\nList<Object> of type NVEntity: " + ServerUtil.areAllInstancesMatchingType(list2, NVEntity.class));
		System.out.println("List<Object> of type Object: " + ServerUtil.areAllInstancesMatchingType(list2, Object.class));
		
		List<Object> list3 = new ArrayList<Object>();
		list3.add("1");
		list3.add("2");
		list3.add("3");
		
		System.out.println("\nList<Object> of type NVEntity: " + ServerUtil.areAllInstancesMatchingType(list3, NVEntity.class));
		System.out.println("List<Object> of type Object: " + ServerUtil.areAllInstancesMatchingType(list3, Object.class));
		System.out.println("List<Object> of type String: " + ServerUtil.areAllInstancesMatchingType(list3, String.class));
		
		List<Object> list4 = new ArrayList<Object>();
		list4.add("1");
		list4.add("2");
		list4.add("3");
		list4.add(new PhoneDAO());
		
		System.out.println("\nList<Object> of type NVEntity: " + ServerUtil.areAllInstancesMatchingType(list4, NVEntity.class));
		System.out.println("List<Object> of type Object: " + ServerUtil.areAllInstancesMatchingType(list4, Object.class));
		System.out.println("List<Object> of type String: " + ServerUtil.areAllInstancesMatchingType(list4, String.class));
		System.out.println("List<Object> of type PhoneDAO: " + ServerUtil.areAllInstancesMatchingType(list4, PhoneDAO.class));
	}

}