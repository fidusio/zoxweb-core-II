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
			
			try 
			{
				String ret;
				do 
				{
					ret = SharedBase64.encodeAsString(Base64Type.URL, HashUtil.hashSequence("SHA-256", UUID.randomUUID().toString()));
				}while(!valide(ret));
				
				return ret;
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
			String ret;
			
			do 
			{
				ret = SharedBase64.encodeAsString(Base64Type.URL, UUID.randomUUID().toString());
			}while(!valide(ret));
			
			return ret;
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
	
	
	private static boolean valide(String str)
	{
		if (str.charAt(0) == '_' || str.charAt(0) == '-')
		{
			return false;
		}
		return true;
	}
	
}
