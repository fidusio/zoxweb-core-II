package org.zoxweb.shared.util;

import java.util.UUID;

public class TokenTest
{
	public static void main(String ...args)
	{
		UUID uuid = UUID.randomUUID();
		uuid.getLeastSignificantBits();
		
		
		byte[] token = BytesValue.LONG.toBytes(null, 0, uuid.getLeastSignificantBits(), uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
		byte[] b64 = SharedBase64.encode(token);
		System.out.println(SharedStringUtil.toString(b64) + " " + uuid);
		
	}
}
