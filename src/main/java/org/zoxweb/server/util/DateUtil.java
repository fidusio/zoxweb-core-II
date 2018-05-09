package org.zoxweb.server.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.zoxweb.shared.util.Const.DayOfWeek;
import org.zoxweb.shared.util.Const.TimeInMillis;

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
	
	
	public static long timeInMillisRelativeToDay()
	{
		return timeInMillisRelativeToDay(new Date());
	}
	
	public static long timeInMillisRelativeToDay(long date)
	{
		return timeInMillisRelativeToDay(new Date(date));
	}
	
	public static long timeInMillisRelativeToDay(Date date)
	{
		Calendar calendar = getCalendar(date);
		long timeInMillis = calendar.get(Calendar.HOUR_OF_DAY)*TimeInMillis.HOUR.MILLIS + 
						 	calendar.get(Calendar.MINUTE)*TimeInMillis.MINUTE.MILLIS +
						 	calendar.get(Calendar.SECOND)*TimeInMillis.SECOND.MILLIS + 
						 	calendar.get(Calendar.MILLISECOND);
		
		return timeInMillis;
	}
	
	public static DayOfWeek dayOfWeek(Date date)
	{
		Calendar calendar = getCalendar(date);
		return DayOfWeek.lookup(calendar.get(Calendar.DAY_OF_WEEK) - 1);
	}
	
	public static DayOfWeek dayOfWeek(long millis)
	{
		return dayOfWeek(new Date(millis));
	}
	
	
}
