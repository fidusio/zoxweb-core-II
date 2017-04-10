/*
 * Copyright (c) 2012-Nov 18, 2014 ZoxWeb.com LLC.
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.zoxweb.shared.filters.AddressFilterType;

public class AddressFilterTypeTest {
	
	@Before
	public void setUp() 
			throws Exception {
	}

	@After
	public void tearDown()
			throws Exception {
	}

	@Test
	public void test1() {
		String zipCode = "90066";
		
		System.out.println("US ZIP Code Filter: " + zipCode + " valid: " + AddressFilterType.US_ZIP_CODE.isValid(zipCode));
	}
	
	@Test
	public void test2() {
		String zipCode = "0";
		
		System.out.println("US ZIP Code Filter: " + zipCode + " valid: " + AddressFilterType.US_ZIP_CODE.isValid(zipCode));
	}

	@Test
	public void test3() {
		String zipCode = "48202";
		
		System.out.println("US ZIP Code Filter: " + zipCode + " valid: " + AddressFilterType.US_ZIP_CODE.isValid(zipCode));
	}

	@Test
	public void test4() {
		String zipCode = "48202555";
		
		System.out.println("US ZIP Code Filter: " + zipCode + " valid: " + AddressFilterType.US_ZIP_CODE.isValid(zipCode));
	}
	
	@Test
	public void test5() {
		String postalCode = "N9G2E1";
		
		System.out.println("Canada Postal Code Filter: " + postalCode + " valid: " + AddressFilterType.CANADA_POSTAL_CODE.isValid(postalCode));
	}
	
	@Test
	public void test6() {
		String postalCode = "9N23G1";
		
		System.out.println("Canada Postal Code Filter: " + postalCode + " valid: " + AddressFilterType.CANADA_POSTAL_CODE.isValid(postalCode));
	}

	@Test
	public void test7() {
		String postalCode = "90066";
		
		System.out.println("Canada Postal Code Filter: " + postalCode + " valid: " + AddressFilterType.CANADA_POSTAL_CODE.isValid(postalCode));
	}

	@Test
	public void test8() {
		String postalCode = "n9g2e1";
		
		System.out.println("Canada Postal Code Filter: " + postalCode + " valid: " + AddressFilterType.CANADA_POSTAL_CODE.isValid(postalCode));
	}
	
}