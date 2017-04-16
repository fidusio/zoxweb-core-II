package org.zoxweb.server.api.provider.notification.smtp;

import org.zoxweb.shared.util.GetNameValue;

import org.zoxweb.shared.api.APIException;
import org.zoxweb.shared.api.APIExceptionHandler;
import org.zoxweb.shared.api.APIException.Category;
import org.zoxweb.shared.api.APIException.Code;

/**
 *
 */
public class SMTPExceptionHandler
	implements APIExceptionHandler
{

	public enum SMTPError
		implements GetNameValue<Integer>
	{
		CONNECTION_FAILED("connection_failed", 101, Category.CONNECTION, Code.CONNECTION_FAILED)
		
		;
		
		private final String name;;
		private final Integer value;
		private final Category category;
		private final Code code;
		
		SMTPError(String name, Integer value, Category category, Code code)
		{
			this.name = name;
			this.value = value;
			this.category = category;
			this.code = code;
		}

		@Override
		public String getName() 
		{
			return name;
		}

		@Override
		public Integer getValue() 
		{
			return value;
		}
		
		public Category getCategory()
		{
			return category;
		}
	
		public Code getCode()
		{
			return code;
		}
	}
	
	
	/**
	 * This variable declares that only one instance of this class can be created.
	 */
	public static final SMTPExceptionHandler SINGLETON = new SMTPExceptionHandler();
	
	/**
	 * The default constructor is declared private to prevent
	 * outside instantiation of this class.
	 */
	private SMTPExceptionHandler()
	{

	}

	/**
	 *
	 * @param e
	 * @throws APIException
	 */
	@Override
	public void throwException(Exception e)
        throws APIException
	{
		APIException apiException = mapException(e);	
		
		if (apiException != null)
		{
			throw apiException;
		}
	}

	/**
	 *
	 * @param e
	 * @return
	 */
	@Override
	public APIException mapException(Exception e) 
	{	
		return null;
	}

}
