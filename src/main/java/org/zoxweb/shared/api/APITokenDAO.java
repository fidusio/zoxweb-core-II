/*
 * Copyright (c) 2012-2015 ZoxWeb.com LLC.
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
package org.zoxweb.shared.api;


import java.util.List;

import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

/**
 * The APITokenDAO contain the APIKeyDAO the APIConfigInfoDAO and the Generated token
 * 
 */
@SuppressWarnings("serial")
public class APITokenDAO extends APIAccessTokenDAO
{
	
	public enum Params
	implements GetNVConfig
	{
		API_CREDENTIALS_DAO(NVConfigManager.createNVConfigEntity("api_credentials_dao", "The Crendentials.", "APICredentialsDAO", true, true, APICredentialsDAO.class, ArrayType.NOT_ARRAY)),
		API_CONFIG_INFO_DAO(NVConfigManager.createNVConfigEntity("api_config_info_dao", "The API config info", "APIConfigInfoDAO", true, true, APIConfigInfoDAO.class, ArrayType.NOT_ARRAY)),
		API_TOKEN(NVConfigManager.createNVConfig("api_token", "The api token","APIToken", false, false, String.class)),
		//API_TOKEN_EXPIRATION(NVConfigManager.createNVConfig("api_token_expiration", "The api token expiration in real time","APITokenExpiration", false, false, Long.class)),
		EXTRA_PARAMETERS(NVConfigManager.createNVConfig("extra_parameters", "Extra configuration parameters", "ExtraParameters", false, true, true, String[].class, null)),
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
	/**
	 * This NVConfigEntity type constant is set to an instantiation of a NVConfigEntityLocal object based on API ConfigInfoDAO.
	 */
	public static final NVConfigEntity NVC_TOKEN_DAO = new NVConfigEntityLocal("api_token_dao",
																				"APITokenDAO", 
																				"APITokenDAO", 
																				true, 
																				false,
																				false,
																				false,
																				APITokenDAO.class,
																				SharedUtil.extractNVConfigs(Params.values()),
																				null, 
																				false,
																				APIAccessTokenDAO.NVC_API_ACCESS_TOKEN_DAO);
	
	public APITokenDAO()
	{
		super(NVC_TOKEN_DAO);
	}
	
	

	
	
	public APICredentialsDAO getAPICredentialsDAO()
	{
		return lookupValue(Params.API_CREDENTIALS_DAO);
		
	}
	
	public void setAPICredentialsDAO(APICredentialsDAO apiCredentials)
	{
		setValue(Params.API_CREDENTIALS_DAO, apiCredentials);
	}
	
	
	public APIConfigInfo getAPIConfigInfo()
	{
		return lookupValue(Params.API_CONFIG_INFO_DAO);
	}
	
	
	public void setAPIConfigInfo(APIConfigInfo apici)
	{
		setValue(Params.API_CONFIG_INFO_DAO, apici);
	}
	
	
	
	/**
	 * 
	 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
	 * 
	 * @return
	 * @deprecated
	 */
	public String getAPIToken()
	{
		return lookupValue(Params.API_TOKEN);
	}

	/**
	 * This method sets the API type name.
	 * @param name
	 * @deprecated
	 */
	public void setAPIToken(String apiToken) 
	{
		setValue(Params.API_TOKEN, apiToken);
	}
	
	
	
//	public long getAPITokenExpiration()
//	{
//		return lookupValue(Params.API_TOKEN_EXPIRATION);
//	}

	/**
	 * This method sets the API type name.
	 * @param name
	 */
	
//	public void setAPITokenExpiration(long expiration) 
//	{
//		setValue(Params.API_TOKEN_EXPIRATION, expiration);
//	}
	
//	public boolean isTokenExpired()
//	{
//		long expiration = getAPITokenExpiration();
//		// zero or negative value token does not expire
//		if (expiration <=0)
//			return true;
//		return (getLastTimeUpdated() + expiration - System.currentTimeMillis() > 0);
//	}
	
	@SuppressWarnings("unchecked")
	public ArrayValues<NVPair> getExtraParameters()
	{
		return (ArrayValues<NVPair>) lookup(Params.EXTRA_PARAMETERS);
	}
	
	public void setExtraParameters(List<NVPair> val)
	{
		getExtraParameters().add(val.toArray(new NVPair[0]), true);
	}
	
	
	
}
