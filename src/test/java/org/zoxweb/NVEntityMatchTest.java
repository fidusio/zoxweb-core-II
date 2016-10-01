/*
 * Copyright (c) 2012-Oct 1, 2015 ZoxWeb.com LLC.
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
package org.zoxweb;

import org.zoxweb.shared.data.UserInfoDAO;
import org.zoxweb.shared.util.SharedUtil;

/**
 * @author mzebib
 *
 */
public class NVEntityMatchTest 
{
	
	public static void main(String[] args)
	{
		UserInfoDAO user1 = new UserInfoDAO();
		user1.setReferenceID("123456789");
		user1.setFirstName("John");
		user1.setLastName("Smith");
		
		UserInfoDAO user2 = new UserInfoDAO();
		user2.setReferenceID("123456789");
		user2.setFirstName("John");
		user2.setLastName("John");
		
		System.out.println("Does user1 equal user2? " + SharedUtil.equals(user1, user2));
		
		UserInfoDAO user3 = new UserInfoDAO();
		user3.setReferenceID("OZ309");
		user3.setFirstName("M");
		user3.setLastName("Z");
		
		System.out.println("Does user1 equal user3? " + SharedUtil.equals(user1, user3));
		System.out.println("Does user2 equal user3? " + SharedUtil.equals(user2, user3));
		
		UserInfoDAO user4 = null;
		
		System.out.println("Does user3 equal user4? " + SharedUtil.equals(user3, user4));
	}

}
