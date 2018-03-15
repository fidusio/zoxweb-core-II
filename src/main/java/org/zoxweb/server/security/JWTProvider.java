package org.zoxweb.server.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.JWTDecoder;
import org.zoxweb.shared.security.JWTDecoderData;
import org.zoxweb.shared.security.JWTEncoder;
import org.zoxweb.shared.security.JWTEncoderData;

public final class JWTProvider
implements JWTEncoder, JWTDecoder {
	
	public final static JWTProvider SINGLETON = new JWTProvider();
	
	private JWTProvider()
	{
		
	}
	

	@Override
	public String encode(byte[] key, JWT jwt) throws AccessSecurityException {
		// TODO Auto-generated method stub
		try 
		{
			return CryptoUtil.encodeJWT(key, jwt);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SecurityException | IOException e) {
	
			throw new AccessSecurityException(e.getMessage());
		}
	}


	@Override
	public JWT decode(byte[] key, String b64urlToken) throws AccessSecurityException {
		// TODO Auto-generated method stub
		try 
		{
			return CryptoUtil.decodeJWT(key, b64urlToken);
		} catch (InvalidKeyException | NoSuchAlgorithmException | SecurityException | IOException e) {
	
			throw new AccessSecurityException(e.getMessage());
		}
	}


	@Override
	public String encode(JWTEncoderData jed)
			throws AccessSecurityException
	{
		return encode(jed.getKey(), jed.getJWT());
	}


	@Override
	public JWT decode(JWTDecoderData jdd)
			throws AccessSecurityException
	{
		return decode(jdd.getKey(), jdd.getToken());
	}

}
