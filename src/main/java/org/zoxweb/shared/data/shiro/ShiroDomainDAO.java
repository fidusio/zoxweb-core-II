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

import org.zoxweb.shared.data.DataConst.DataParam;
import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.DomainID;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public abstract class ShiroDomainDAO
	extends SetNameDescriptionDAO
	implements  DomainID<String>, ShiroDAO
{
	
	private static final NVConfig NVC_DOMAIN_ID =  NVConfigManager.createNVConfig("domain_id", null,"DomainID",true, false, String.class);
	
	public static final NVConfigEntity NVC_SHIRO_DOMAIN_DAO = new NVConfigEntityLocal("shiro_domain_dao", null , "ShiroDomainDAO", false, true, false, false, ShiroDomainDAO.class, SharedUtil.toNVConfigList(NVC_DOMAIN_ID), null, false, SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);//,SharedUtil.extractNVConfigs( new Params[]{Params.REFERENCE_ID, Params.NAME, Params.LENGTH}));
	
	
	protected ShiroDomainDAO(NVConfigEntity nvce) 
	{
		super(nvce);
	}
	
	public String getDomainID()
	{
		return lookupValue(NVC_DOMAIN_ID);
	}
	
	public void setDomainID(String domainID)
	{
		setValue(NVC_DOMAIN_ID, SharedStringUtil.toLowerCase( domainID));
	}
	
	public void setName(String name)
	{
		setValue(DataParam.NAME, SharedStringUtil.trimOrEmpty(SharedStringUtil.toLowerCase( name)));
	}

	@Override
	public boolean equals(Object o)
	{
		if ( this == o)
		{
			return true;
		}
			
		if ( o != null && o instanceof ShiroDomainDAO)
		{
			ShiroDomainDAO to = (ShiroDomainDAO) o;
			
			if (getReferenceID() != null && to.getReferenceID() != null)
			{
				return getReferenceID().equals(to.getReferenceID());
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return toCanonicalID();
	}
	
}