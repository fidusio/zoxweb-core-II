package org.zoxweb.server.util;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zoxweb.server.filters.TimestampFilter;
import org.zoxweb.shared.util.Const.TimeInMillis;

public class DateFormatterTest {

	@Before
	public void init()
	{
		
	}
	
	@Test
	public void testFormat()
	{
		System.out.println(DateUtil.DEFAULT_DATE_FORMAT.format(new Date()));
		System.out.println(DateUtil.DEFAULT_GMT_MILLIS.format(new Date()));
	}
	
	@Test
	public void testParseGMT()
	{
		Date date = new Date();
		String strDate = DateUtil.DEFAULT_GMT_MILLIS.format(date);
		Date newDate = new Date(TimestampFilter.SINGLETON.validate(strDate));
		Assert.assertEquals(date, newDate);
	}
	
	@Test
	public void testParsePatial()
	{
		
		System.out.println(TimestampFilter.SINGLETON.validate("2017-10-13T22:50:18.437Z"));
		System.out.println(TimestampFilter.SINGLETON.validate("2017-10-13T22:50:18Z"));
		
		System.out.println(TimestampFilter.SINGLETON.validate("2017-10-14T14:33:04.398-07:00"));
		System.out.println(TimestampFilter.SINGLETON.validate("2017-10-14T14:33:04-07:00"));
		
		System.out.println(new Date(TimestampFilter.SINGLETON.validate("2017-10-14T14:33:04-07:00")));
		
	}
	
	@Test
	public void defaultFormat()
	{
		SimpleDateFormat format = new SimpleDateFormat();
		System.out.println(format.toLocalizedPattern());
		System.out.println("Pattern: " + format.toPattern());
		Date date = new Date();
		
		System.out.println(date);
		System.out.println(DateUtil.DEFAULT_JAVA_FORMAT.format(date));
		System.out.println(new Date(TimestampFilter.SINGLETON.validate("" + new Date())));
	}
	
	
	@Test
	public void calendarTest()
	{
		
		
		
		String tokens[] = 
			{
				"01:00:00",
				"01:00:00.500.500",
				"01:00:00.500",
				"00:00:10",
				"00:10",
				"48:35:59.234",
				"48:60:59.234",
				"48:35:60.22323",
				"3h",
				"03:00:00"
			};
		
		for(String time : tokens)
		{
			try
			{
		
				long millis = TimeInMillis.toMillis(time);
				System.out.println(time + "," +  millis+", " + TimeInMillis.toString(millis));
			}
			catch(Exception e)
			{
				System.out.println(time + ", failed");
			}
		}
		
		GregorianCalendar calendar = new GregorianCalendar();
		System.out.println("Millis:" + calendar.getTimeInMillis() + "," + System.currentTimeMillis());
		System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
		System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
		System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
		System.out.println(DateUtil.timeInMillisRelativeToDay(calendar.getTimeInMillis()));
		System.out.println(DateUtil.dayOfWeek(calendar.getTimeInMillis()));
		System.out.println("Time relative to week:" + TimeInMillis.toString(DateUtil.timeInMillisRelativeToWeek(calendar)));
		
		
		
	}
	
}
