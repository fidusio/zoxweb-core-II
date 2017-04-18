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

import org.junit.Assert;
import org.junit.Test;

import org.zoxweb.shared.filters.AddressFilterType;

public class AddressFilterTypeTest {

	@Test
	public void testValidUSZipCode() {
		String zipCode = "90025";
		boolean valid = AddressFilterType.US_ZIP_CODE.isValid(zipCode);
		Assert.assertTrue(valid);

		zipCode = "48202";
		valid = AddressFilterType.US_ZIP_CODE.isValid(zipCode);
		Assert.assertTrue(valid);
	}
	
	@Test
	public void testInvalidUSZipCode() {
		String zipCode = "0";
		boolean valid = AddressFilterType.US_ZIP_CODE.isValid(zipCode);
		Assert.assertFalse(valid);

		zipCode = "48202555";
		valid = AddressFilterType.US_ZIP_CODE.isValid(zipCode);
		Assert.assertFalse(valid);
	}
	
	@Test
	public void testValidCanadaPostalCode() {
		String postalCode = "N9G2E1";
		boolean valid = AddressFilterType.CANADA_POSTAL_CODE.isValid(postalCode);
		Assert.assertTrue(valid);
	}
	
	@Test
	public void testInvalidCanadaPostalCode() {
		String postalCode = "9N23G1";
		boolean valid = AddressFilterType.CANADA_POSTAL_CODE.isValid(postalCode);
		Assert.assertFalse(valid);

		postalCode = "90066";
		valid = AddressFilterType.CANADA_POSTAL_CODE.isValid(postalCode);
		Assert.assertFalse(valid);
	}
	
}