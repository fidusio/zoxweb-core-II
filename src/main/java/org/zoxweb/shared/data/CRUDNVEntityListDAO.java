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
package org.zoxweb.shared.data;

import java.util.List;

import org.zoxweb.shared.util.CRUD;
import org.zoxweb.shared.util.CRUDNVEntityList;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

/**
 * 
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class CRUDNVEntityListDAO
	extends SetNameDAO
	implements CRUDNVEntityList
{

	public enum Param
		implements GetNVConfig
	{
		CRUD_OP(NVConfigManager.createNVConfig("crud_op", "CRUD operation", "CRUDOperation", true, false, CRUD.class)),
		NVE_LIST(NVConfigManager.createNVConfigEntity("nve_list", "NVEntity", "NVEntity", false, true, NVEntity.class, ArrayType.LIST)),

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
	
	public static final NVConfigEntity NVC_CRUD_NVENTITY_LIST_DAO = new NVConfigEntityLocal("crud_nventity_list_dao", null , "CRUDNVEntityListDAO", true, false, false, false, CRUDNVEntityListDAO.class, SharedUtil.extractNVConfigs(Param.values()), null, false, SetNameDAO.NVC_NAME_DAO);

	public CRUDNVEntityListDAO()
	{
		super(NVC_CRUD_NVENTITY_LIST_DAO);
	}
	
	public CRUDNVEntityListDAO(CRUD crud, List<NVEntity> nve)
	{
		this();
		setCRUD(crud);
		setNVEntityList(nve);
	}
	
	@Override
	public CRUD getCRUD() 
	{
		return lookupValue(Param.CRUD_OP);
	}
	
	public void setCRUD(CRUD crud)
	{
		setValue(Param.CRUD_OP, crud);
	}
	
	@Override
	public List<NVEntity> getNVEntityList()
	{
		return lookupValue(Param.NVE_LIST);
	}
	
	public void setNVEntityList(List<NVEntity> nve)
	{
		setValue(Param.NVE_LIST, nve);
	}

}
