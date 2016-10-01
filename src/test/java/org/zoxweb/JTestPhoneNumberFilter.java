/*
 * Copyright (c) 2012-Oct 2, 2014 ZoxWeb.com LLC.
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

import org.zoxweb.shared.filters.PhoneNumberFilter;

/**
 * @author mzebib
 *
 */
public class JTestPhoneNumberFilter 
{
	
	@Before
	public void setUp() 
			throws Exception 
	{
		
	}


	@After
	public void tearDown() 
			throws Exception 
	{

	}

	@Test
	public void testA0() 
	{
		String number = "+1-415-555-5555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test
	public void testA1() 
	{
		String number = "415-555-5555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test
	public void testA2() 
	{
		String number = "+1 (415) 555-5555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test
	public void testA3() 
	{
		String number = "415 555 5555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test
	public void testA4() 
	{
		String number = "(415)555-5555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testA5() 
	{
		String number = "415 555 5555 xx 123";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test
	public void testA6() 
	{
		String number = "4155555555x123";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testA7() 
	{		
		String number = "4155555555ext123";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test
	public void testA8() 
	{
		String number = "4155555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testA9() 
	{
		String number = "415)5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB0()
	{
		String number = "+1((415)-5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB1() 
	{		
		String number = ")415(5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB2() 
	{
		String number = "415))5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB3() 
	{
		String number = "(415)5555555()";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB4() 
	{		
		String number = ")(5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB5() 
	{
		String number = "()5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB6() 
	{
		String number = "(+415)5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB7() 
	{
		String number = "+(415)++5555555";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB8() 
	{
		String number = "--(415)5555555x";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testB9() 
	{
		String number = "+1(415)5555555xsdfdsf";
		System.out.println("Phone Number: " + PhoneNumberFilter.SINGLETON.validate(number));
		System.out.println("Valid: " + PhoneNumberFilter.SINGLETON.isValid(number));
		System.out.println("");
	}

	
}