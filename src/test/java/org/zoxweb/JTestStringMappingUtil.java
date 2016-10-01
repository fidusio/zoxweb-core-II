/*
 * Copyright (c) 2012-Sep 9, 2014 ZoxWeb.com LLC.
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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zoxweb.shared.data.CreditCardDAO;
import org.zoxweb.shared.data.CreditCardType;
import org.zoxweb.shared.util.NVConfigMapUtil;

/**
 * @author mzebib
 *
 */
public class JTestStringMappingUtil 
{

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM-yyyy");
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
	}

	@Test
	public void testA1() throws ParseException 
	{
		CreditCardDAO card = new CreditCardDAO();
		card.setCardType(CreditCardType.MASTER);
		card.setCardHolderName("Mustapha Zebib");
		card.setCardNumber("5-1-1234-56789-11234");
		card.setSecurityCode("999");
		card.setExpirationDate(DEFAULT_DATE_FORMAT.parse("06-2015").getTime());
	
		System.out.println(NVConfigMapUtil.toString(card, null));
	}

	@Test
	public void testA2() throws ParseException 
	{
		CreditCardDAO card1 = new CreditCardDAO();
	
		System.out.println(NVConfigMapUtil.toString(card1, null));	
	}
	
	@Test
	public void testA3() throws ParseException 
	{	
		System.out.println(NVConfigMapUtil.toString(null, null));	
	}
	
	
	@Test
	public void testA4() throws ParseException 
	{
		CreditCardDAO card = new CreditCardDAO();
		card.setCardType(CreditCardType.MASTER);
		card.setCardHolderName("Mustapha Zebib");
		card.setCardNumber("5-1-1234-56789-11234");
		card.setSecurityCode("999");
		card.setExpirationDate(DEFAULT_DATE_FORMAT.parse("06-2015").getTime());
		
	
		System.out.println(NVConfigMapUtil.toString(card, null));	
	}
	
}
