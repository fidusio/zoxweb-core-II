package org.zoxweb.server.util;

import java.util.UUID;

import org.zoxweb.shared.data.StatCounter;
import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class IDGeneratorUtilTest {

	public static void main(String ...args)
	{
		// invoke it for the first time to force class loading
		IDGeneratorUtil.UUIDSHA256Base64.generateID();
		
		StatCounter sc = new StatCounter();
		
		
		int max = 10000;
		for(int i = 0; i < max; i++)
		{
			IDGeneratorUtil.UUIDSHA256Base64.generateID();
		}
		
		System.out.println(sc.deltaNow() + " millis for " + max);
		
		max=10;
		sc.setReferenceTime();
		
		for(int i = 0; i < max; i++)
		{
			String id = IDGeneratorUtil.UUIDSHA256Base64.generateID();
			System.out.println(id + " length:" + id.length());
		}
		
		System.out.println(sc.deltaNow() + " millis for " + max);
		
		sc.setReferenceTime();
		max = 10000;
		for(int i = 0; i < max; i++)
		{
			IDGeneratorUtil.UUIDBase64.generateID();
		}
		
		System.out.println(sc.deltaNow() + " millis for " + max);
		
		max=10;
		sc.setReferenceTime();
		
		for(int i = 0; i < max; i++)
		{
			String id = IDGeneratorUtil.UUIDBase64.generateID();
			System.out.println(id + " length:" + id.length());
		}
		
		System.out.println(sc.deltaNow() + " millis for " + max);
		
		String id = UUID.randomUUID().toString();
		String b64 = SharedBase64.encodeAsString(Base64Type.URL, id);
		System.out.println(id + " length:" + id.length());
		System.out.println(b64 + " length:" + b64.length());
		System.out.println(SharedBase64.decodeAsString(Base64Type.URL, b64));
	
	}
}
