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
package org.zoxweb.client.data;

import com.google.gwt.regexp.shared.RegExp;

public class GWTHTMLFilter
{
	
	public final static String tagStart =
		"^\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
	public final static String tagEnd =
	    "\\</\\w+\\>$";
	public final static String tagSelfClosing =
	    "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
	public final static String htmlEntity =
	    "&[a-zA-Z][a-zA-Z0-9]+;";
	    
    /**
     * The HTML format pattern.
     */
    public final static RegExp htmlPattern = 
    		RegExp.compile
		(
			"(" + tagStart + ".*" + tagEnd + ")|(" + tagSelfClosing + ")|(" + htmlEntity +")"
		);


    /**
     * Checks if given input String is HTML.
     * @param str of type html to be checked
     * @return true is html
     */
    public static boolean isHTML(String str)
    {
        boolean ret = false;
        
        if (str != null)
        {
            ret = htmlPattern.test(str);
        }
        
        return ret;
    }

}