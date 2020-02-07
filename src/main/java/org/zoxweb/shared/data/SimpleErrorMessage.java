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
public class SimpleErrorMessage
	extends SimpleMessage
{
	public enum Param
		implements GetNVConfig
	{
		ERROR_CODE(NVConfigManager.createNVConfig("error_code", "Error code", "ErrorCode", false, true, int.class)),
		ERROR_CATEGORY(NVConfigManager.createNVConfig("error_category", "Error category", "ErrorCategory", false, true, false, false, String.class, FilterType.CLEAR)),
		ERROR_TYPE(NVConfigManager.createNVConfig("error_type", "Error type", "ErrorType", false, true, false, false, String.class, FilterType.CLEAR)),
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
	
	public static final NVConfigEntity NVC_SIMPLE_ERROR_MESSAGE = new NVConfigEntityLocal(
            "simple_error_message",
            "Simple Error Message",
            "SimpleErrorMessage",
            true,
            false,
            false,
            false,
            SimpleErrorMessage.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SimpleMessage.NVC_SIMPLE_MESSAGE
    );
	
	/**
	 * The default constructor.
	 */
	public SimpleErrorMessage() 
	{
		super(NVC_SIMPLE_ERROR_MESSAGE);
	}
	
	
	public SimpleErrorMessage(String message)
    {
      this();
      setMessage(message);
    }
	
	public SimpleErrorMessage(String message, int code, String type)
	{
	  this();
	  setMessage(message);
	  setErrorCode(code);
	  setErrorType(type);
	}
	
	/**
	 * This constructor instantiates SimpleErrorMessage based on given NVConfigEntity parameter.
	 * @param nvce
	 */
	protected SimpleErrorMessage(NVConfigEntity nvce) 
	{
		super(nvce);
	}
	
	public int getErrorCode()
	{
		return lookupValue(Param.ERROR_CODE);
	}
	
	public void setErrorCode(int errorCode)
	{
		setValue(Param.ERROR_CODE, errorCode);
	}
	
	public String getErrorCategory()
    {
        return lookupValue(Param.ERROR_CATEGORY);
    }
    
    public void setErrorCategory(String errorCategory)
    {
        setValue(Param.ERROR_CATEGORY, errorCategory);
    }
    public String getErrorType()
    {
        return lookupValue(Param.ERROR_TYPE);
    }
    
    public void setErrorType(String errorType)
    {
        setValue(Param.ERROR_TYPE, errorType);
    }
    
    
    
    
    
   
}