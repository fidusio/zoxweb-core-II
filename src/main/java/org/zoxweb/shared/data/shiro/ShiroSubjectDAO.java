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

package org.zoxweb.shared.data.shiro;

import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class ShiroSubjectDAO
	extends ShiroDomainDAO
{
	
	private static final NVConfig NVC_AUTHENTICATION_REALM =  NVConfigManager.createNVConfig("authentication_realm", null,"AuthenticationRealm",true, false, String.class);
	private static final NVConfig NVC_PASSWORD =  NVConfigManager.createNVConfig("password", null,"Password",true, false, String.class);
	
	public static final NVConfigEntity  NVC_SHIRO_SUBJECT_DAO = new NVConfigEntityLocal("shiro_subject_dao", "Shiro subject dao object" , "ShiroSubjectDAO", false, true, false, false, ShiroSubjectDAO.class, SharedUtil.toNVConfigList(NVC_AUTHENTICATION_REALM, NVC_PASSWORD), null, false, ShiroDomainDAO.NVC_SHIRO_DOMAIN_DAO);
	
	public ShiroSubjectDAO()
	{
		super(NVC_SHIRO_SUBJECT_DAO);
	}
	
	public ShiroSubjectDAO(String domainID, String authenticationRelam, String userName, String passwd)
	{
		this();
		setDomainID(domainID);
		setName( userName);
		setAuthenticationRealm(authenticationRelam);
		setPassword( passwd);
	}
	
	public String getAuthenticationRealm()
	{
		return lookupValue(NVC_AUTHENTICATION_REALM);
	}

	public void setAuthenticationRealm(String authenticationRealm) 
	{
		setValue(NVC_AUTHENTICATION_REALM, authenticationRealm);
	}

	public String getPassword()
	{
		return lookupValue(NVC_PASSWORD);
	}

	public void setPassword(String password) 
	{		
		setValue(NVC_PASSWORD, password);
	}
	
//	public boolean equals(Object o)
//	{
//		if ( this == o)
//		{
//			return true;
//		}
//			
//		if ( o != null && o instanceof ShiroSubjectDAO)
//		{
//			ShiroSubjectDAO to = (ShiroSubjectDAO) o;
//			if (getReferenceID() != null && to.getReferenceID() != null)
//				return getReferenceID().equals(to.getReferenceID());
//		}
//		return false;
//	}
	
	
	@Override
	public String toCanonicalID()
	{
		return SharedUtil.toCanonicalID(CAN_ID_SEP, getDomainID(), getAuthenticationRealm(), getName());
	}
	
}