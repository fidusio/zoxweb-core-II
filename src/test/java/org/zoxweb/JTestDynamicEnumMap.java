/*
 * Copyright (c) 2012-Aug 19, 2014 ZoxWeb.com LLC.
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


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zoxweb.shared.accounting.Currency;
import org.zoxweb.shared.util.DynamicEnumMap;
import org.zoxweb.shared.util.DynamicEnumMapManager;
import org.zoxweb.shared.util.NVPair;

/**
 * @author mzebib
 *
 */
public class JTestDynamicEnumMap 
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
	public void testA1() 
	{
		DynamicEnumMap gender = new DynamicEnumMap("Gender");
		
		gender.addEnumValue(new NVPair("M", "Male"));
		gender.addEnumValue(new NVPair("F", "Female"));
		
		System.out.println(gender.getName());
		
		for (NVPair nvp : gender.getValue())
		{
			System.out.println(nvp.getName() + " " + nvp.getValue());
		}
		
	}
	
	@Test
	public void testA2() 
	{
		DynamicEnumMap states = new DynamicEnumMap("US States");
		
		states.addEnumValue(new NVPair("AL", "Alabama"));
		states.addEnumValue(new NVPair("AK", "Alaska"));
		states.addEnumValue(new NVPair("AZ", "Arizona"));
		states.addEnumValue(new NVPair("AR", "Arkansas"));
		states.addEnumValue(new NVPair("CA", "California"));
		states.addEnumValue(new NVPair("CO", "Colorado"));
		states.addEnumValue(new NVPair("CT", "Connecticut"));
		states.addEnumValue(new NVPair("DE", "Delaware"));
		states.addEnumValue(new NVPair("FL", "Florida"));
		states.addEnumValue(new NVPair("GA", "Georgia"));
		states.addEnumValue(new NVPair("HI", "Hawaii"));
		states.addEnumValue(new NVPair("ID", "Idaho"));
		states.addEnumValue(new NVPair("IL", "Illinois"));		
		states.addEnumValue(new NVPair("IN", "Indiana"));
		states.addEnumValue(new NVPair("IA", "Iowa"));
		states.addEnumValue(new NVPair("KS", "Kansas"));
		states.addEnumValue(new NVPair("KY", "Kentucky"));
		states.addEnumValue(new NVPair("LA", "Louisiana"));
		states.addEnumValue(new NVPair("ME", "Maine"));
		states.addEnumValue(new NVPair("MD", "Maryland"));
		states.addEnumValue(new NVPair("MA", "Massachusetts"));
		states.addEnumValue(new NVPair("MI", "Michigan"));
		states.addEnumValue(new NVPair("MN", "Minnesota"));
		states.addEnumValue(new NVPair("MS", "Mississippi"));
		states.addEnumValue(new NVPair("MO", "Missouri"));
		states.addEnumValue(new NVPair("MT", "Montana"));
		states.addEnumValue(new NVPair("NE", "Nebraska"));
		states.addEnumValue(new NVPair("NV", "Nevada"));
		states.addEnumValue(new NVPair("NH", "New Hampshire"));
		states.addEnumValue(new NVPair("NJ", "New Jersey"));
		states.addEnumValue(new NVPair("NM", "New Mexico"));
		states.addEnumValue(new NVPair("NY", "New York"));
		states.addEnumValue(new NVPair("NC", "North Carolina"));
		states.addEnumValue(new NVPair("ND", "North Dakota"));
		states.addEnumValue(new NVPair("OH", "Ohio"));
		states.addEnumValue(new NVPair("OK", "Oklahoma"));
		states.addEnumValue(new NVPair("OR", "Oregon"));
		states.addEnumValue(new NVPair("PA", "Pennsylvania"));
		states.addEnumValue(new NVPair("RI", "Rhode Island"));
		states.addEnumValue(new NVPair("SC", "South Carolina"));
		states.addEnumValue(new NVPair("SD", "Sout Dakota"));
		states.addEnumValue(new NVPair("TN", "Tennessee"));
		states.addEnumValue(new NVPair("TX", "Texas"));
		states.addEnumValue(new NVPair("UT", "Utah"));
		states.addEnumValue(new NVPair("VT", "Vermont"));
		states.addEnumValue(new NVPair("VA", "Virginia"));
		states.addEnumValue(new NVPair("WA", "Washington"));
		states.addEnumValue(new NVPair("WV", "West Virginia"));
		states.addEnumValue(new NVPair("WI", "Wisconsin"));
		states.addEnumValue(new NVPair("WY", "Wyoming"));
		
		states.addEnumValue(new NVPair("DC", "District of Columbia"));
		states.addEnumValue(new NVPair("GU", "Guam"));
		states.addEnumValue(new NVPair("PR", "Puerto Rico"));
		states.addEnumValue(new NVPair("VI", "Virgin Islands"));
		
		System.out.println("\n" + states.getName());
		
		for (NVPair nvp : states.getValue())
		{
			System.out.println(nvp.getName() + " " + nvp.getValue());
		}
		
	}
	
	@Test
	public void testA3() 
	{
		DynamicEnumMap country = new DynamicEnumMap("Countries");
		
		country.addEnumValue(new NVPair("US", "United States of America"));
		country.addEnumValue(new NVPair("CA", "Canada"));
		country.addEnumValue(new NVPair("UK", "United Kingdom"));
		country.addEnumValue(new NVPair("FR", "France"));
		country.addEnumValue(new NVPair("DE", "Germany"));
		country.addEnumValue(new NVPair("AU", "Australia"));
		country.addEnumValue(new NVPair("JP", "Japan"));
		country.addEnumValue(new NVPair("HK", "Hong Kong"));
		country.addEnumValue(new NVPair("SG", "Singapore"));
		
		System.out.println("\n" + country.getName());
		
		for (NVPair nvp : country.getValue())
		{
			System.out.println(nvp.getName() + " " + nvp.getValue());
		}
		
	}
	
	@Test
	public void testA4() 
	{
		DynamicEnumMap currency = new DynamicEnumMap(Currency.class);
	
		System.out.println("\n" + currency.toCanonicalID());
		
		for (NVPair nvp : currency.getValue())
		{
			System.out.println(nvp.getName() + " " + nvp.getValue());
		}
		
	}
	
	@Test (expected = NullPointerException.class)
	public void testA5() 
	{
		DynamicEnumMap test = new DynamicEnumMap("Test");
		test.addEnumValue(new NVPair(null));	
	}

	@Test
	public void testA6() 
	{
		DynamicEnumMap test = new DynamicEnumMap(DynamicEnumMap.NAME_PREFIX + ":" + "Test");
		
		System.out.println(test.getName());
		
		test = new DynamicEnumMap(DynamicEnumMap.NAME_PREFIX + "Test");
		
		System.out.println(test.getName());
		
	}
	
	@Test
	public void testA7() 
	{
		DynamicEnumMap names = new DynamicEnumMap("Names");
		
		ArrayList<NVPair> list = new ArrayList<NVPair>();

		list.add(new NVPair("Mustapha", "M"));
		list.add(new NVPair("Marwan", "M"));
		
		names.setValue(list);
		
		DynamicEnumMapManager.validateDynamicEnumMap(names);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testA8() 
	{
		DynamicEnumMap names = new DynamicEnumMap("States");
		
		ArrayList<NVPair> list = new ArrayList<NVPair>();

		list.add(new NVPair("CA", "California"));
		list.add(new NVPair("CA", "California"));
		
		names.setValue(list);
		
		DynamicEnumMapManager.validateDynamicEnumMap(names);	
	}
	
	
}
