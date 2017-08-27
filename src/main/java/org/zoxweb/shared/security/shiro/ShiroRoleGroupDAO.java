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

import java.util.List;

import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

@SuppressWarnings("serial")
public class ShiroRoleGroupDAO
	extends ShiroDomainDAO
{
	
	public enum Param
		implements GetNVConfig
	{
		
		ROLES(NVConfigManager.createNVConfigEntity("roles", "The roles associated with the role group.", "Roles", false, true, ShiroRoleDAO.class, ArrayType.REFERENCE_ID_MAP)),
		
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
	
	public static final NVConfigEntity  NVC_SHIRO_ROLE_GROUP_DAO = new NVConfigEntityLocal("shiro_rolegroup_dao", "Shiro rolegroup dao object" , 
			"ShiroRoleGroupDAO", false, true, false, false, ShiroRoleGroupDAO.class, SharedUtil.extractNVConfigs(Param.values()),
			null, false, ShiroDomainDAO.NVC_SHIRO_DOMAIN_DAO);
	
	public ShiroRoleGroupDAO()
	{
		super(NVC_SHIRO_ROLE_GROUP_DAO);
	}
	
	public ShiroRoleGroupDAO(String domainID, String name, String description)
	{
		this();
		setDomainID( domainID);
		setName( name);
		setDescription( description);
	}
	
	@Override
	public String toCanonicalID()
	{
		return SharedUtil.toCanonicalID(CAN_ID_SEP, getDomainID(), getName());
	}
	
//	public boolean equals(Object o)
//	{
//		if (this == o)
//		{
//			return true;
//		}
//			
//		if (o != null && o instanceof ShiroRoleGroupDAO)
//		{
//			ShiroRoleGroupDAO to = (ShiroRoleGroupDAO) o;
//			if (SharedUtil.referenceIDToLong(this) == 0 && SharedUtil.referenceIDToLong(to) == 0)
//			{
//				return toCanonicalID().equals(to);
//			}
//			if ( getReferenceID() == to.getReferenceID())
//			{
//				return true;
//			}
//			
//		}
//		
//		return false;
//	}
	
	@SuppressWarnings("unchecked")
	public ArrayValues<NVEntity> getRoles()
	{
		return (ArrayValues<NVEntity>) lookup(Param.ROLES);
	}
	
	public void setRoles(ArrayValues<NVEntity> values)
	{
		getRoles().add(values.values(), true);
	}
	
	public void setRoles(List<NVEntity> values)
	{
		getRoles().add(values.toArray(new NVEntity[0]), true);
	}
	
}