package org.zoxweb.server.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.zoxweb.shared.crypto.JWTDecoder;
import org.zoxweb.shared.crypto.JWTEncoder;
import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;

public final class JWTProvider
implements JWTEncoder, JWTDecoder {
	
	public final static JWTProvider SINGLETON = new JWTProvider();
	
	private JWTProvider()
	{
		
	}
	

	@Override
	public String encodeJWT(byte[] key, JWT jwt) throws AccessSecurityException {
		// TODO Auto-generated method stub
		try 
		{
			return CryptoUtil.encodeJWT(key, jwt);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SecurityException | IOException e) {
	
			throw new AccessSecurityException(e.getMessage());
		}
	}


	@Override
	public JWT decodeJWT(byte[] key, String b64urlToken) throws AccessSecurityException {
		// TODO Auto-generated method stub
		try 
		{
			return CryptoUtil.decodeJWT(key, b64urlToken);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SecurityException | IOException e) {
	
			throw new AccessSecurityException(e.getMessage());
		}
	}

}
