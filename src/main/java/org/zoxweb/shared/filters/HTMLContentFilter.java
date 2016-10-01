package org.zoxweb.shared.filters;

import org.zoxweb.shared.util.SharedStringUtil;

/**
 * The HTML content filter class validates HTML.
 * @author mzebib
 *
 */
public class HTMLContentFilter 
{
	
	public static String DEFAULT_HTML_TAG = "<html><head><title></title></head><body></body></html>";
	public static String HTML_PRE_TAG = "<html>";
	public static String HTML_POST_TAG = "</html>";
	public static String HEAD_PRE_TAG = "<head>";
	public static String HEAD_POST_TAG = "</head>";
	public static String TITLE_PRE_TAG = "<title>";
	public static String TITLE_POST_TAG = "</title>";
	public static String BODY_PRE_TAG = "<body>";
	public static String BODY_POST_TAG = "</body>";
	
	/**
	 * Validates given HTML.
	 * @param html
	 * @return
	 */
	public static String validateHTML(String html)
	{
		if (!SharedStringUtil.isEmpty(html))
		{			
			if (!SharedStringUtil.contains(html, HTML_PRE_TAG, true))
			{
				html = HTML_PRE_TAG + html;
			}
			
			if (!SharedStringUtil.contains(html, HTML_POST_TAG, true))
			{
				html = html + HTML_POST_TAG;
			}
			
			if (!SharedStringUtil.contains(html, HEAD_PRE_TAG, true))
			{
				String value = SharedStringUtil.valueAfterLeftToken(html, HTML_PRE_TAG);
				
				if (value != null)
				{
					html = HTML_PRE_TAG + HEAD_PRE_TAG + value;
				}
			}
			
			if (!SharedStringUtil.contains(html, HEAD_POST_TAG, true))
			{
				if (SharedStringUtil.contains(html, TITLE_POST_TAG, true))
				{
					String value1 = SharedStringUtil.valueBeforeLeftToken(html, TITLE_POST_TAG);
					String value2 = SharedStringUtil.valueAfterLeftToken(html, TITLE_POST_TAG);
					
					if (value1 != null && value2 != null)
					{
						html = value1 + TITLE_POST_TAG + HEAD_POST_TAG + value2;
					}
				}
				else if (SharedStringUtil.contains(html, BODY_PRE_TAG, true))
				{
					String value1 = SharedStringUtil.valueBeforeLeftToken(html, BODY_PRE_TAG);
					String value2 = SharedStringUtil.valueAfterLeftToken(html, BODY_PRE_TAG);
					
					if (value1 != null && value2 != null)
					{
						html = value1 + HEAD_POST_TAG + BODY_PRE_TAG + value2;
					}
				}
				else
				{
					String value1 = SharedStringUtil.valueBeforeLeftToken(html, HEAD_PRE_TAG);
					String value2 = SharedStringUtil.valueAfterLeftToken(html, HEAD_PRE_TAG);
					
					if (value1 != null && value2 != null)
					{
						html = value1 + HEAD_PRE_TAG + HEAD_POST_TAG + value2;
					}
				}
			}
			
			if (!SharedStringUtil.contains(html, TITLE_PRE_TAG, true))
			{
				String value1 = SharedStringUtil.valueBeforeLeftToken(html, HEAD_PRE_TAG);
				String value2 = SharedStringUtil.valueAfterLeftToken(html, HEAD_PRE_TAG);
				
				if (value1 != null && value2 != null)
				{
					html = value1 + HEAD_PRE_TAG + TITLE_PRE_TAG + value2;
				}
			}
			
			if (!SharedStringUtil.contains(html, TITLE_POST_TAG, true))
			{
				String value1 = SharedStringUtil.valueBeforeLeftToken(html, HEAD_POST_TAG);
				String value2 = SharedStringUtil.valueAfterLeftToken(html, HEAD_POST_TAG);
				
				if (value1 != null && value2 != null)
				{
					html = value1 + TITLE_POST_TAG + HEAD_POST_TAG + value2;
				}
			}
			
			if (!SharedStringUtil.contains(html, BODY_PRE_TAG, true))
			{
				String value1 = SharedStringUtil.valueBeforeLeftToken(html, HEAD_POST_TAG);
				String value2 = SharedStringUtil.valueAfterLeftToken(html, HEAD_POST_TAG);
				
				if (value1 != null && value2 != null)
				{
					html = value1 + HEAD_POST_TAG + BODY_PRE_TAG + value2;
				}
			}
			
			if (!SharedStringUtil.contains(html, BODY_POST_TAG, true))
			{
				String value1 = SharedStringUtil.valueBeforeLeftToken(html, HTML_POST_TAG);
				
				if (value1 != null)
				{
					html = value1 + BODY_POST_TAG + HTML_POST_TAG;
				}
			}
			
			return html;
		}
		
		return DEFAULT_HTML_TAG;
	}
	
}