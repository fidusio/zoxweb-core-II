/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
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

import org.junit.Assert;
import org.junit.Test;

public class SharedStringUtilTest {

	@Test
	public void testTruncate() {
		String str = "Hello, my name is John Smith.";
		String result = SharedStringUtil.truncate(str, str.length());
		Assert.assertNotNull(result);
		Assert.assertEquals("Hello, my name is John Smith.", result);

		str = "Hello, my name is John Smith.";
		result = SharedStringUtil.truncate(str, 5);
		Assert.assertNotNull(result);
		Assert.assertEquals("Hello", result);

		result = SharedStringUtil.truncate(null, 0);
		Assert.assertNull(result);
	}
	
	@Test
	public void testSplit()
	{
		System.out.println("After right:" + SharedStringUtil.valueAfterRightToken("john.smith.dickson", "."));
		System.out.println("Before right:" + SharedStringUtil.valueBeforeRightToken("john.smith.dickson", "."));
		System.out.println("Before left:" + SharedStringUtil.valueBeforeLeftToken("john.smith.dickson", "."));
		System.out.println("After left:" + SharedStringUtil.valueAfterLeftToken("john.smith.dickson", "."));
	}
	
	@Test
	public void testByteToHex()
	{
		byte [] buffer = {0,1,2,3,4,5,6,7,0,9,10,100,-1};
		String str = SharedStringUtil.bytesToHex(buffer, "::");
		System.out.println(str + " length:" + str.length());
		str = SharedStringUtil.bytesToHex(buffer);
		System.out.println(str + " length:" + str.length());
	}

}