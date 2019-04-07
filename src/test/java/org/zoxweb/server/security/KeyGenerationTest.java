package org.zoxweb.server.security;


import java.security.Key;

import java.security.KeyPair;

import java.security.PublicKey;




import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.crypto.CryptoConst;
import org.zoxweb.shared.crypto.EncryptedDAO;
import org.zoxweb.shared.crypto.EncryptedKeyDAO;
import org.zoxweb.shared.util.Const.TimeInMillis;
import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedBase64.Base64Type;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;




public class KeyGenerationTest
{
	

//	public static PublicKey readPublicKey(byte[] keys) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
//	{
//	    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(keys);
//	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	    return keyFactory.generatePublic(publicSpec);
//	}
//
//	public static PrivateKey readPrivateKey(byte[] keys) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
//	{
//	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keys);
//	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	    return keyFactory.generatePrivate(keySpec);
//	}
	
	
	
	public static void main(String ...args)
	{
		
		
		byte[] message = SharedStringUtil.getBytes("JavaConsigliere Batata, JavaConsigliere Batata, JavaConsigliere Batata, JavaConsigliere Batata, JavaConsigliere Batata, JavaConsigliere Batata, JavaConsigliere Batata, JavaConsigliere Batata, JavaConsigliere Batata.");
		
		try 
		{
			for (int i = 0; i < 5; i++)
			{

				KeyPair aliceKey = CryptoUtil.generateKeyPair(2048, "rsa");
				long ts = System.nanoTime();
				KeyPair bobKey = CryptoUtil.generateKeyPair(2048, "rsa");
				ts = System.nanoTime() - ts;
				System.out.println("" + bobKey.toString() + " it took " + TimeInMillis.nanosToString(ts));
				bobKey.getPublic().getFormat();




				for(int j=0; j<2; j++)
				{
					System.out.println(CryptoUtil.toString(bobKey.getPublic()));
					System.out.println(CryptoUtil.toString(bobKey.getPrivate()));
					byte signature[] = CryptoUtil.sign(CryptoConst.SignatureAlgo.SHA256_RSA, bobKey.getPrivate(), message);
					System.out.println("Signature length:" + signature.length + " : " + CryptoUtil.verify(CryptoConst.SignatureAlgo.SHA256_RSA, bobKey.getPublic(), message, signature) + " : " + SharedBase64.encodeAsString(Base64Type.URL, signature));
					byte encrypted[] = CryptoUtil.encrypt(bobKey.getPublic(), aliceKey.getPrivate(), message);

					byte decrypted[] = CryptoUtil.decrypt(bobKey.getPrivate(), aliceKey.getPublic(), encrypted);
					System.out.println("Signature length:" + signature.length + " : " + SharedBase64.encodeAsString(Base64Type.URL, signature));
					System.out.println("Decrypted Message:" + SharedStringUtil.toString(decrypted));
					System.out.println("Encrypted by bob based64 [" + encrypted.length +"]:" + SharedBase64.encodeAsString(Base64Type.URL, encrypted));

					Key aesKey = CryptoUtil.generateKey(256, CryptoUtil.AES);
					System.out.println(CryptoUtil.toString(aesKey));


					encrypted = CryptoUtil.encrypt(aliceKey.getPublic(), bobKey.getPrivate(), message);
					decrypted = CryptoUtil.decrypt(aliceKey.getPrivate(), bobKey.getPublic(), encrypted);
					System.out.println("Decrypted Message:" + SharedStringUtil.toString(decrypted));
					System.out.println("Encrypted by alice based64 [" + encrypted.length +"]:" + SharedBase64.encodeAsString(Base64Type.URL, encrypted));
					System.out.println(j);
				}
				System.out.println("\n\n");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		

		int loopSize = 500;
		
		Key keys[] = new Key[loopSize];
		try {
			long ts = System.currentTimeMillis();
			for (int i = 0; i < loopSize; i++)
			{
				keys[i] = CryptoUtil.generateKey(i%2 == 0 ? 256 : 128, CryptoUtil.AES);
			}
			ts = System.currentTimeMillis() - ts;
			for(Key k : keys)
			{
				System.out.println(SharedUtil.toCanonicalID(',', k.getAlgorithm(),k.getEncoded().length, SharedStringUtil.bytesToHex(k.getEncoded())));
			}
			System.out.println("it took " + TimeInMillis.toString(ts));
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try {
		  
		  int loop = 500;
		  long ts = System.currentTimeMillis();
		  for (int i = 0; i < loop; i++)
		  {
		    CryptoUtil.encryptDAO(new EncryptedKeyDAO(), SharedStringUtil.getBytes("password"), null, 8196);
		  }
		  ts = System.currentTimeMillis() - ts;
		  System.out.println("Generated " + loop + " with rehash 8196 took " + TimeInMillis.toString(ts));
		  loop = 100000;
		  ts = System.currentTimeMillis();
          for (int i = 0; i < loop; i++)
          {
            CryptoUtil.encryptDAO(new EncryptedKeyDAO(), SharedStringUtil.getBytes("password"), null, 1);
          }
          ts = System.currentTimeMillis() - ts;
          System.out.println("Generated " + loop + " with rehash 1 took " + TimeInMillis.toString(ts));
          EncryptedDAO ed = CryptoUtil.encryptDAO(new EncryptedKeyDAO(), SharedStringUtil.getBytes("password"), null, 1);
          String json = GSONUtil.toJSON(ed, false, false, false, Base64Type.URL);
          System.out.println(json);
          ed = GSONUtil.fromJSON(json, EncryptedKeyDAO.class, Base64Type.URL);
          byte data[] = CryptoUtil.decryptEncryptedDAO(ed, SharedStringUtil.getBytes("password"), 1);
          System.out.println(SharedStringUtil.bytesToHex(data));
		}
		catch(Exception e) {
		  e.printStackTrace();
		}
		
		for(String filename : args)
		{
			try
			{
//				String pem = IOUtil.inputStreamToString(filename);
//				String splits[] = pem.split("\n");
//				System.out.println(pem);
//				System.out.println(splits.length);
//				StringBuffer sb = new StringBuffer();
//				for (String str : splits)
//				{
//					if(!str.startsWith("-"))
//						sb.append(str);
//				}
//				System.out.println(sb.toString());
//				
//				
//				
//				
//				byte keys[] = SharedBase64.decode(sb.toString());
//				
//				
//				
//				
//				
//				//PublicKey pubKey = readPublicKey(keys);
//				PrivateKey priKey = readPrivateKey(keys);
//				System.out.println(filename);
//				//System.out.println("public  key:" +  SharedBase64.encodeAsString(Base64Type.URL, pubKey.getEncoded()));
				PublicKey pk = CryptoUtil.getPublicKey(filename, 443);
				System.out.println(filename + " public key:" +  CryptoUtil.toString(pk));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
