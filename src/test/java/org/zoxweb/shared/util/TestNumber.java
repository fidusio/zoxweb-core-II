package org.zoxweb.shared.util;



//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class TestNumber {

	static String longMax = "" + Long.MAX_VALUE;
	static String longMin = "" + Long.MAX_VALUE;
	static String intMax = "" + Integer.MAX_VALUE;
	static String intMin = "" + Integer.MIN_VALUE;
	static String maxDouble = "" + Double.MAX_VALUE;
	static String maxIntPlusOne = "2147483648";
	static String d = "0.044198288480142844";
	@Test
	public void testLong() 
	{
		assert(SharedUtil.parseNumber(maxIntPlusOne) instanceof Long);
		assert(SharedUtil.parseNumber(longMax) instanceof Long);
		assert(SharedUtil.parseNumber(longMin) instanceof Long);
	}
	
	
	@Test
	public void testInt() 
	{
		
		assert(SharedUtil.parseNumber(intMax) instanceof Integer);
		assert(SharedUtil.parseNumber(intMin) instanceof Integer);
	}
	
	
	@Test
	public void testFloat()
	{
		assert(SharedUtil.parseNumber("23343.4534") instanceof Float);
	}
	
	@Test
	public void testDouble()
	{
		assert(SharedUtil.parseNumber(maxDouble) instanceof Double);
	}

	@Test
	public void longBase64Conversion()
	{
		byte[] longArray = BytesValue.LONG.toBytes(Long.MAX_VALUE);
		String result = SharedBase64.encodeAsString(Base64Type.URL, longArray);
		System.out.println(result + ", " + result.length());
	}

	@Test
	public void testDoubleConversion()
	{
		System.out.println("double:" + Double.parseDouble(d));
		System.out.println("float :" + Float.parseFloat(d));
	}

}
