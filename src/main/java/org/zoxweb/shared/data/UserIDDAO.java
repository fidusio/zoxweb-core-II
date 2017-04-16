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



import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This class defines the user id data access object used to create
 * user id for access.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class UserIDDAO 
	extends SetNameDescriptionDAO
{
	
	/**
	 * This enum includes the following parameters:
	 * primary email and user information.
	 */
	public enum Params
		implements GetNVConfig
	{
		PRIMARY_EMAIL(NVConfigManager.createNVConfig("primary_email", "Primary email address", "PrimaryEmail", true, true, true, String.class, FilterType.EMAIL)),
		USER_INFO(NVConfigManager.createNVConfigEntity("user_info", "User information", "UserInfo", true, true, UserInfoDAO.NVC_USER_INFO_DAO)),
		
		;

        private final NVConfig nvc;

        Params(NVConfig nvc)
        {
            this.nvc = nvc;
        }

        public NVConfig getNVConfig()
        {
            return nvc;
        }

	}
	
	public static final NVConfigEntity NVC_USER_ID_DAO = new NVConfigEntityLocal("user_id_dao", null , "UserIDDAO", true, false, false, false, UserIDDAO.class, SharedUtil.extractNVConfigs(Params.values()), null, false, SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
	
	
	/**
	 * The default constructor.
	 */
	public UserIDDAO()
	{
		super(NVC_USER_ID_DAO);
	}

//	protected UserIDDAO(List<NVConfigEntity> list)
//	{
//		super(SharedUtil.merge(list, NVC_USER_ID_DAO));
//	}
	
	/**
	 * Gets the primary email.
	 * @return primary email
	 */
	public String getPrimaryEmail() 
	{
		return lookupValue(Params.PRIMARY_EMAIL);
	}
	
	/**
	 * Sets the primary email.
	 * @param email
	 */
	public void setPrimaryEmail(String email)
	{
		setValue(Params.PRIMARY_EMAIL, email);
	}
	
	/**
	 * Gets the user information.
	 * @return user info dao
	 */
	public UserInfoDAO getUserInfo() 
	{
		return lookupValue(Params.USER_INFO);
	}
	
	/**
	 * Sets the user information.
	 * @param user
	 */
	public void setUserInfo(UserInfoDAO user)
	{
		setValue(Params.USER_INFO, user);
	}
	
	/**
	 * Gets the user ID.
	 * @return user id
	 */
	@Override
	public String getUserID()
	{
		return getReferenceID();
	}
	
}