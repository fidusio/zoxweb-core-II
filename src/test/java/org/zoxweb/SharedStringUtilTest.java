/*
 * Copyright (c) 2012-Sep 10, 2014 ZoxWeb.com LLC.
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

import org.junit.Test;

import org.zoxweb.shared.util.SharedStringUtil;

public class SharedStringUtilTest {

	@Test
	public void testA1() 
	{
		String str = "Hello, my name is John Smith.";
		System.out.println(SharedStringUtil.truncate(str, str.length()));
	}
	
	@Test
	public void testA2() {
        String str = "Hello, my name is John Smith.";
		System.out.println(SharedStringUtil.truncate(str, 5));
	
	}
	
	@Test
	public void testA3() {
		System.out.println(SharedStringUtil.truncate(null, 0));
	}

}