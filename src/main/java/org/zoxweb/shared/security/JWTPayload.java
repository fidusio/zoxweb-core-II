/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
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
package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.AppID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetNVGenericMap;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.SharedUtil;
import java.util.Date;


@SuppressWarnings("serial")
public class JWTPayload 
    extends SetNameDescriptionDAO
    implements AppID<String>,
    		   GetNVGenericMap
{
	

	public enum Param
	    implements GetNVConfig
	{
		
		ISS(NVConfigManager.createNVConfig("iss", "Issuer intentifier", "Issuer", false, true, false, String.class, null)),
		SUB(NVConfigManager.createNVConfig("sub", "Subject Identifier", "SubjectID", true, true, false, String.class, null)),
		AUD(NVConfigManager.createNVConfig("aud", "Audience", "Audience", false, true, false, String.class, null)),	
		EXP(NVConfigManager.createNVConfig("exp", "Expiration time", "Expiration", false, true, false, Date.class, null)),
		NBF(NVConfigManager.createNVConfig("nbf", "Not Before", "NBF", false, true, false, Date.class, null)),
		IAT(NVConfigManager.createNVConfig("iat", "Issued At", "IAT", false, true, false, long.class, null)),
		JTI(NVConfigManager.createNVConfig("jti", "JWT ID", "JWTID", false, true, false, String.class, null)),
		//NAME(NVConfigManager.createNVConfig("name", "name", "Name", true, true, false, String.class, null)),
		ADMIN(NVConfigManager.createNVConfig("admin", "Admin", "admin", false, true, boolean.class)),
		DOMAIN_ID(NVConfigManager.createNVConfig("domain", "Domain identifier", "DomainID", false, true, false, String.class, FilterType.DOMAIN)),
		APP_ID(NVConfigManager.createNVConfig("app", "ApplicationID", "AppID", false, true, false, String.class, null)),
		NONCE(NVConfigManager.createNVConfig("nonce", "Nonce", "Nonce", false, true, false, long.class, null)),
		//RANDOM(NVConfigManager.createNVConfig("random", "Random Data", "Random", false, true, false, byte[].class, null)),
		
		
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
	
	public static final NVConfigEntity NVC_JWT_PAYLOAD = new NVConfigEntityLocal(  "jwt_payload", 
																					null , 
																					"JWTPayload", 
																					true, 
																					false, 
																					false, 
																					false, 
																					JWTPayload.class, 
																					SharedUtil.extractNVConfigs(Param.values()), 
																					null, 
																					true, 
																					SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);

	
	private final NVGenericMap genericMap;

	public JWTPayload() 
	{
		super(NVC_JWT_PAYLOAD);
		
		genericMap = SharedUtil.toNVGenricMap(this);
	
		
		//genericMap.
	}

	@Override
	public String getDomainID() 
	{
		return lookupValue(Param.DOMAIN_ID);
	}

	@Override
	public void setDomainID(String domainID) 
	{
		setValue(Param.DOMAIN_ID, domainID);
	}

	public String getAppID() 
	{
		return lookupValue(Param.APP_ID);
	}

	public void setAppID(String appID) 
	{
		setValue(Param.APP_ID, appID);
	}
	
	public long getNonce() 
	{
		return lookupValue(Param.NONCE);
	}

	public String getSubjectID() 
	{
		return lookupValue(Param.SUB);
	}

	public void setSubjectID(String sub) 
	{
		setValue(Param.SUB, sub);
	}

	public boolean isAdmin() 
	{
		return lookupValue(Param.ADMIN);
	}
	
	public void setAdmin(boolean isAdmin) 
	{
		setValue(Param.ADMIN, isAdmin);
	}

	public void setNonce(String nonce) 
	{
		setValue(Param.NONCE, nonce);
	}

//	public byte[] getRandom() 
//	{
//		return lookupValue(Param.RANDOM);
//	}
//
//
//	public void setRandom(byte[] random) 
//	{
//		setValue(Param.RANDOM, random);
//	}

	public String getAudience() 
	{
		return lookupValue(Param.AUD);
	}

	public void setAudience(String aud) 
	{
		setValue(Param.AUD, aud);
	}
	
	public long getExpirationTime() 
	{
		return lookupValue(Param.EXP);
	}

	public void setExpirationTime(long exp) 
	{
		setValue(Param.EXP, exp);
	}

	public long getNotBefore() 
	{
		return lookupValue(Param.NBF);
	}

	public void setNotBefore(long nbf) 
	{
		setValue(Param.NBF, nbf);
	}

	public long getIssuedAt() 
	{
		return lookupValue(Param.IAT);
	}

	public void setIssuedAt(long iat) 
	{
		setValue(Param.IAT, iat);
	}

	public String getJWTID() 
	{
		return lookupValue(Param.JTI);
	}

	public void setJWTID(String jti) 
	{
		setValue(Param.JTI, jti);
	}
	
	
	public NVGenericMap getNVGenericMap()
	{
		return genericMap;	
	}
	
	public void setNVGenericMap(NVGenericMap nvgm)
	{
		SharedUtil.updateGetNVGenericMap(this, nvgm);
	}

	@Override
	@Deprecated
	public String getAppGID()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
	}

	@Override
	@Deprecated
	public void setAppGID(String appGID)
		throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
