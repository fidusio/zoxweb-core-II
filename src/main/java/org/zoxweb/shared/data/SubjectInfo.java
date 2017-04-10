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

import org.zoxweb.shared.util.SubjectID;

/**
 * The SubjectInfo interface.
 * @author mzebib
 *
 */
public interface SubjectInfo
	extends SubjectID<String>
{
	/**
	 * Gets the first name.
	 * @return fist name 
	 */
	public String getFirstName();
	
	/**
	 * Sets the first name.
	 * @param first
	 */
	public void setFirstName(String first);

	/**
	 * Gets the middle name.
	 * @return middle name
	 */
	public String getMiddleName();
	
	/**
	 * Sets the middle name.
	 * @param middle
	 */
	public void setMiddleName(String middle);
	
	/**
	 * Gets the last name.
	 * @return last name
	 */
	public String getLastName();
	
	/**
	 * Sets the last name.
	 * @param last
	 */
	public void setLastName(String last);
	
	/**
	 * Gets the primary email.
	 * @return primary email
	 */
	public String getPrimaryEmail();
	
	/**
	 * Sets the primary email.
	 * @param email
	 */
	public void setPrimaryEmail(String email);
	
	/**
	 * Gets the title.
	 * @return title 
	 */
	public String getTitle();
	
	/**
	 * Sets the title.
	 * @param title
	 */
	public void setTitle(String title);
		
}