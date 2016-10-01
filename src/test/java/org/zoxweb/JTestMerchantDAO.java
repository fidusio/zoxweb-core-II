/*
 * Copyright (c) 2012-Jun 6, 2014 ZoxWeb.com LLC.
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

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.data.MerchantDAO;
import org.zoxweb.shared.data.PhoneDAO;
import org.zoxweb.shared.util.NVPair;

/**
 * @author mzebib
 *
 */
public class JTestMerchantDAO {

	private MerchantDAO merchant;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		merchant = new MerchantDAO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		merchant = null;
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#MerchantDAO()}.
	 */
	@Test
	public void testMerchantDAO() 
	{
		MerchantDAO merchant1 = new MerchantDAO();
		assertFalse("Merchant Objects Check: ", merchant.equals(merchant1));
	}


	/**
	 * 
	 */
	@Test
	public void testGetDomainName()
	{
		merchant.setName("Google");
		assertEquals("Name check: ", "Google", merchant.getName());
	}

	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void testSetDomainName() 
	{
		merchant.setName(null);
	}
	
	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#getDomainID()}.
	 */
	@Test
	public void testGetDomainID() 
	{
		merchant.setAccountID("www.google.com");
		assertEquals("Domain ID: ", "www.google.com", merchant.getAccountID());
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#setDomainID(java.lang.String)}.
	 */
	@Test
	public void testSetDomainID() 
	{
		merchant.setAccountID("www.yahoo.com");
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#getCompanyType()}.
	 */
	@Test(expected = Error.class)
	public void testGetCompanyType() 
	{
		merchant.setCompanyType("computer software");
		assertEquals("CompanyType: ", "internet", "computer software");
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#setCompanyType(java.lang.String)}.
	 */
	@Test
	public void testSetCompanyType() 
	{
		merchant.setCompanyType(null);
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#getListOfAddresses()}.
	 */
	@Test(expected = Error.class)
	public void testGetListOfAddresses() 
	{		
		ArrayList<AddressDAO> addresslist= new ArrayList<AddressDAO>();
		AddressDAO address = new AddressDAO();
		address.setStreet("1580 Purdue Ave. Suite A");
		address.setCity("Los Angeles");
		address.setStateOrProvince("CA");
		address.setCountry("USA");
		address.setZIPOrPostalCode("90025");
		addresslist.add(address);
		merchant.setListOfAddresses(addresslist);
		
		assertNull("List of Addresses Null: ", merchant.getListOfAddresses());
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#setListOfAddresses(java.util.ArrayList)}.
	 */
	@Test
	public void testSetListOfAddresses() 
	{
		ArrayList<AddressDAO> addresslist= new ArrayList<AddressDAO>();
		AddressDAO address = new AddressDAO();
		address.setStreet("1580 Purdue Ave. Suite A");
		address.setCity("Los Angeles");
		address.setStateOrProvince("CA");
		address.setCountry("USA");
		address.setZIPOrPostalCode("90025");
		addresslist.add(address);
		merchant.setListOfAddresses(addresslist);
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#getListOfPhones()}.
	 */
	@Test
	public void testGetListOfPhones() 
	{
		ArrayList<PhoneDAO> phonelist = new ArrayList<PhoneDAO>();
		PhoneDAO phone = new PhoneDAO();
		phone.setPhoneType("MOBILE");
		phone.setCountryCode("1");
		phone.setAreaCode("310");
		phone.setNumber("3027883");
		phonelist.add(phone);
		merchant.setListOfPhones(phonelist);
		
		assertNotNull("List of Phones NOT Null: ", merchant.getListOfPhones());
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#setListOfPhones(java.util.ArrayList)}.
	 */
	@Test
	public void testSetListOfPhones() 
	{
		merchant.setListOfPhones(null);
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#getListOfDomainEmails()}.
	 */
	@Test
	public void testGetListOfDomainEmails() 
	{
		ArrayList<NVPair> emailList = new ArrayList<NVPair>();
		NVPair email1 = new NVPair();
		email1.setName("Work");
		email1.setValue("mzebib@zoxweb.com");
		emailList.add(email1);
		NVPair email2 = new NVPair();
		email2.setName("Personal");
		email2.setValue("mustaphaazebib@gmail.com");
		emailList.add(email2);
		merchant.setListOfDomainEmails(emailList);
		
		assertFalse("Same Domain Emails Check: ", merchant.getListOfDomainEmails().get(0).equals(merchant.getListOfDomainEmails().get(1)));
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#setListOfDomainEmails(java.util.ArrayList)}.
	 */
	@Test
	public void testSetListOfDomainEmails() 
	{
		merchant.setListOfDomainEmails(null);
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#getListOfPaymentInfo()}.
	 */
	@Test(expected = Error.class)
	public void testGetListOfPaymentInfos() 
	{
		assertEquals("List of Payment Info Check: ", 0, merchant.getListOfPaymentInfos());
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#setListOfPaymentInfo(java.util.ArrayList)}.
	 */
	@Test
	public void testSetListOfPaymentInfos() 
	{
		merchant.setListOfPaymentInfos(null);
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#getAdditonalInfo()}.
	 */
	@Test(expected = Error.class)
	public void testGetAdditonalInfo() 
	{
		assertEquals("Additional Info Check: ", null, merchant.getAdditonalInfos());
	}

	/**
	 * Test method for {@link org.zoxweb.shared.data.MerchantDAO#setAdditionalInfo(java.util.ArrayList)}.
	 */
	@Test
	public void testSetAdditionalInfo() 
	{
		merchant.setAdditionalInfos(null);
	}

}
