/*
 * Copyright (c) 2012-2020 ZoxWeb.com LLC.
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
 * @author mnael
 *
 */
@SuppressWarnings("serial")
public class SimpleMessage
	extends TimeStampDAO
{
	public enum Param
		implements GetNVConfig
	{
		MESSAGE(NVConfigManager.createNVConfig("message", "Content of the message", "Message", false, true, false, false, String.class, FilterType.CLEAR)),
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
	
	public static final NVConfigEntity NVC_SIMPLE_MESSAGE = new NVConfigEntityLocal(
            "simple_message",
            "Simple Message",
            "SimpleMessage",
            true,
            false,
            false,
            false,
            SimpleMessage.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            TimeStampDAO.NVC_TIME_STAMP_DAO
    );
	
	/**
	 * The default constructor.
	 */
	public SimpleMessage() 
	{
		super(NVC_SIMPLE_MESSAGE);
	}
	
	/**
	 * This constructor instantiates SimpleDocumentDAO based on given NVConfigEntity parameter.
	 * @param nvce
	 */
	protected SimpleMessage(NVConfigEntity nvce) 
	{
		super(nvce);
	}
	
	/**
	 * Returns message.
	 * @return message
	 */
	public String getMessage()
	{
		return lookupValue(Param.MESSAGE);
	}
	
	/**
	 * Sets message.
	 * @param message
	 */
	public void setMessage(String message)
	{
		setValue(Param.MESSAGE, message);
	}
	
}