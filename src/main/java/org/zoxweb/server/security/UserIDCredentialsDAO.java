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
package org.zoxweb.server.security;

import java.util.Date;

import org.zoxweb.shared.crypto.PasswordDAO;
import org.zoxweb.shared.data.SetNameDescriptionDAO;

import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetValue;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This class defines user credentials data access object used to
 * create and store user credentials.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class UserIDCredentialsDAO 
	extends SetNameDescriptionDAO
{
	/**
	 * This enum contains user status with a specified status
	 * expiration time.
	 * @author mzebib
	 *
	 */
	public enum UserStatus 
		implements GetValue<Long>
	{
		// Note: 
		//	0 = no expiration time
		// -1 = expiration time is irrelevant
		ACTIVE(0),
		DEACTIVATED(0),
		INACTIVE(-1),
		PENDING_RESET_PASSWORD(Const.TimeInMillis.DAY.MILLIS * 2),
		PENDING_ACCOUNT_ACTIVATION(Const.TimeInMillis.DAY.MILLIS * 2)		
		
		;

		private final long EXPIRATION_TIME;
		
		UserStatus(long time)
		{
			EXPIRATION_TIME = time;
		}
		
		
		@Override
		public Long getValue()
		{
			return EXPIRATION_TIME;
		}
		
	}
	
	
	/**
	 * This enum contains user credential variables including: 
	 * user id, user status, last status update time stamp, pending token,
	 * pending pin, and password.
	 * @author mzebib
	 *
	 */
	public enum UserCredentials
		implements GetNVConfig
	{
		//USER_ID_DAO(NVConfigManager.createNVConfigEntity("user_id_dao", "User ID data access object.", "UserIDDAO", true, true, UserIDDAO.NVC_USER_ID_DAO)),
		USER_STATUS(NVConfigManager.createNVConfig("user_status", "User status", "UserStatus", true, true, UserStatus.class)),
		LAST_STATUS_UPDATE_TIMESTAMP(NVConfigManager.createNVConfig("last_status_update_timestamp", "Timestamp of last status update", "LastStatusUpdateTimestamp", true, true, Date.class)),
		PENDING_TOKEN(NVConfigManager.createNVConfig("pending_token", "Pending token", "PendingToken", true, true, String.class)),
		PENDING_PIN(NVConfigManager.createNVConfig("pending_pin", "Pending pin", "PendingPin", true, true, String.class)),
		PASSWORD(NVConfigManager.createNVConfigEntity("password", "Password", "Password", true, true, PasswordDAO.NVCE_PASSWORD_DAO)),
				
		;
		
		private final NVConfig cType;
		UserCredentials(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}

	}
	
	/**
	 * This NVConfigEntity type constant is set to an instantiation of a NVConfigEntityLocal object based on UserCredentialsStatusDAO.
	 */
	public static final NVConfigEntity NVC_USER_ID_CREDENTIALS_DAO = new NVConfigEntityLocal(
																								"user_id_credentials_dao", 
																								 null,
																								 "UserIDCredentialsDAO",
																								 true,
																								 false,
																								 false, 
																								 false,
																								 UserIDCredentialsDAO.class, 
																								 SharedUtil.extractNVConfigs(UserCredentials.values()),
																								 null,
																								 false,
																								 SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																							);
	
	
	/**
	 * This is the default constructor.
	 */
	public UserIDCredentialsDAO()
	{
		super(NVC_USER_ID_CREDENTIALS_DAO);
	}



	
	/**
	 * This method returns the user status.
	 * @return UserStatus
	 */
	public UserStatus getUserStatus() 
	{
		return lookupValue(UserCredentials.USER_STATUS);
	}
	
	/**
	 * This method sets the user status.
	 * @param status
	 */
	public void setUserStatus(UserStatus status)
	{
		setValue(UserCredentials.USER_STATUS, status);
	}
	
	
	/**
	 * This method returns the time stamp of the last status update.
	 * @return in millis last status update
	 */
	public long getLastStatusUpdateTimestamp() 
	{
		return lookupValue(UserCredentials.LAST_STATUS_UPDATE_TIMESTAMP);
	}
	
	/**
	 * This method sets the time stamp for the last status update.
	 * @param timestamp
	 */
	public void setLastStatusUpdateTimestamp(long timestamp)
	{
		setValue(UserCredentials.LAST_STATUS_UPDATE_TIMESTAMP, timestamp);
	}
	
	
	/**
	 * This method returns the pending token.
	 * @return the pending token
	 */
	public String getPendingToken() 
	{
		return lookupValue(UserCredentials.PENDING_TOKEN);
	}
	
	/**
	 * This method sets the pending token.
	 * @param token
	 */
	public void setPendingToken(String token)
	{
		setValue(UserCredentials.PENDING_TOKEN, token);
	}
	
	
	/**
	 * This method returns the pending pin.
	 * @return pending pin
	 */
	public String getPendingPin() 
	{
		return lookupValue(UserCredentials.PENDING_PIN);
	}
	
	/**
	 * This method sets the pending pin.
	 * @param pin
	 */
	public void setPendingPin(String pin)
	{
		setValue(UserCredentials.PENDING_PIN, pin);
	}
	
	
	/**
	 * This method returns the password.
	 * @return password dao
	 */
	public PasswordDAO getPassword() 
	{
		return lookupValue(UserCredentials.PASSWORD);
	}
	
	/**
	 * This method sets the password.
	 * @param password
	 */
	public void setPassword(PasswordDAO password)
	{
		setValue(UserCredentials.PASSWORD, password);
	}
	
}
