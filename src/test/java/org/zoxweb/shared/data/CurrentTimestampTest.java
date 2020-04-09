package org.zoxweb.shared.data;





import java.io.IOException;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.util.Const.TimeUnitType;

public class CurrentTimestampTest 
{

	@Test
	public void timeInMillis() throws IOException
	{
		CurrentTimestamp ct = new CurrentTimestamp(System.currentTimeMillis(), TimeUnitType.MILLIS);
		String json = GSONUtil.toJSON(ct, true, false, false);
		System.out.println(json);
		CurrentTimestamp newCT = GSONUtil.fromJSON(json, CurrentTimestamp.class);
		String newJson= GSONUtil.toJSON(newCT, true, false, false);
		System.out.println(newJson);
		Assertions.assertEquals(json, newJson);
		
		
		ct = new CurrentTimestamp(System.currentTimeMillis(), TimeUnitType.MILLIS, "localhost");
		json = GSONUtil.toJSON(ct, true, false, true);
		System.out.println(json);
		newCT = GSONUtil.fromJSON(json, CurrentTimestamp.class);
		newJson= GSONUtil.toJSON(newCT, true, false, true);
		System.out.println(newJson);
		Assertions.assertEquals(json, newJson);
	}
	
	
	@Test
	public void timeInNanos() throws IOException
	{
		CurrentTimestamp ct = new CurrentTimestamp(System.nanoTime(), TimeUnitType.NANOS);
		String json = GSONUtil.toJSON(ct, true, false, false);
		System.out.println(json);
		CurrentTimestamp newCT = GSONUtil.fromJSON(json, CurrentTimestamp.class);
		String newJson= GSONUtil.toJSON(newCT, true, false, false);
		System.out.println(newJson);
		Assertions.assertEquals(json, newJson);
		
		
		ct = new CurrentTimestamp(System.nanoTime(), TimeUnitType.NANOS, "localhost");
		json = GSONUtil.toJSON(ct, true, false, true);
		System.out.println(json);
		newCT = GSONUtil.fromJSON(json, CurrentTimestamp.class);
		newJson= GSONUtil.toJSON(newCT, true, false, true);
		System.out.println(newJson);
		Assertions.assertEquals(json, newJson);
	}

}
