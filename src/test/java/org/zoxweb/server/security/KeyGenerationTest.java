package org.zoxweb.server.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.crypto.EncryptedDAO;
import org.zoxweb.shared.crypto.EncryptedKeyDAO;
import org.zoxweb.shared.util.Const.TimeInMillis;
import org.zoxweb.shared.util.SharedBase64.Base64Type;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;




public class KeyGenerationTest
{
	
	public static byte[] encrypt(Key key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");   
	    cipher.init(Cipher.ENCRYPT_MODE, key);  
	    return cipher.doFinal(plaintext);
	}

	public static byte[] decrypt(Key key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
	    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");   
	    cipher.init(Cipher.DECRYPT_MODE, key);  
	    return cipher.doFinal(ciphertext);
	}
	
	
	public static PublicKey readPublicKey(byte[] keys) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
	    X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(keys);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePublic(publicSpec);       
	}

	public static PrivateKey readPrivateKey(byte[] keys) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keys);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    return keyFactory.generatePrivate(keySpec);     
	}
	
	
	
	public static void main(String ...args)
	{
		
		
		byte[] message = SharedStringUtil.getBytes("JavaConsigliere Marwan NAEL y batata ds;fsdjjgdjglkfjdagkfd");
		
		try 
		{
			for (int i = 0; i < 10; i++)
			{
				long ts = System.nanoTime();
				KeyPair kp = CryptoUtil.generateKeyPair(3072, "rsa");
				ts = System.nanoTime() - ts;
				System.out.println("" + kp.toString() + " it took " + TimeInMillis.nanosToString(ts));
				kp.getPublic().getFormat();
				PublicKey pubKey = kp.getPublic();
				PrivateKey privKey = kp.getPrivate();
				System.out.println(CryptoUtil.toString(pubKey));
				System.out.println(CryptoUtil.toString(privKey));
				
				byte encrypted[] = encrypt(pubKey, message);
				byte decrypted[] = decrypt(privKey, encrypted);
				System.out.println(SharedStringUtil.toString(decrypted));
				Key aesKey = CryptoUtil.generateKey(256, CryptoUtil.AES);
				System.out.println(CryptoUtil.toString(aesKey));
				
				
				encrypted = encrypt(pubKey, message);
				decrypted = decrypt(privKey, encrypted);
				System.out.println(encrypted.length + ":" + SharedStringUtil.toString(decrypted));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		int loopSize = 1000;
		
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
		  
		  int loop = 1000;
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
