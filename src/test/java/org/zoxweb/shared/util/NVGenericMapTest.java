package org.zoxweb.shared.util;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class NVGenericMapTest
{
	public static void printValue(NVGenericMap nvgm)
	{
		System.out.println(nvgm.getName());
		
		GetNameValue<?>[] avs = nvgm.values();
		for(GetNameValue<?> av: avs)
		{
			System.out.println(av.getClass().getName() + ":" + av.getName() + "=" + av.getValue());
			
		}
		
	}
	
	
	public static void main(String ...args)
	{
		try
		{
			NVGenericMap nvgm = new NVGenericMap();
			nvgm.setName("name");
			nvgm.add(new NVLong("longValue", 67));
			nvgm.add(new NVDouble("doubleValue", 567.45));
			nvgm.add(new NVBoolean("booleanValue", true));
			nvgm.add(new NVPair("aname", "value"));
			
			
			AddressDAO address = new AddressDAO();
			
			address.setStreet("123 Main St.");
			address.setCity("Los Angeles");
			address.setStateOrProvince("CA");
			address.setCountry("USA");
			address.setZIPOrPostalCode("90025");
			nvgm.add(new NVEntityReference(address));
			nvgm.add(new NVBlob("byteArrray", new byte[] {0,1,2,3,5,6,7,8}));
			
			printValue(nvgm);
			
			String json = GSONUtil.genericMapToJSON(nvgm, true, false, false, Base64Type.URL);
			System.out.println(json);
			
			nvgm = GSONUtil.genericMapFromJSON(json, null, Base64Type.URL);
			printValue(nvgm);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
