package org.zoxweb.shared.util;

import org.zoxweb.shared.data.AddressDAO;

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
			AddressDAO address = new AddressDAO();
			
			address.setStreet("123 Main St.");
			address.setCity("Los Angeles");
			address.setStateOrProvince("CA");
			address.setCountry("USA");
			address.setZIPOrPostalCode("90025");
			nvgm.add(new NVEntityReference(address));
			
			printValue(nvgm);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
