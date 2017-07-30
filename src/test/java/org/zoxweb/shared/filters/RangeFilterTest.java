package org.zoxweb.shared.filters;

import org.junit.Test;

public class RangeFilterTest {
	
	IntRangeFilter intOneToHundred = new IntRangeFilter(1, true, 100, false);
	FloatRangeFilter floatOneToHundred = new FloatRangeFilter(1, false, 100, false);
	
	DoubleRangeFilter doubleOneToHundred = new DoubleRangeFilter(1, true, 100, true);
	@Test
	public void testIntFilterIsValid()
	{
		
		int values [] = 
			{
				1, 200, 99, 100, 0, 50, 35	
			};
		for (int v : values)
			System.out.println(intOneToHundred  + ": " + v + " inrange " + intOneToHundred.isValid(v));
	}
	
	
	@Test
	public void testFloatFilterIsValid()
	{
		
		float values [] = 
			{
				1, 200, (float)99.99, 100, 0, 50, 35	
			};
		for (float v : values)
			System.out.println(floatOneToHundred  + ": " + v + " inrange " + floatOneToHundred.isValid(v));
	}
	
	
	@Test
	public void testDoubleFilterIsValid()
	{
		
		double values [] = 
			{
				1, 200, (double)99.99, 100, 0, 50, 35	
			};
		for (double v : values)
			System.out.println(doubleOneToHundred  + ": "+ v + " inrange " + doubleOneToHundred.isValid(v));
	}
}
