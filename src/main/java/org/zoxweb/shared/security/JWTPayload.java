package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.DomainID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.SubjectID;

@SuppressWarnings("serial")
public class JWTPayload 
extends SetNameDescriptionDAO
implements DomainID<String>, SubjectID<String>
{
	

	public enum Param
	implements GetNVConfig
	{
		DOMAIN_ID(NVConfigManager.createNVConfig("domain_id", "Domain identifier", "DomainID", true, true, false, String.class, FilterType.DOMAIN)),
		APP_ID(NVConfigManager.createNVConfig("app_id", "ApplicationID", "AppID", true, true, false, String.class, null)),
		SUBJECT_ID(NVConfigManager.createNVConfig("subject_id", "Subject Identifier", "SubjectID", false, true, false, String.class, null)),
		NONCE(NVConfigManager.createNVConfig("nonce", "ApplicationID", "Nonce", false, true, false, long.class, null)),
		RANDOM(NVConfigManager.createNVConfig("random", "Random Data", "Rendom", false, true, false, byte[].class, null)),
		SUB(NVConfigManager.createNVConfig("sub", "ApplicationID", "AppID", true, true, false, String.class, null)),
		NAME(NVConfigManager.createNVConfig("name", "ApplicationID", "AppID", true, true, false, String.class, null)),
		ADMIN(NVConfigManager.createNVConfig("admin", "ApplicationID", "AppID", true, true, boolean.class)),
		;
		
		private final NVConfig nvc;
		
		Param(NVConfig nvc)
		{
	        this.nvc = nvc;
		}
		
		public NVConfig getNVConfig() 
		{
			return nvc;
		}
	}
	
	public static final NVConfigEntity NVC_JWT_PAYLOAD = new NVConfigEntityLocal(
																					"jwt_payload", 
																					null , 
																					"JWTPayload", 
																					true, 
																					false, 
																					false, 
																					false, 
																					JWTPayload.class, 
																					SharedUtil.extractNVConfigs(Param.values()), 
																					null, 
																					false, 
																					SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);


	
	public JWTPayload() 
	{
		super(NVC_JWT_PAYLOAD);
		// TODO Auto-generated constructor stub
	}



	@Override
	public String getSubjectID() 
	{
		// TODO Auto-generated method stub
		return lookupValue(Param.SUBJECT_ID);
	}



	@Override
	public void setSubjectID(String subjectID) 
	{
		// TODO Auto-generated method stub
		setValue(Param.SUBJECT_ID, subjectID);
	}



	@Override
	public String getDomainID() 
	{
		// TODO Auto-generated method stub
		return lookupValue(Param.DOMAIN_ID);
	}



	@Override
	public void setDomainID(String domainID) 
	{
		// TODO Auto-generated method stub
		setValue(Param.DOMAIN_ID, domainID);
	}
	
	
	
	public String getAppID() 
	{
		// TODO Auto-generated method stub
		return lookupValue(Param.APP_ID);
	}



	public void setAppID(String appID) 
	{
		// TODO Auto-generated method stub
		setValue(Param.APP_ID, appID);
	}
	
	public long getNonce() 
	{
		// TODO Auto-generated method stub
		return lookupValue(Param.NONCE);
	}


	public void setSub(String sub) 
	{
		// TODO Auto-generated method stub
		setValue(Param.SUB, sub);
	}
	
	
	public boolean isAdmin() 
	{
		// TODO Auto-generated method stub
		return lookupValue(Param.ADMIN);
	}
	
	
	public void setAdmin(boolean isAdmin) 
	{
		// TODO Auto-generated method stub
		setValue(Param.ADMIN, isAdmin);
	}
	
	
	public String getSub() 
	{
		// TODO Auto-generated method stub
		return lookupValue(Param.SUB);
	}


	public void setNonce(String nonce) 
	{
		// TODO Auto-generated method stub
		setValue(Param.NONCE, nonce);
	}
	
	
	public byte[] getRandom() 
	{
		// TODO Auto-generated method stub
		return lookupValue(Param.RANDOM);
	}


	public void setRandom(byte[] random) 
	{
		// TODO Auto-generated method stub
		setValue(Param.RANDOM, random);
	}
	
	
}



