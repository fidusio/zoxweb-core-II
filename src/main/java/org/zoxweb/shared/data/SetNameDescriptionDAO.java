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

import org.zoxweb.shared.data.DataConst.DataParam;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;

import org.zoxweb.shared.util.SetDescription;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This class defines set name description data access object
 * which extends SetNameDAO.
 * @author mnael
 * @version $Revision: 1.17 $
 * @lastModifiedBy $Author: mnael $
 * @lastModifiedAt $Date: 2015/11/30 06:38:57 $
 */
@SuppressWarnings("serial")
public abstract class SetNameDescriptionDAO
	extends SetNameDAO
	implements SetDescription
{
	
	//public static final NVConfig NVC_DESCRIPTION =  NVConfigManager.createNVConfig("description", null, "Description", false, true, String.class);
	public static final NVConfigEntity NVC_NAME_DESCRIPTION_DAO = new NVConfigEntityLocal(null, null , null, true, false, false, false, SetNameDescriptionDAO.class, SharedUtil.toNVConfigList(DataParam.DESCRIPTION.getNVConfig()), null, false, SetNameDAO.NVC_NAME_DAO);
	
	/**
	 * This constructor instantiates SimpleDocumentDAO based on given NVConfigEntity parameter.
	 * @param nvce
	 */
	protected SetNameDescriptionDAO(NVConfigEntity nvce) 
	{
		super(nvce);
	}
	
	/**
	 * Gets the description.
	 * @return
	 */
	public String getDescription() 
	{
		return lookupValue(DataParam.DESCRIPTION);
	}

	/**
	 * Sets the description.
	 * @param description
	 */
	public void setDescription(String description) 
			throws NullPointerException, IllegalArgumentException
	{
		setValue(DataParam.DESCRIPTION, description);
	}

}
