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

import java.io.Serializable;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * The login token data access object.
 * @author mzebib
 */
@SuppressWarnings("serial")
public class LoginTokenDAO 
	extends SetNameDescriptionDAO
	implements Serializable
{
	
	/**
	 * This enum contains login token parameters.
	 * @author mzebib
	 *
	 */
	public enum Param
		implements GetNVConfig
	{
		LAST_NAME(NVConfigManager.createNVConfig("last_name", "Last name", "LastName", false, true, String.class)),
		LOGIN_ID(NVConfigManager.createNVConfig("login_id", "Login ID", "LoginID", false, true, String.class)),
		SESSION_ID(NVConfigManager.createNVConfig("session_id", "Session ID", "SessionID", false, true, String.class)),
		
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
	
	public static final NVConfigEntity NVC_LOGIN_IN_DAO = new NVConfigEntityLocal(
																					"login_token_dao", 
																					null , 
																					"LoginTokenDAO", 
																					true, 
																					false, 
																					false, 
																					false, 
																					LoginTokenDAO.class, 
																					SharedUtil.extractNVConfigs(Param.values()), 
																					null, 
																					false, 
																					SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);

	/**
	 * The default constructor.
	 */
	public LoginTokenDAO()
	{
		super(NVC_LOGIN_IN_DAO);
	}
	
	
	
	/**
	 * Gets last name.
	 * 
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lookupValue(Param.LAST_NAME);
	}
	
	/**
	 * Sets last name.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName)
	{
		setValue(Param.LAST_NAME, lastName);
	}
	
	/**
	 * Gets the login ID.
	 * 
	 * @return login id
	 */
	public String getLoginID() 
	{
		return lookupValue(Param.LOGIN_ID);
	}
	
	/**
	 * Sets the login ID.
	 * 
	 * @param loginID
	 */
	public void setLoginID(String loginID)
	{
		setValue(Param.LOGIN_ID, loginID);
	}
	
	
	/**
	 * Gets the current session ID optional.
	 * 
	 * @return session id
	 */
	public String getSessionID() 
	{
		return lookupValue(Param.SESSION_ID);
	}
	
	/**
	 * Sets the session ID.
	 * 
	 * @param sessionID
	 */
	public void setSessionID(String sessionID)
	{
		setValue(Param.SESSION_ID, sessionID);
	}
	
}