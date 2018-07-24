package org.zoxweb.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


import org.zoxweb.shared.util.Const.DayOfWeek;
import org.zoxweb.shared.util.Const.TimeInMillis;

public class DateUtil 
{
	
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
	public static final SimpleDateFormat DEFAULT_JAVA_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	public static final SimpleDateFormat DEFAULT_GMT_MILLIS = createSDF("yyyy-MM-dd'T'HH:mm:ss.SSSX", "UTC");
	public static final SimpleDateFormat DEFAULT_GMT = createSDF("yyyy-MM-dd'T'HH:mm:ssX", "UTC");
	
	private DateUtil()
	{
	}
	
	
	
	public static SimpleDateFormat createSDF(String pattern, String timezone)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf;
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
	
	public static long timeInMillisRelativeToWeek(Date date)
	{
		return timeInMillisRelativeToWeek(getCalendar(date));
	}
	
	public static long timeInMillisRelativeToWeek(long date)
	{
		return timeInMillisRelativeToWeek(getCalendar(date));
	}
	
	public static long timeInMillisRelativeToWeek(Calendar calendar)
	{
		long timeInMillis =  DayOfWeek.lookup(calendar.get(Calendar.DAY_OF_WEEK) - 1).getValue()*TimeInMillis.DAY.MILLIS+
				 			calendar.get(Calendar.HOUR_OF_DAY)*TimeInMillis.HOUR.MILLIS + 
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
