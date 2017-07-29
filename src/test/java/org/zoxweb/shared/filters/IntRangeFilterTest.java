package org.zoxweb.shared.filters;

import org.junit.Test;

public class IntRangeFilterTest {
	@Test
	public void testIntRange()
	{
		IntRangeFilter oneToHundred = new IntRangeFilter(1, true, 100, false);
		
		
		int values [] = 
			{
				1, 200, 99, 100, 0, 50, 35	
			};
		for (int v : values)
			System.out.println(v + " inrange " + oneToHundred.isValid(v));
	}
}
