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

import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SetCanonicalID;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;

/**
 * This class is a generic class configurator 
 * @author mnael
 *
 */
@SuppressWarnings("serial")
public class ConfigDAO 
	extends SetNameDescriptionDAO
	implements SetCanonicalID
{
	/**
	 * This enum contains data content variables which include:
	 * canonical ID, content type, content, and language.
	 * @author mzebib
	 *
	 */
	
	private volatile  Object attachment;

	public enum Param
		implements GetNVConfig
	{
		CANONICAL_ID(NVConfigManager.createNVConfig("canonical_id", "Canonical ID", "CanonicalID", false, true, String.class)),
		BEAN_CLASS_NAME(NVConfigManager.createNVConfig("bean_class_name", "Bean class name", "BeanClassName", false, true, String.class)),
		PROPERTIES(NVConfigManager.createNVConfig("properties", "Configuration properties", "Properties", false, true, String[].class)),
		CONTENT(NVConfigManager.createNVConfigEntity("content", "Sub configuration", "Content", false, true, NVEntity[].class, ArrayType.GET_NAME_MAP)),
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
	
	/**
	 * This NVConfigEntity type constant is set to an instantiation of a NVConfigEntityLocal object based on DataContentDAO.
	 */
	public static final NVConfigEntity NVC_CONFIG_DAO = new NVConfigEntityLocal("config_dao",
																				null,
																				"ConfigDAO", 
																				true,
																				false,
																				false,
																				false,
																				ConfigDAO.class,
																				SharedUtil.extractNVConfigs(Param.values()),
																				null,
																				false,
																				SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
																					
	
	/**
	 * This is the default constructor.
	 */
	public ConfigDAO()
	{
		super(NVC_CONFIG_DAO);
	}
	
	
	public ConfigDAO(String name)
	{
		this();
		setName(name);
	}

	/**
	 * Returns string representation of this class.
	 */
	@Override
	public String toCanonicalID() 
	{
		return null;
	}
	
	/**
	 *Returns canonical ID. 
	 */
	@Override
	public String getCanonicalID() 
	{
		return lookupValue(Param.CANONICAL_ID);
	}

	/**
	 * Sets canonical ID.
	 */
	@Override
	public void setCanonicalID(String id) 
	{
		setValue(Param.CANONICAL_ID, id);
	}
	
	/**
	 * Returns content type.
	 * @return the bean class name
	 */
	public String getBeanClassName()
	{
		return lookupValue(Param.BEAN_CLASS_NAME);
	}

	/**
	 * Sets content type.
	 * @param type
	 */
	public void setBeanClassName(String type)
	{
		setValue(Param.BEAN_CLASS_NAME, type);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayValues<GetNameValue<String>> getProperties()
	{
		return (ArrayValues<GetNameValue<String>>) lookup(Param.PROPERTIES);
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayValues<NVEntity> getContent()
	{
		return (ArrayValues<NVEntity>) lookup(Param.CONTENT);
	}
	
	
	public Object attachment()
	{
		return attachment;
	}
	
	public void attach(Object attachment)
	{
		this.attachment = attachment;
	}
}
