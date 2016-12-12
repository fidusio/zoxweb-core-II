package org.zoxweb;

import org.zoxweb.server.crypto.CryptoUtil;
import org.zoxweb.server.io.UByteArrayOutputStream;
import org.zoxweb.shared.crypto.EncryptedDAO;
import org.zoxweb.shared.crypto.EncryptedKeyDAO;
import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.Const.SizeInBytes;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class CryptoUtilTest 
{
	public static void main(String ...args)
	{
		try 
		{
			
			int len = 4096;
			int repeat = 5;
			
			
			int index = 0;
			if (args.length > index)
			{
				len = (int) SizeInBytes.parse(args[index++]);
			}
			
			if (args.length > index)
			{
				
				
				repeat = Integer.parseInt(args[index++]);
			}
			
			
			
			//EncryptedKeyDAO ekd = CryptoUtil.createEncryptedKeyDAO("password");
			
			UByteArrayOutputStream ubaos = new UByteArrayOutputStream();
			for (int i = 0; i < len; i++)
			{
				ubaos.write(i);
			}
			ubaos.close();
			byte original [] = ubaos.toByteArray();
			
			
			
			
			for (int i = 0; i < repeat; i++)
			{
				EncryptedDAO ed = new EncryptedDAO();
				long delta = System.nanoTime();
				ed = CryptoUtil.encryptDAO(ed, "password".getBytes(), original);
				delta = System.nanoTime() - delta;
				System.out.println("Encrypting: " + original.length +" bytes took "+ Const.TimeInMillis.nanosToString(delta));
				//System.out.println(ed.toCanonicalID());
				delta = System.nanoTime();
				byte []data = CryptoUtil.decryptEncryptedDAO(ed, "password");
				delta = System.nanoTime() - delta;
				System.out.println(SharedUtil.slowEquals(original, data) + ": decrypting took " + Const.TimeInMillis.nanosToString(delta));
			}
			
			EncryptedKeyDAO ekd = CryptoUtil.createEncryptedKeyDAO("password");
			byte key[] = CryptoUtil.decryptEncryptedDAO(ekd, "password");
			System.out.println(SharedStringUtil.bytesToHex(key));
			System.out.println(ekd.toCanonicalID());
			
			
			EncryptedDAO ed = CryptoUtil.encryptDAO(new EncryptedKeyDAO(), SharedStringUtil.getBytes("password"), SharedStringUtil.getBytes("password"));
			System.out.println(ed.toCanonicalID());
			
			key = CryptoUtil.decryptEncryptedDAO(ed, "password");
			System.out.println(SharedStringUtil.bytesToHex(key));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
