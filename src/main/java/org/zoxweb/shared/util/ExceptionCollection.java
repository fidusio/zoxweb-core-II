/*
 * Copyright (c) 2012-Sep 16, 2014 ZoxWeb.com LLC.
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

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends RuntimeException and is used to handle store and manage
 * multiple exceptions.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class ExceptionCollection 
	extends RuntimeException
{
	private List<Exception> list = new ArrayList<Exception>();
	private String reason;
	
	/**
	 * This is the default constructor.
	 */
	public ExceptionCollection()
	{
	}
	
	/**
	 * This constructor instantiates ExceptionCollection with
	 * message set based on reason input.
	 * @param reason
	 */
	public ExceptionCollection(String reason)
	{
		super();
		setMessage(reason);
	}
	
	/**
	 * This method returns the exception list.
	 * @return
	 */
	public List<Exception> getExceptionList()
	{
		return list;
	}
	
	/**
	 * This method sets the exception list.
	 * @param list
	 */
	public void setExceptionList(List<Exception> list)
	{
		this.list = list;
	}
	
	/**
	 * This method returns the message;
	 * @return
	 */
	public String getMessage()
	{
		return reason;
	}
	
	/**
	 * This method sets the message.
	 * @param reason
	 */
	public void setMessage(String reason)
	{
		this.reason = reason;
	}

	/**
	 * This method adds an existing exception collection to the current 
	 * exception collection.
	 * @param ec
	 */
	public void add(ExceptionCollection ec)
	{
		list.addAll(ec.getExceptionList());
	}
}
