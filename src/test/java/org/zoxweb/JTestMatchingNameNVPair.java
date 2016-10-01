/*
 * Copyright (c) 2012-Oct 12, 2014 ZoxWeb.com LLC.
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedUtil;

/**
 * @author mzebib
 *
 */
public class JTestMatchingNameNVPair {


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
		List<NVPair> list = new ArrayList<NVPair>();
		
		list.add(new NVPair("USA", "United States of America"));
		list.add(new NVPair("CAN", "Canada"));
		list.add(new NVPair("GBR", "United Kingdom"));
		list.add(new NVPair("FRA", "France"));
		
		System.out.println("Does name exist in list of NVPair? " + SharedUtil.doesNameExistNVList(list, "USA"));
	}
	
	@Test
	public void testA2()
	{
		List<NVPair> list = new ArrayList<NVPair>();
		
		list.add(new NVPair("USA", "United States of America"));
		list.add(new NVPair("CAN", "Canada"));
		list.add(new NVPair("GBR", "United Kingdom"));
		list.add(new NVPair("FRA", "France"));
		
		System.out.println("Does name exist in list of NVPair? " + SharedUtil.doesNameExistNVList(list, "LBN"));
	}
	
	@Test
	public void testA3()
	{
		List<NVPair> list = new ArrayList<NVPair>();
		
		list.add(new NVPair("USA", "United States of America"));
		list.add(new NVPair("CAN", "Canada"));
		list.add(new NVPair("GBR", "United Kingdom"));
		list.add(new NVPair("FRA", "France"));
		
		System.out.println("Does name exist in list of NVPair? " + SharedUtil.doesNameExistNVList(list, "FRA"));
	}
	
	@Test
	public void testA4()
	{
		List<NVPair> list = new ArrayList<NVPair>();
		
		list.add(new NVPair("USA", "United States of America"));
		list.add(new NVPair("CAN", "Canada"));
		list.add(new NVPair("GBR", "United Kingdom"));
		list.add(new NVPair("FRA", "France"));
		
		System.out.println("Does name exist in list of NVPair? " + SharedUtil.doesNameExistNVList(list, "JPN"));
	}
	
}