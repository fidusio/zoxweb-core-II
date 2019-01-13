package org.zoxweb.shared.util;

import java.util.ArrayList;
import java.util.Arrays;

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
			System.out.println(av.getClass().getName() + ":" + av);
			
		}
		
	}
	
	static class TestClass
	{
	  String name;
	  NVGenericMap nvgm;
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
			address.setName("tizi");
			address.setStreet("123 Main St.");
			address.setCity("Los Angeles");
			address.setStateOrProvince("CA");
			address.setCountry("USA");
			address.setZIPOrPostalCode("90025");
			nvgm.add(address);
			nvgm.add(new NVBlob("byteArray", new byte[] {0,1,2,3,5,6,7,8}));
			NVPairList nvp = new NVPairList("nvp", new ArrayList<NVPair>());
			nvp.add(new NVPair("nameNVP", "valueNVP"));
			nvgm.add(nvp);
			
	
			NVLongList nvll = new NVLongList("nvll", new ArrayList<Long>());
			nvll.getValue().add((long) 1);
			nvll.getValue().add((long) 2);
			nvgm.add(nvll);
			printValue(nvgm);
			String json = GSONUtil.toJSONGenericMap(nvgm, true, false, true);
			System.out.println(json);
			
			nvgm = GSONUtil.fromJSONGenericMap(json, null, Base64Type.URL);
			printValue(nvgm);
			byte array[] = nvgm.getValue("byteArray");
			System.out.println("byteArray" + Arrays.toString(array));
			
			
			System.out.println(Float.parseFloat(""+Double.MAX_VALUE)); 
			System.out.println(Integer.parseInt(""+Integer.MAX_VALUE));
			
			nvgm = new NVGenericMap();
			nvgm.setName("name");
			nvgm.add(new NVPair ("folderRef", "1234325"));
			NVEntityReferenceList nvl = new NVEntityReferenceList("nves");
			nvl.add(address);
			nvgm.add(nvl);
			json = GSONUtil.toJSONGenericMap(nvgm, true, false, false);
			System.out.println(json);
			//nvgm = GSONUtil.fromJSONGenericMap(json, null, Base64Type.URL);
			
			
			TestClass tc = new TestClass();
			tc.name = "not set";
			tc.nvgm = nvgm;
			json = GSONUtil.DEFAULT_GSON.toJson(tc);
			System.out.println(json);
			tc = GSONUtil.DEFAULT_GSON.fromJson(json, TestClass.class);
			
			json = GSONUtil.DEFAULT_GSON.toJson(tc);
            System.out.println(json);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
