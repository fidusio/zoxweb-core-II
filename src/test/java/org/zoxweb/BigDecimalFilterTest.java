/*
 * Copyright (c) 2012-Aug 29, 2014 ZoxWeb.com LLC.
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

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.zoxweb.shared.filters.BigDecimalFilter;

public class BigDecimalFilterTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testA1() {
		BigDecimal value = BigDecimalFilter.SINGLETON.validate("100.00");
		
		System.out.println("Result: " + value);
	}
	
	@Test
	public void testA2() {
		BigDecimal value = BigDecimalFilter.SINGLETON.validate("1000");
		
		System.out.println("Result: " + value);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testA3() 
	{
		BigDecimalFilter.SINGLETON.validate("John");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testA4() 
	{
		BigDecimalFilter.SINGLETON.validate("");
	}
	
	@Test (expected = NullPointerException.class)
	public void testA5() 
	{
		BigDecimalFilter.SINGLETON.validate(null);
	}
	
	@Test
	public void testA6() {
		BigDecimal value = BigDecimalFilter.SINGLETON.validate(".99");
		
		System.out.println("Result: " + value);
	}

}