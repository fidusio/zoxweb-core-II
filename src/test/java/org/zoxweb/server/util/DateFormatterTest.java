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
		System.out.println(DateFilter.DEFAULT_GMT.format(new Date()));
	}
	
	@Test
	public void testParseGMT()
	{
		Date date = new Date();
		String strDate = DateFilter.DEFAULT_GMT.format(date);
		Date newDate = new Date(DateFilter.SINGLETON.validate(strDate));
		Assert.assertEquals(date, newDate);
	}
	
	
	
}
