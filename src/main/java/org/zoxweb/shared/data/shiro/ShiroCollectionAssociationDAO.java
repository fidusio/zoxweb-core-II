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
package org.zoxweb.shared.data.shiro;

import java.util.ArrayList;

import org.zoxweb.shared.data.SetNameDAO;
import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class ShiroCollectionAssociationDAO
	extends SetNameDAO
{

	private enum Param
	    implements GetNVConfig
	{
		SHIRO_DAO(NVConfigManager.createNVConfigEntity("shiro_dao", "The shiro dao", "ShiroDAO", false, false, ShiroDAO.class, ArrayType.NOT_ARRAY)),
		ASSOCIATED_DAOS(NVConfigManager.createNVConfigEntity("associated_daos", "The shiro associated daos", "AssociatedDAOS", false, false, ShiroDAO[].class, ArrayType.LIST)),
		TO_BE_ASSOCIATED_DAOS(NVConfigManager.createNVConfigEntity("to_be_associated_daos", "The shiro to be associated daos", "ToBeAssociatedDAOS", false, false, ShiroDAO[].class, ArrayType.LIST)),
		SHIRO_ASSOCIATION_TYPE(NVConfigManager.createNVConfig("shiro_association_type", "The shiro dao association type", "ShiroAssociationType", true, false, ShiroAssociationType.class)),
		
		;

		private final NVConfig nvc;

        Param( NVConfig nvc)
		{
			this.nvc = nvc;
		}

		@Override
		public NVConfig getNVConfig() 
		{
			return nvc;
		}
	}
	
	private static final NVConfigEntity NVC_SHIRO_COLLECTION_ASSOCIATION_DAO = new NVConfigEntityLocal("shiro_collection_association_dao", "" , "ShiroCollectionAssociationDAO", false, true, false, false,
			ShiroCollectionAssociationDAO.class, SharedUtil.extractNVConfigs(Param.values()), null, false, SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
	
	public ShiroCollectionAssociationDAO() 
	{
		super( NVC_SHIRO_COLLECTION_ASSOCIATION_DAO);
	}
	
	public ShiroDAO getShiroDAO()
    {
		return lookupValue(Param.SHIRO_DAO);
	}

	public void setShiroDAO(ShiroDAO shiroDao)
    {
		setValue(Param.SHIRO_DAO, shiroDao);
	}

	public ArrayList<ShiroDAO> getAssociatedDAOs()
    {
		return lookupValue(Param.ASSOCIATED_DAOS);
	}

	public void setAssociatedDAOs(ArrayList< ShiroDAO> associatedDao)
    {
		setValue(Param.ASSOCIATED_DAOS, associatedDao);
	}

	public ShiroAssociationType getAssociationType()
    {
		return lookupValue( Param.SHIRO_ASSOCIATION_TYPE);
	}

	public void setAssociationType(ShiroAssociationType associationType)
    {
		setValue(Param.SHIRO_ASSOCIATION_TYPE, associationType);
	}

	public ArrayList<ShiroDAO> getToBeAssociatedDAOs()
    {
		return lookupValue(Param.TO_BE_ASSOCIATED_DAOS);
	}

	public void setToBeAssociatedDAOs(ArrayList< ShiroDAO> toAssociateDao) {
		setValue(Param.TO_BE_ASSOCIATED_DAOS, toAssociateDao);
	}
	
}