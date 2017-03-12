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

import java.io.Serializable;

import org.zoxweb.shared.filters.SetValueFilter;
import org.zoxweb.shared.util.SetDescription;
import org.zoxweb.shared.util.SetDisplayName;
import org.zoxweb.shared.util.SetEditable;
import org.zoxweb.shared.util.SetMandatory;

/**
 * The NVConfig interface declares methods to set and return the meta type
 * and the filter value. This interface extends interfaces which include 
 * methods declared to set the following properties: name, description, 
 * mandatory, editable, and display name. The Serialization interface is 
 * extended in order to enable serialization for NVConfig.
 * @author mzebib
 *
 */
@SuppressWarnings("rawtypes")
public interface NVConfig
	extends	SetName,
			SetDescription,
			SetMandatory,
			SetEditable,
			SetDisplayName,
			SetUnique,
			SetHidden,
			SetValueFilter,
			Serializable
{
	/**
	 * This method returns the class type of NVConfig.
	 * @return class meta type
	 */
	public Class<?> getMetaType();
	
	/**
	 * @return base meta type if the meta type is array
	 */
	public Class<?> getMetaTypeBase();
	
	
	public void setArray(boolean array);
	public boolean isArray();
	
	public boolean isEnum();
	
	public boolean isTypeReferenceID();
	
	public void setTypeReferenceID(boolean type);
	
	
	
	/**
	 * This method sets the class type of NVConfig.
	 * @param type is of generic class type
	 */
	public void setMetaType(Class<?> type);
	
	/**
	 * This method returns the value filter.
	 * @return
	 */
	//public ValueFilter<?,?> getValueFilter();
	
	/**
	 * This method sets the value filter.
	 * @param vf is of type ValueFilter interface which 
	 * takes the following parameters: input value and an 
	 * output filtered value
	 */
	//public void setValueFilter(ValueFilter<?,?> vf);
	
}
