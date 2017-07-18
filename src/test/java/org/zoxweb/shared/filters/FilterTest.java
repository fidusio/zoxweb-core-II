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
import org.zoxweb.shared.util.SharedStringUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterTest {
    
    @Test
    public void testEmailFilter() {
        System.out.println("EMAIL FILTER");

        String[] validEmails = {"johnsmith@example.com ", "JohnSmith@example.com"};

        System.out.println("Valid Emails:");

        for (String email : validEmails) {
            System.out.println(email);
            assertTrue(FilterType.EMAIL.isValid(email));
        }

        String[] invalidEmails = {"johnsmith@example", "johnsmith", null};

        System.out.println("\nInvalid Emails:");

        for (String email : invalidEmails) {
            System.out.println(email);
            assertFalse(FilterType.EMAIL.isValid(email));
        }
    }

    @Test
    public void testIntegerFilter() {
        System.out.println("INTEGER FILTER");

        String[] validIntegers = {"100", "5", "44005"};

        System.out.println("Valid Integers:");

        for (String value : validIntegers) {
            System.out.println(value);
            assertTrue(FilterType.INTEGER.isValid(value));
        }

        String[] invalidIntegers = {"1.0", "400.50"};

        System.out.println("\nInvalid Integers:");

        for (String value : invalidIntegers) {
            System.out.println(value);
            assertFalse(FilterType.INTEGER.isValid(value));
        }
    }

    @Test
    public void testLongFilter() {
        System.out.println("LONG FILTER");

        String[] validLongs = {"12589461", "-100"};

        System.out.println("Valid Longs:");

        for (String value : validLongs) {
            System.out.println(value);
            assertTrue(FilterType.LONG.isValid(value));
        }

        String[] invalidLongs= {"1.0", "1,200,000", null, "23.587"};

        System.out.println("\nInvalid Longs:");

        for (String value : invalidLongs) {
            System.out.println(value);
            assertFalse(FilterType.LONG.isValid(value));
        }
    }

    @Test
    public void testDoubleFilter() {
        System.out.println("DOUBLE FILTER");

        String[] validDoubles = {"9.99", "4.0"};

        System.out.println("Valid Doubles:");

        for (String value : validDoubles) {
            System.out.println(value);
            assertTrue(FilterType.DOUBLE.isValid(value));
        }

        String[] invalidDoubles = {"44..44"};

        System.out.println("\nInvalid Doubles:");

        for (String value : invalidDoubles) {
            System.out.println(value);
            assertFalse(FilterType.DOUBLE.isValid(value));
        }
    }

    @Test
    public void testPasswordFilter() {
        System.out.println("PASSWORD FILTER");

        String[] validPasswords = {"Password14", "p1ssw2rdS", "Password1", "Password2014",
				"Password14Password14Password14Password14", "Password14Password14Password14Password14Password14", "Password14$"};

        System.out.println("Valid Passwords:");

        for (String password : validPasswords) {
            System.out.println(password);
            assertTrue(FilterType.PASSWORD.isValid(password));
        }

        String[] invalidPasswords = {"p1ssw2rd", "password", "password14", "Password"};

        System.out.println("\nInvalid Passwords:");

        for (String password : invalidPasswords) {
            System.out.println(password);
            assertFalse(FilterType.PASSWORD.isValid(password));
        }
    }

    @Test
    public void testHiddenFilter() {
        System.out.println("HIDDEN FILTER");

        String[] validHiddenTexts = {null, ""};

        System.out.println("Valid Hidden Types:");

        for (String str : validHiddenTexts) {
            System.out.println(str);
            assertTrue(FilterType.HIDDEN.isValid(str));
        }

        String[] invalidHiddenTexts = {"https://www.yahoo.com "};

        System.out.println("Invalid Hidden Types:");

        for (String str : invalidHiddenTexts) {
            System.out.println(str);
            assertFalse(FilterType.HIDDEN.isValid(str));
        }
    }

    @Test
    public void testDomainIDFilter() {
        System.out.println("DOMAIN ACCOUNT ID FILTER");

        String[] validAccountIDs = {"www.yahoo.com", "yahoo.com", "1258058", "999", "20485"};

		System.out.println("Valid Account IDs:");

		for (String accountID : validAccountIDs) {
            System.out.println(accountID);
            assertTrue(FilterType.DOMAIN_ACCOUNT_ID.isValid(accountID));
        }

        String[] invalidAccountIDs = {"https://www.yahoo.com ", "http://.www.yahoo.com/", "www.yahoo.com/",
                "yahoo.com/", "https://10.0.1.1/marwan?param=1&p1=2"};

        System.out.println("Invalid Account IDs:");

        for (String accountID : invalidAccountIDs) {
            System.out.println(accountID);
            assertFalse(FilterType.DOMAIN_ACCOUNT_ID.isValid(accountID));
        }
    }

    @Test
    public void testURLFilter() {

        System.out.println("URL FILTER");

        String[] validURLs = {"https://www.google.com ", "https://10.0.1.1/hello_world?param=1&p1=2"};

        System.out.println("Valid URLs:");

        for (String url : validURLs) {
            System.out.println(url);
            assertTrue(FilterType.URL.isValid(url));
        }

        String[] invalidURLs = {"http://.www.google.com/", "www.google.com/", "google.com/", "google"};

        System.out.println("Invalid URLs:");

        for (String url : invalidURLs) {
            System.out.println(url);
            assertFalse(FilterType.URL.isValid(url));
        }
    }

    @Test
    public void testCanonicalFilenameFilter() {
        System.out.println("CANONICAL FILENAME FILTER");

        String[] validFilenames = {"/", "/Users/johnsmith/Desktop"};

        System.out.println("Valid Canonical File Names:");

        for (String filename : validFilenames) {
            System.out.println("File Name: " + filename + " Filtered: \"" + CanonicalFilenameFilter.SINGLETON.validate(filename) + "\"");
            assertTrue(CanonicalFilenameFilter.SINGLETON.isValid(filename));
        }

        String[] invalidFilenames = {null, "/////", "\\file\\hello//world/123;", "/////ta", "start///////", ""};

        System.out.println("Invalid Canonical File Names:");

        for (String filename : invalidFilenames) {
            System.out.println("File Name: " + filename + " Filtered: \"" + CanonicalFilenameFilter.SINGLETON.validate(filename) + "\"");
            assertFalse(CanonicalFilenameFilter.SINGLETON.isValid(filename));
		}
    }

    @Test
    public void testFilters() {
        String test = "testuser@example.com";

		for (FilterType ft : FilterType.values()) {
			System.out.println(ft.toCanonicalID() + " " + ft.isValid(test));
		}

		String macs[] = {
			"00:11:22:33:44:55",
			"01-23-45-67-89-0A",
			"AA.BB.CC.DD.EE.FF"
		};

		for (String mac: macs) {
			String str = SharedStringUtil.filterString(mac, new String[]{"-", ":", "."});
			System.out.println( str);
			byte[] address = SharedStringUtil.hexToBytes(str);
			System.out.println( SharedStringUtil.bytesToHex(address));
		}
    }

}