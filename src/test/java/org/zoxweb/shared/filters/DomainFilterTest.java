/*
 * Copyright (c) 2012-Nov 17, 2015 ZoxWeb.com LLC.
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

public class DomainFilterTest {

    public static void main(String[] args) {
    	String[] values = 
	    			{
		    			"https://www.zoxweb.com/welcome",
	    				"http://www.zoxweb.com/main?&t=20&f=52",
		    			"http://www.zoxweb.com.fr",
		    			"http://www.admin.zoxweb.com",
		    			"www.admin.zoxweb.com",
		    			"admin.zoxweb.com",
		    			"www.zoxweb.com",
		    			"zoxweb.com",
		    			"zoxweb",
		    			"website.fr",
		    			"www.jeb65465451515415845848548548441040000000000001011111111111111111111111111111111111111111.com"
	    			};
    	
    	for (String val : values) {
    		try {
    			System.out.println("INPUT: " + val + "\nDOMAIN NAME: " + FilterType.DOMAIN.validate(val));
    		} catch (Exception e) {
    			System.out.println("INVALID INPUT: " + val);
    		}
    		
    		System.out.println(" ");
    	}
    }
}