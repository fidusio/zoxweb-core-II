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
package org.zoxweb.shared.filters;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BigDecimalFilterTest {

	@Test
	public void testValidBigDecimal() {
		BigDecimal value = BigDecimalFilter.SINGLETON.validate("100.00");
		assertEquals("100.00", value.toString());

		value = BigDecimalFilter.SINGLETON.validate("1000");
		assertEquals("1000", value.toString());

		value = BigDecimalFilter.SINGLETON.validate(".99");
		assertEquals("0.99", value.toString());
	}

	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidBigDecimal() {
		BigDecimalFilter.SINGLETON.validate("John");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyBigDecimal() {
		BigDecimalFilter.SINGLETON.validate("");
	}
	
	@Test (expected = NullPointerException.class)
	public void testNullBigDecimal() {
		BigDecimalFilter.SINGLETON.validate(null);
	}

}