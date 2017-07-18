package org.zoxweb.server.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.zoxweb.shared.crypto.JWTEncoder;
import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;

public final class ServerJWTEncoder implements JWTEncoder {
	
	public final static ServerJWTEncoder SINGLETON = new ServerJWTEncoder();
	
	private ServerJWTEncoder()
	{
		
	}
	

	@Override
	public String encodeJWT(byte[] key, JWT jwt) throws AccessSecurityException {
		// TODO Auto-generated method stub
		try {
			return CryptoUtil.encodeJWT(key, jwt);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SecurityException | IOException e) {
	
			throw new AccessSecurityException(e.getMessage());
		}
	}

}
