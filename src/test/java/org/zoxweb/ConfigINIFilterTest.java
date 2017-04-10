package org.zoxweb;

import org.zoxweb.shared.filters.ConfigINIFilter;

public class ConfigINIFilterTest {

	public static void main (String[] args) {
		String test1 = 
				"#hello\r\n this is a test\n how are you #dumdum\r#you are a dumdum\n#   tizi\n       #\n// java comment\njava//comment\n//###\n hash and java//##";
		
		System.out.println( ConfigINIFilter.SINGLETON.validate( test1));
	}
	
}
