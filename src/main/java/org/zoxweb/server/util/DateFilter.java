package org.zoxweb.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.zoxweb.shared.filters.ValueFilter;

/**
 * The date filter validates date formats.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class DateFilter 
	implements ValueFilter<String, Long>
{
	
	public static class SDFBuilder extends SimpleDateFormat
	{
		public SDFBuilder(String pattern)
		{
			super(pattern);
		}
		
		public SDFBuilder timeZone(TimeZone tz)
		{
			setTimeZone(tz);
			return this;
		}
	}
	
	/**
	 * Sets the default date format.
	 */
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
	
	public static final SimpleDateFormat DEFAULT_GMT = new SDFBuilder("yyyy-MM-dd HH:mm:ss:SSS'Z'").timeZone(TimeZone.getTimeZone("UTC"));
	
	/**
	 * Defines an array of support date formats.
	 */
	private static SimpleDateFormat[] sdf = 
		{
			DEFAULT_GMT,
			new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd"),	
			
			new SimpleDateFormat("MM-yy"),
			new SimpleDateFormat("MM-yyyy"),
		};
	
	/**
	 * Declares that only one instance of this class can be created.
	 */
	public static final DateFilter SINGLETON = new DateFilter();
	
	/**
	 * The default constructor is declared private to prevent
	 * outside instantiation of this class.
	 */
	private DateFilter()
	{
		
	}
	
	/**
	 * Gets the string representation of this class.
	 */
	@Override
	public String toCanonicalID() 
	{
		return null;
	}

	/**
	 * Validates a string input and returns a long value.
	 * @param in
	 */
	@Override
	public Long validate(String in) 
			throws NullPointerException, IllegalArgumentException 
	{
		
		for (SimpleDateFormat format : sdf)
		{
			try 
			{
				return format.parse(in).getTime();
			}
			catch (ParseException e) 
			{
				
			}
		}
	
		try
		{
			return Long.parseLong(in);
		}
		catch (NumberFormatException e)
		{
			
		}
		
		throw new IllegalArgumentException("Invalid format: " + in);
	}

	/**
	 * Checks whether the input is valid or not.
	 * @param in
	 */
	@Override
	public boolean isValid(String in) 
	{
		try
		{
			validate(in);
			
			return true;
		}
		catch (Exception e)
		{
			
		}
		
		return false;
	}

}