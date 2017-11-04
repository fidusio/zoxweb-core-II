package org.zoxweb.server.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil 
{
	private DateUtil()
	{
	}
	
	
	/**
	 * Return date in normal format jan=1... dec=12
	 * @param date
	 * @return
	 */
	public static int getNormalizedMonth(long date)
	{
		return getNormalizedMonth(new Date(date));
	}
	

	/**
	 * Return date in normal format jan=1... dec=12
	 * @param date
	 * @return
	 */
	public static int getNormalizedMonth(Date date)
	{
		return getCalendar(date).get(Calendar.MONTH) + 1;
	}
	
	
	public static int getNormalizedYear(long date)
	{
		return getNormalizedYear(new Date(date));
	}
	
	public static int getNormalizedYear(Date date)
	{
		return getCalendar(date).get(Calendar.YEAR);
	}
	
	/**
	 * Return gregorian calendar
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(long date)
	{
		return getCalendar(new Date(date));
	}
	
	/**
	 * Return gregorian calendar
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date)
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	
}
