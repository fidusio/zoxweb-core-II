package org.zoxweb.shared.filters;

import org.zoxweb.shared.filters.ValueFilter;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.api.APINotificationMessage;

/**
 * The message content filter class validates message content.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class MessageContentFilter 
	implements ValueFilter<APINotificationMessage, String>
{
	/**
	 * Initializes delimiter constant.
	 */
	public static final String DEFAULT_DELIMITER = "$$";
	
	/**
	 * Declares that only one instance of this class can be created.
	 */
	public static final MessageContentFilter SINGLETON = new MessageContentFilter();
	
	/**
	 * The default constructor is declared private to prevent
	 * outside instantiation of this class.
	 */
	private MessageContentFilter()
	{
		
	}
	
	/**
	 * Returns a string representation of the class.
	 */
	@Override
	public String toCanonicalID() 
	{
		return null;
	}

	/**
	 * Validates the notification message.
	 * @param in
	 * @return validated 
	 */
	@Override
	public String validate(APINotificationMessage in)
			throws NullPointerException, IllegalArgumentException 
	{
		String bodyContent = in.getBodyTemplate();
		
		
		if (!SharedStringUtil.isEmpty(bodyContent))
		{
			for (NVPair nvp : in.getBodyTags().values())
			{
				String preTag = in.getPreTag() != null ? in.getPreTag() : DEFAULT_DELIMITER;
				String postTag = in.getPostTag() != null ? in.getPostTag() : DEFAULT_DELIMITER;
				if(nvp.getValue() != null)
					bodyContent = bodyContent.replace(preTag + nvp.getName() + postTag, nvp.getValue());
			}
		}
		
		return bodyContent;
	}

	/**
	 * Checks if the notification message is valid.
	 * @param in
	 * @return true if valid
	 */
	@Override
	public boolean isValid(APINotificationMessage in)
	{
		return in != null;
	}

}