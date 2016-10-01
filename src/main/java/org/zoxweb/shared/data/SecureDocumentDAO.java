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

import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * 
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class SecureDocumentDAO
	extends SimpleDocumentDAO
{
		// sender id in this case user id
		// timestamp
		// message id ie refid
		// content as NVEntity
	
	public enum Params
		implements GetNVConfig
	{
		CONTENT(NVConfigManager.createNVConfig("content", "Content of the document", "Content", false, true, false, false, String.class, FilterType.ENCRYPT)),
		
		;
		
		private final NVConfig cType;
		
		Params(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}
	}
	
	public static final NVConfigEntity NVC_SECURE_DOCUMENT_DAO = new NVConfigEntityLocal(
																						    "secure_document_dao",
																							"Secure Note",
																							"SecureNote", 
																							true,
																							false,
																							false,
																							false,
																							SecureDocumentDAO.class,
																							SharedUtil.extractNVConfigs(Params.values()),
																							null,
																							false,
																							SimpleDocumentDAO.NVC_SIMPLE_DOCUMENT_DAO
																						);
	
	
	/**
	 * The default constructor.
	 */
	public SecureDocumentDAO() 
	{
		super(NVC_SECURE_DOCUMENT_DAO);
	}
	
	

	
}