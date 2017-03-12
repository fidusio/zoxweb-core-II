/*
 * Copyright (c) 2012-2016 ZoxWeb.com LLC.
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
package org.zoxweb.shared.data;

import java.util.ArrayList;
import java.util.List;

import org.zoxweb.shared.accounting.PaymentInfoDAO;
import org.zoxweb.shared.data.DataConst.DataParam;
import org.zoxweb.shared.util.DomainID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

/**
 * 
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class MerchantDAO 
	extends SetNameDescriptionDAO 
{
	
	public enum Merchant
		implements GetNVConfig
	{
		LIST_OF_DOMAIN_IDS(NVConfigManager.createNVConfigEntity("domain_ids", "Domain ID", "DomainIDs", true, true, DomainInfoDAO.NVC_DOMAIN_INFO_DAO, ArrayType.LIST)),
		COMPANY_TYPE(NVConfigManager.createNVConfig("company_type", "Type of company", "CompanyType", false, true, String.class)),
		LIST_OF_ADDRESSES(NVConfigManager.createNVConfigEntity("addresses", "List of addresses", "Addresses", false, true, AddressDAO.NVC_ADDRESS_DAO, ArrayType.LIST)),
		LIST_OF_PHONES(NVConfigManager.createNVConfigEntity("phones", "List of phones", "ListOfPhones", false, true, PhoneDAO.NVC_PHONE_DAO, ArrayType.LIST)),
		LIST_OF_DOMAIN_EMAILS(NVConfigManager.createNVConfig("domain_emails", "List of domain emails", "DomainEmails", false, true, String[].class)),
		LIST_OF_PAYMENT_INFOS(NVConfigManager.createNVConfigEntity("payment_infos", "List of payment info", "PaymentInfos", false, true, PaymentInfoDAO.NVC_PAYMENT_INFO_DAO, ArrayType.LIST)),
		ADDITIONAL_INFOS(NVConfigManager.createNVConfig("additional_info", "Additional information", "AdditionalInfos", false, true, String[].class)),
		
		;
		
		private final NVConfig cType;
		
		Merchant(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}

	}
	
	public static final NVConfigEntity NVC_MERCHANT_DAO = new NVConfigEntityLocal(
																					"merchant_dao", 
																					null, 
																					"MerchantDAO", 
																					true, 
																					false, 
																					false, 
																					false, 
																					MerchantDAO.class, 
																					SharedUtil.extractNVConfigs(Merchant.values()), 
																					null, 
																					true, 
																					SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);
	
	/**
	 * The default constructor.
	 */
	public MerchantDAO()
	{
		super(NVC_MERCHANT_DAO);
	}

//	protected MerchantDAO(List<NVConfigEntity> list)
//	{
//		super(SharedUtil.merge(list, NVC_MERCHANT_DAO));
//	}
	
	/**
	 * Gets the domain ID.
	 * @return list of domain ids
	 */
	public List<DomainID<String>> getDomainIDs() 
	{
		return lookupValue(Merchant.LIST_OF_DOMAIN_IDS);
	}
	
	/**
	 * Sets the domain ID.
	 * @param domainIDs
	 */
	public void setDomainID(List<DomainID<String>> domainIDs)
	{
		setValue(Merchant.LIST_OF_DOMAIN_IDS, domainIDs);
	}
	
	/**
	 * Gets the company type.
	 * @return company type
	 */
	public String getCompanyType() 
	{
		return lookupValue(Merchant.COMPANY_TYPE);
	}
	
	/**
	 * Sets the company type.
	 * @param type
	 */
	public void setCompanyType(String type)
	{
		setValue(Merchant.COMPANY_TYPE, type);
	}
	
	/**
	 * Gets list of addresses.
	 * @return list of addresses
	 */
	public ArrayList<AddressDAO> getListOfAddresses() 
	{
		return lookupValue(Merchant.LIST_OF_ADDRESSES);
	}
	
	/**
	 * Sets list of addresses.
	 * @param list
	 */
	public void setListOfAddresses(ArrayList<AddressDAO> list) 
	{
		setValue(Merchant.LIST_OF_ADDRESSES, list);
	}
	
	/**
	 * Gets list of phones.
	 * @return list of phones
	 */
	public ArrayList<PhoneDAO> getListOfPhones() 
	{
		return lookupValue(Merchant.LIST_OF_PHONES);
	}
	
	/**
	 * Sets list of phones.
	 * @param list
	 */
	public void setListOfPhones(ArrayList<PhoneDAO> list) 
	{
		setValue(Merchant.LIST_OF_PHONES, list);
	}
	
	/**
	 * Gets list of domain emails.
	 * @return list of emails 
	 */
	public ArrayList<NVPair> getListOfDomainEmails() 
	{
		return lookupValue(Merchant.LIST_OF_DOMAIN_EMAILS);
	}
	
	/**
	 * Sets list of domain emails.
	 * @param list
	 */
	public void setListOfDomainEmails(ArrayList<NVPair> list) 
	{
		setValue(Merchant.LIST_OF_DOMAIN_EMAILS, list);
	}
	
	/**
	 * Gets list of payment information.
	 * @return list of payment infos
	 */
	public ArrayList<PaymentInfoDAO> getListOfPaymentInfos() 
	{
		return lookupValue(Merchant.LIST_OF_PAYMENT_INFOS);
	}
	
	/**
	 * Sets list of payment information.
	 * @param list
	 */
	public void setListOfPaymentInfos(ArrayList<PaymentInfoDAO> list) 
	{
		setValue(Merchant.LIST_OF_PAYMENT_INFOS, list);
	}
	
	/**
	 * Gets additional information.
	 * @return list of additional info
	 */
	public ArrayList<NVPair> getAdditonalInfos() 
	{
		return lookupValue(Merchant.ADDITIONAL_INFOS);
	}
	
	/**
	 * Sets additional information.
	 * @param info
	 */
	public void setAdditionalInfos(ArrayList<NVPair> info)
	{
		setValue(Merchant.ADDITIONAL_INFOS, info);
	}
	
	/**
	 * Sets the name.
	 * @param name
	 */
	public void setName(String name) 
			throws NullPointerException, IllegalArgumentException
	{
		SharedUtil.checkIfNulls("Name cannot be empty or null.", SharedStringUtil.trimOrNull(name));
		setValue(DataParam.NAME, name);
	}
	
}