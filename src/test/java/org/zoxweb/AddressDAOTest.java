/*
 * Copyright (c) 2012-Sep 11, 2014 ZoxWeb.com LLC.
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

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.AddressDAO;

public class AddressDAOTest {

	@Before
	public void setUp()
            throws Exception {
	}

	@After
	public void tearDown() 
			throws Exception {
	}

	@Test
	public void testA1()
            throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		AddressDAO address = new AddressDAO();
		
		address.setStreet("123 Main St.");
		address.setCity("Los Angeles");
		address.setStateOrProvince("CA");
		address.setCountry("USA");
		address.setZIPOrPostalCode("9000");
		
		
		System.out.println(address);
		String json = GSONUtil.toJSON(address, true);
		System.out.println( json);
		AddressDAO newAddress = GSONUtil.fromJSON(json, AddressDAO.class);
		System.out.println("new address from json:" + newAddress);
	}
		
	@Test
	public void testA2() {
		AddressDAO address = new AddressDAO();
		
		address.setCountry("USA");

		System.out.println(address);
	}

	@Test
	public void testA3() {
		AddressDAO address = new AddressDAO();
		
		address.setCountry("Canada");
		
		System.out.println(address);
	}
	
	@Test
	public void testA4() {
		AddressDAO address = new AddressDAO();
		
		address.setCountry("UK");
		
		System.out.println(address);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testA5() {
		AddressDAO address = new AddressDAO();
		
		address.setCountry("France");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testA6() {
		AddressDAO address = new AddressDAO();
		
		address.setCountry("China");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testA7() {
		AddressDAO address = new AddressDAO();
		
		address.setCountry("India");
	}
	
}