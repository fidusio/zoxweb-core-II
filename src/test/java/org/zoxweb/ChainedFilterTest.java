/*
 * Copyright (c) 2012-Mar 10, 2015 ZoxWeb.com LLC.
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

import org.junit.Test;

import org.zoxweb.shared.filters.ChainedFilter;
import org.zoxweb.shared.filters.CreditCardNumberFilter;
import org.zoxweb.shared.filters.FilterType;

public class ChainedFilterTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {
		ChainedFilter cf = new ChainedFilter(FilterType.CLEAR, FilterType.EMAIL);
		
		String input = "johnsmith@zoxweb.com";
		
		System.out.println("***Test 1***");
		System.out.println("Validate: " + cf.validate(input)  + "   Valid: " + cf.isValid(input));
	}
	
	@Test
	public void test2() 
	{
		FilterType[] filters = new FilterType[6];
		filters[0] = FilterType.BIG_DECIMAL;
		filters[1] = FilterType.BINARY;
		filters[2] = FilterType.DOUBLE;
		filters[3] = FilterType.FLOAT;
		filters[4] = FilterType.INTEGER;
		filters[5] = FilterType.LONG;
		
		ChainedFilter cf = new ChainedFilter(filters);

		System.out.println("***Test 2***");
		System.out.println("Filter Type: " + FilterType.BOOLEAN + " Inlcuded: " + cf.isFilterSupported(FilterType.BOOLEAN));
		System.out.println("Filter Type: " + FilterType.CLEAR + " Inlcuded: " + cf.isFilterSupported(FilterType.CLEAR));
		System.out.println("Filter Type: " + FilterType.EMAIL + " Inlcuded: " + cf.isFilterSupported(FilterType.EMAIL));
		System.out.println("Filter Type: " + FilterType.ENCRYPT_MASK + " Inlcuded: " + cf.isFilterSupported(FilterType.ENCRYPT_MASK));
		
		for (FilterType ft : filters)
		{
			System.out.println("Filter Type: " + ft + " Inlcuded: " + cf.isFilterSupported(ft));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3() 
	{
		ChainedFilter cf = new ChainedFilter
							(
								FilterType.BIG_DECIMAL, 
								FilterType.BINARY, 
								FilterType.DOUBLE, 
								FilterType.FLOAT, 
								FilterType.INTEGER,
								FilterType.LONG
							);
		
		System.out.println("***Test 3***");
		System.out.println("Canonical ID: " + cf.toCanonicalID());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test4() 
	{
		ChainedFilter cf = new ChainedFilter(FilterType.CLEAR, FilterType.EMAIL);
		
		String[] inputs = {"mzebib@zoxweb.com", "54545", "mzebib@gmail.com", "mzebib@gmail"};
		
		System.out.println("***Test 4***");
		System.out.println("Filters: " + cf.toCanonicalID());
		
		for (String input : inputs)
		{
			try
			{
				System.out.println("Valid:   " + cf.validate(input));
			}
			catch (Exception e)
			{
				System.out.println("Invalid: " + input);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test5() 
	{
		ChainedFilter cf = new ChainedFilter(CreditCardNumberFilter.SINGLETON, FilterType.ENCRYPT_MASK);
		
		String[] inputs = {"5412 3456 7891 1234", "123", "4412 3456 7891 1234", "Hello", "34 1234 5678 9112 3"};
		
		System.out.println("***Test 5***");
		System.out.println("Filters: " + cf.toCanonicalID());
		
		for (String input : inputs)
		{
			try
			{
				System.out.println("Valid:   " + cf.validate(input));
			}
			catch (Exception e)
			{
				System.out.println("Invalid: " + input);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void test6() 
	{
		ChainedFilter cf = new ChainedFilter(FilterType.INTEGER, FilterType.PASSWORD);
		
		String input = "johmsmith@zoxweb.com";
		
		System.out.println("***Test 6***");
		System.out.println("Filters: " + cf.toCanonicalID());
		System.out.println("Validate:   " + cf.validate(input));
	}
	
	
}
