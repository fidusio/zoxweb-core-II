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
     * @param str
     * @return
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
