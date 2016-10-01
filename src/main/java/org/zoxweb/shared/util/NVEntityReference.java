/*
 * Copyright 2012 ZoxWeb.com LLC.
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
package org.zoxweb.shared.util;

/**
 * This class declares NVBase as NVEntity type.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class NVEntityReference 
	extends NVBase<NVEntity>
	implements GetNVConfig
{
	private NVConfigEntity nvce = null;
	
	/**
	 * Default constructor used for Java Bean Compiler.
	 */
	public NVEntityReference()
	{
		
	}
	
	/**
	 * This constructor instantiates NVEntityReference based on value of NVConfig type.
	 * @param config
	 */
	public NVEntityReference(NVConfig config)
	{
		super(config.getName(), null);
		nvce = (NVConfigEntity) config;
	}

	/**
	 * This method returns the NVConfig.
	 * @return
	 */
	@Override
	public NVConfig getNVConfig()
	{
		return nvce;
	}
	
}
