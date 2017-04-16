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

import org.zoxweb.shared.util.CRUD;
import org.zoxweb.shared.util.CRUDNVConfig;
import org.zoxweb.shared.util.NVConfig;

/**
 *
 */
@SuppressWarnings("serial")
public class CRUDNVConfigDAO 
	extends CRUDEventDAO 
	implements CRUDNVConfig
{

	private NVConfig nvc;
	
	public CRUDNVConfigDAO(CRUD crud, NVConfig nvc)
	{
		super(crud);
		this.nvc = nvc;
	}
	
	@Override
	public NVConfig getNVConfig() 
	{
		return nvc;
	}

}