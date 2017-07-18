package org.zoxweb.shared.crypto;

import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;

public interface JWTEncoder 
{
	public String encodeJWT(byte[] key, JWT jwt)
			throws AccessSecurityException;
}
