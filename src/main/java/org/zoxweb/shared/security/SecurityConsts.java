/*
 * Copyright (c) 2012-Oct 23, 2015 ZoxWeb.com LLC.
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

import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.http.HTTPMethod;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetValue;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.Const.TimeInMillis;


/**
 * @author mnael
 *
 */
public final class SecurityConsts
{
	/**
	 * Non Instantiatable object
	 */
	private SecurityConsts()
	{
	}
	
	public enum OAuthParam
		implements GetName,GetNVConfig
	{
		ACCESS_TOKEN(NVConfigManager.createNVConfig("access_token", "Access token", "AccessToken", false, true, false, String.class, FilterType.ENCRYPT)),
		AUTHORIZATION(NVConfigManager.createNVConfig("authorization", "The Authorization", "Authorization", true, true, String.class)),
		AUTHORIZATION_CODE(NVConfigManager.createNVConfig("authorization_code", "The Authorization code", "AuthorizationCode", true, true, String.class)),
		BEARER(NVConfigManager.createNVConfig("bearer", "Bearer", "Bearer", true, true, String.class)),
		CLIENT_ID(NVConfigManager.createNVConfig("client_id", "OAUTH client identifier", "ClientID", true, true, String.class)),
		CLIENT_SECRET(NVConfigManager.createNVConfig("client_secret", "OAUTH client secret", "ClientSecret", true, true, String.class)),
		EXPIRES_IN(NVConfigManager.createNVConfig("expires_in", "Expiration time value", "ExpiresIn", false, true, Integer.class)),
		EXPIRATION_UNIT(NVConfigManager.createNVConfig("expiration_unit", "Expiration time unit", "ExpirationUnit", false, true, TimeInMillis.class)),
		CODE(NVConfigManager.createNVConfig("code", "The code", "Code", true, true, String.class)),
		GRANT_TYPE(NVConfigManager.createNVConfig("grant_type", "The grant type", "GrantType", true, true, String.class)),
		REFRESH_TOKEN(NVConfigManager.createNVConfig("refresh_token", "Refresh token", "RefreshToken", false, true, false, String.class, FilterType.ENCRYPT)),
		TOKEN_TYPE(NVConfigManager.createNVConfig("token_type", "Token type", "TokenType", false, true, String.class)),
		;
		
		private NVConfig nvConfig;
		
		OAuthParam(NVConfig config)
		{
			nvConfig = config;
		}
		
		
		/* (non-Javadoc)
		 * @see org.zoxweb.shared.util.GetName#getName()
		 */
		@Override
		public String getName()
		{
			// TODO Auto-generated method stub
			return getNVConfig().getName();
		}
		
		public String toString()
		{
			return getName();
		}


		/* (non-Javadoc)
		 * @see org.zoxweb.shared.util.GetNVConfig#getNVConfig()
		 */
		@Override
		public NVConfig getNVConfig()
		{
			// TODO Auto-generated method stub
			return nvConfig;
		}
	}
	
	
	
	public enum SystemURI
	implements GetValue<String>
	{
		REGISTER(HTTPMethod.POST, "register"),
		DEREGISTER(HTTPMethod.POST, "deregister"),
		VALIDATE_ACCESS_CODE(HTTPMethod.POST, "validate-access-code"),
		GENERATE_ACCESS_CODE(HTTPMethod.POST, "generate-access-code"),
		;

		private final HTTPMethod method;
		private final String value;
		SystemURI(HTTPMethod method, String value)
		{
			this.method = method;
			this.value = value;
		}
		/* (non-Javadoc)
		 * @see org.zoxweb.shared.util.GetValue#getValue()
		 */
		@Override
		public String getValue()
		{
			// TODO Auto-generated method stub
			return value;
		}
		
		public final HTTPMethod getHTTPMethod()
		{
			return method;
		}
	}
	
	
}
