package org.zoxweb.shared.filters;

import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.filters.CanonicalFilenameFilter;
import org.zoxweb.shared.util.SharedStringUtil;

public class FilterTest {
	
	public static void main(String[] args) {

		String test = "mnael@zoxweb.com";

		for (FilterType ft : FilterType.values()) {
			System.out.println( ft.toCanonicalID() + " " + ft.isValid( test));
		}

		String macs[] = {
			"00:11:22:33:44:55",
			"01-23-45-67-89-0A",
			"AA.BB.CC.DD.EE.FF"
		};
		
		for (String mac: macs) {
			String str = SharedStringUtil.filterString(mac, new String[]{"-", ":", "."});
			System.out.println( str);
			byte[] addr = SharedStringUtil.hexToBytes(str);
			System.out.println( SharedStringUtil.bytesToHex(addr));
		}

		String[] emails = {"johnsmith@zoxweb.com ", "JohnSmith@zoxweb.com", "johnsmith@zoxeb", "johnsmith", null,};
		
		System.out.println("Testing Email Type: ");

		for (int i=0; i < emails.length; i++) {
			
			try {
			    System.out.print(emails[i] + ":");
				System.out.println("\""+FilterType.EMAIL.validate(emails[i]) + "\" Valid");
			} catch(Exception e) {
				System.out.println(":Invalid ");
			}
		}

		String[] longs = {"1,200,000", null, "12589461", "0", "-1", "23.587"};
		
		System.out.println("Testing Long Type: ");

		for (int i=0; i < longs.length; i++) {
			try {
				System.out.print(longs[i] + ":");
				System.out.println("\""+FilterType.LONG.validate(longs[i]) + "\" Valid");
			} catch(Exception e) {
                System.out.println(":Invalid ");
            }
		}

		String[] urls = {"https://www.yahoo.com ", " 	Mzebib@zoxweb.com", "mzebib@zoxeb", "mzebib", "https://10.0.1.1/marwaa?param=1&p1=2","http://.www.foo.bar./"};
		
		System.out.println("Testing URL Type: ");

		for (int i=0; i < urls.length; i++) {
			try {
				System.out.println("\""+FilterType.URL.validate(urls[i]) + "\" Valid");
			} catch (Exception e) {
				System.out.println(urls[i] + " Invalid");
			}
		}
		
		String[] passwords = {"Password14", "p1ssw2rd", "p1ssw2rdS", "password", "password14", "Pass14", "Password1", "Password2014", 
				"Password14Password14Password14Password14", "Password14Password14Password14Password14Password14", "Password14$"};
		
		System.out.println("Testing Passwords: ");
		for (int i=0; i < passwords.length; i++) {
			
			try {
				System.out.println("\""+FilterType.PASSWORD.validate(passwords[i]) + "\" Valid");
			} catch (Exception e) {
				System.out.println(passwords[i] + " Invalid");
			}
		}
		
		String[] accountIDs = {"https://www.yahoo.com ", "www.yahoo.com", "yahoo.com", "1258058", "http://.www.yahoo.com/", "www.yahoo.com/", "yahoo.com/", "999", "20485", "https://10.0.1.1/marwan?param=1&p1=2"};
		
		System.out.println("Testing Account ID Type: ");
		for (int i = 0; i < accountIDs.length; i++) {
			try {
				System.out.println("Domain: \"" + FilterType.DOMAIN_ACCOUNT_ID.validate(accountIDs[i]) + "\" Valid");
			} catch (Exception e) {
				System.out.println("Domain: " + accountIDs[i] + " Invalid");
			}
		}

		String[] hiddenTexts = {null, "https://www.yahoo.com ", ""};
		
		System.out.println("Testing Hidden Type: ");
		
		for (int i = 0; i < hiddenTexts.length; i++) {
            try {
				System.out.println("\"" + FilterType.HIDDEN.validate(hiddenTexts[i]) + "\" Valid");
				System.out.println("Valid: " + FilterType.HIDDEN.isValid(hiddenTexts[i]));
			} catch (Exception e) {
				System.out.println(hiddenTexts[i] + " Invalid");
				System.out.println("Valid: " + FilterType.HIDDEN.isValid(hiddenTexts[i]));
			}
		}

		String[] integers = {"100", "5", "44005", "400.50"};
		
		System.out.println("Testing Integer Type: ");
		
		for (int i = 0; i < hiddenTexts.length; i++) {
			
			try {
				System.out.println("Valid: " + FilterType.INTEGER.validate(integers[i]));
			} catch (Exception e) {
				System.out.println(integers[i] + " Invalid");
			}
		}

		String[] doubles = {"9.99", "4.0", "44..44"};
		
		System.out.println("Testing Double Type: ");
		
		for (int i = 0; i < hiddenTexts.length; i++) {
			
			try {
				System.out.println("Valid: " + FilterType.DOUBLE.validate(doubles[i]));
			} catch (Exception e) {
				System.out.println(doubles[i] + " Invalid");
			}
		}
		
		String filenames[] = {
				null,
				"/////",
				"\\file\\toto//titi/ta;tzi:darta",
				"/////ta",
				"/",
				"start///////",
				"",
			};

		for (String filename : filenames) {
			System.out.println(filename + " filtered \"" + CanonicalFilenameFilter.SINGLETON.validate(filename)+"\"");
		}

	}
}