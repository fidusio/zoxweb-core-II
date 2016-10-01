package org.zoxweb.shared.data;

import java.util.Date;

import org.zoxweb.shared.filters.ChainedFilter;
import org.zoxweb.shared.filters.CreditCardNumberFilter;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.EmailID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

@SuppressWarnings("serial")
public class DetailedCreditCardDAO
	extends CanonicalIDDAO
	implements EmailID
{
	
	public enum Params
		implements GetNVConfig
	{
		EMAIL(NVConfigManager.createNVConfig("email", "The issuer's email address.", "Email", false, true, false, String.class, FilterType.EMAIL)),
		PHONE_NUMBER(NVConfigManager.createNVConfigEntity("phone_number", "Phone number.", "Phone Number", false, true, PhoneDAO.class, ArrayType.NOT_ARRAY)),

		CARD_HOLDER_NAME(NVConfigManager.createNVConfig("card_holder_name", "Name of card holder", "Cardholder Name", true, true, String.class)),
		CARD_TYPE(NVConfigManager.createNVConfig("credit_card_type", "Type of credit card", "Credit Card Type", true, true, CreditCardType.class)),
		@SuppressWarnings("unchecked")
		CARD_NUMBER(NVConfigManager.createNVConfig("card_number", "Credit card number", "CardNumber", true, true, false, String.class, new ChainedFilter(CreditCardNumberFilter.SINGLETON, FilterType.ENCRYPT))),
		EXPIRATION_DATE(NVConfigManager.createNVConfig("expiration_date", "Card expiration date", "Expiration Date", true, true, Date.class)),
		SECURITY_CODE(NVConfigManager.createNVConfig("security_code", "Card security code", "Security Code", true, true, String.class)),
		
		FRONTEND_IMAGE(NVConfigManager.createNVConfigEntity("frontend_image", "Frontend image of credit card.", "Frontend Image", false, true, FileInfoDAO.class, ArrayType.NOT_ARRAY)),		
		BACKEND_IMAGE(NVConfigManager.createNVConfigEntity("backend_image", "Backend image of credit card.", "Backend Image", false, true, FileInfoDAO.class, ArrayType.NOT_ARRAY)),		
		
		BILLING_ADDRESS(NVConfigManager.createNVConfigEntity("billing_address", "The credit card billing address.", "Billing Address", true, true, AddressDAO.class, ArrayType.NOT_ARRAY)),		

		;
		
		private final NVConfig cType;
		
		Params(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}
	}
	
	public static final NVConfigEntity NVC_DETAILED_CREDIT_CARD_DAO = new NVConfigEntityLocal
																							(
																								"detailed_credit_card_dao", 
																								null , 
																								"Detailed Credit Card", 
																								true, 
																								false, 
																								false, 
																								false, 
																								DetailedCreditCardDAO.class, 
																								SharedUtil.extractNVConfigs(Params.values()), 
																								null, 
																								false, 
																								CanonicalIDDAO.NVC_CANONICAL_ID_DAO
																							);

	public DetailedCreditCardDAO()
	{
		super(NVC_DETAILED_CREDIT_CARD_DAO);
	}
	
	
	public String getEmailID() 
	{
		return lookupValue(Params.EMAIL);
	}
	
	public void setEmailID(String email) 
	{
		setValue(Params.EMAIL, email);
	}
	
	
	public PhoneDAO getPhoneNumber() 
	{
		return lookupValue(Params.PHONE_NUMBER);
	}
	
	public void setPhoneNumber(PhoneDAO phone) 
	{
		setValue(Params.PHONE_NUMBER, phone);
	}
	
	
	public String getCardHolderName() 
	{
		return lookupValue(Params.CARD_HOLDER_NAME);
	}
	
	public void setCardHolderName(String name) 
	{
		setValue(Params.CARD_HOLDER_NAME, name);
	}
	
	
	public CreditCardType getCardType() 
	{
		return lookupValue(Params.CARD_TYPE);
	}
	
	public void setCardType(CreditCardType type) 
	{
		setValue(Params.CARD_TYPE, type);
	}
	
	
	public String getCardNumber()
	{	
		return lookupValue(Params.CARD_NUMBER);
	}
	
	public void setCardNumber(String number)
	{
		setValue(Params.CARD_NUMBER, number);
	}
	
	
	public long getExpirationDate()
	{
		return lookupValue(Params.EXPIRATION_DATE);
	}
	
	public void setExpirationDate(long date) 
	{
		setValue(Params.EXPIRATION_DATE, date);				
	}
	
	
	public String getSecurityCode()
	{
		return lookupValue(Params.SECURITY_CODE);
	}

	public void setSecurityCode(String code)
	{
		setValue(Params.SECURITY_CODE, CreditCardNumberFilter.validateCVV(getCardNumber(), code));				
	}
	
	
	public FileInfoDAO getFrontEndImage() 
	{
		return lookupValue(Params.FRONTEND_IMAGE);
	}
	
	public void setFrontEndImage(FileInfoDAO file) 
	{
		setValue(Params.FRONTEND_IMAGE, file);
	}
	
	
	public FileInfoDAO getBackEndImage() 
	{
		return lookupValue(Params.BACKEND_IMAGE);
	}
	
	public void setBackEndImage(FileInfoDAO file) 
	{
		setValue(Params.BACKEND_IMAGE, file);
	}
	
	
	public AddressDAO getBillingAddress() 
	{
		return lookupValue(Params.BILLING_ADDRESS);
	}
	
	public void setBillingAddress(AddressDAO address) 
	{
		setValue(Params.BILLING_ADDRESS, address);
	}

}