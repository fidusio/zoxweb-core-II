package org.zoxweb.shared.crypto;

import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;

public interface JWTDecoder 
{
	public JWT decodeJWT(byte key[], String b64URLToken)
			throws AccessSecurityException;
}
