package org.zoxweb.server.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.zoxweb.shared.util.Const.TimeInMillis;
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
	
	public static void main(String ...args)
	{
		
		
		byte[] message = SharedStringUtil.getBytes("JavaConsigliere Marwan NAEL y batata ds;fsdjjgdjglkfjdagkfd");
		
		try 
		{
			for (int i = 0; i < 10; i++)
			{
				long ts = System.nanoTime();
				KeyPair kp = CryptoUtil.generateKeyPair(2048, "rsa");
				ts = System.nanoTime() - ts;
				System.out.println("" + kp.toString() + " it took " + TimeInMillis.nanosToString(ts));
				kp.getPublic().getFormat();
				PublicKey pubKey = kp.getPublic();
				PrivateKey privKey = kp.getPrivate();
				System.out.println(SharedUtil.toCanonicalID(':', pubKey.getAlgorithm(), pubKey.getEncoded().length, pubKey.getFormat(),SharedStringUtil.bytesToHex(pubKey.getEncoded())));
				System.out.println(SharedUtil.toCanonicalID(':', privKey.getAlgorithm(), privKey.getEncoded().length, privKey.getFormat(), SharedStringUtil.bytesToHex(privKey.getEncoded())));
				
				byte encrypted[] = encrypt(pubKey, message);
				byte decrypted[] = decrypt(privKey, encrypted);
				System.out.println(SharedStringUtil.toString(decrypted));
				
				
				encrypted = encrypt(pubKey, message);
				decrypted = decrypt(privKey, encrypted);
				System.out.println(encrypted.length + ":" + SharedStringUtil.toString(decrypted));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
