package org.zoxweb.shared.filters;

import org.junit.Test;

public class RangeFilterTest {
	
	IntRangeFilter intOneToHundred = new IntRangeFilter(1, true, 100, false);
	@Test
	public void testIntFilterIsValid()
	{
		
		int values [] = 
			{
				1, 200, 99, 100, 0, 50, 35	
			};
		for (int v : values)
			System.out.println(v + " inrange " + intOneToHundred.isValid(v));
	}
}
