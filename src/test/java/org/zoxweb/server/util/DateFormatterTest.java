package org.zoxweb.server.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateFormatterTest {

	@Before
	public void init()
	{
		
	}
	
	@Test
	public void testFormat()
	{
		System.out.println(DateFilter.DEFAULT_DATE_FORMAT.format(new Date()));
		System.out.println(DateFilter.DEFAULT_GMT_MILLIS.SDF.format(new Date()));
	}
	
	@Test
	public void testParseGMT()
	{
		Date date = new Date();
		String strDate = DateFilter.DEFAULT_GMT_MILLIS.SDF.format(date);
		Date newDate = new Date(DateFilter.SINGLETON.validate(strDate));
		Assert.assertEquals(date, newDate);
	}
	
	@Test
	public void testParsePatial()
	{
		
		System.out.println(DateFilter.SINGLETON.validate("2017-10-13T22:50:18.437Z"));
		System.out.println(DateFilter.SINGLETON.validate("2017-10-13T22:50:18Z"));
		
		System.out.println(DateFilter.SINGLETON.validate("2017-10-14T14:33:04.398-07:00"));
		System.out.println(DateFilter.SINGLETON.validate("2017-10-14T14:33:04-07:00"));
		
		System.out.println(new Date(DateFilter.SINGLETON.validate("2017-10-14T14:33:04-07:00")));
		
	}
	
	
}
