package org.zoxweb.server.util;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.zoxweb.server.security.HashUtil;
import org.zoxweb.shared.util.IDGenerator;
import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class IDGeneratorUtil 
{
	public static final IDGenerator<String> UUIDSHA256Base64 = new IDGenerator<String>()
			{
				
				@Override
				public String generateID() 
				{
					
					try {
						return SharedBase64.encodeAsString(Base64Type.URL, HashUtil.hashSequence("SHA-256", UUID.randomUUID().toString()));
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					throw new IllegalArgumentException("ID generation failed");
					
		
				}
		
				@Override
				public String getName() 
				{
					// TODO Auto-generated method stub
					return "UUIDSHA256Base64";
				}
			};
			
			
			public static final IDGenerator<String> UUIDBase64 = new IDGenerator<String>()
			{
				
				@Override
				public String generateID() 
				{
					
					return SharedBase64.encodeAsString(Base64Type.URL, UUID.randomUUID().toString());
				}
		
				@Override
				public String getName() 
				{
					// TODO Auto-generated method stub
					return "UUIDBase64";
				}
			};

	
	private IDGeneratorUtil()
	{
	}
	
}
