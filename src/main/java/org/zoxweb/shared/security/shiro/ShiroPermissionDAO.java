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
package org.zoxweb.shared.security.shiro;

import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class ShiroPermissionDAO
	extends ShiroDomainDAO
{
	
	private static final NVConfig NVC_PATTERN =  NVConfigManager.createNVConfig("pattern", null,"Pattern",true, false, String.class);
	
	public static final NVConfigEntity  NVC_SHIRO_PERMISSION_DAO = new NVConfigEntityLocal("shiro_permission_dao", "Shiro permission dao object" , 
			"ShiroPermissionDAO", false, true, false, false, ShiroPermissionDAO.class, SharedUtil.toNVConfigList(NVC_PATTERN),
			null, false, ShiroDomainDAO.NVC_SHIRO_DOMAIN_DAO);
	
	public ShiroPermissionDAO()
	{
		super(NVC_SHIRO_PERMISSION_DAO);
	}
	
	public ShiroPermissionDAO(String domainID, String name, String description, String pattern)
	{
		this();
		setDomainID( domainID);
		setName(name);
		setDescription(description);
		setPermissionPattern(pattern);
	}
	
//	public boolean equals(Object o)
//	{
//		if (this == o)
//		{
//			return true;
//		}
//			
//		if (o != null && o instanceof ShiroPermissionDAO)
//		{
//			ShiroPermissionDAO to = (ShiroPermissionDAO) o;
//			
//			if (SharedUtil.referenceIDToLong(this) == 0 && SharedUtil.referenceIDToLong(to) == 0)
//			{
//				return toCanonicalID().equals(to);
//			}
//			if (getReferenceID() == to.getReferenceID())
//			{
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	@Override
	public String toCanonicalID()
	{
		return SharedUtil.toCanonicalID(CAN_ID_SEP, getDomainID(), getName(), getPermissionPattern());
	}
	
	public String getPermissionPattern() 
	{
		return lookupValue(NVC_PATTERN);	
	}
	
	public void setPermissionPattern(String pattern)
	{
		setValue(NVC_PATTERN, SharedStringUtil.trimOrEmpty(SharedStringUtil.toLowerCase(pattern)));
	}
	
}