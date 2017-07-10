package org.zoxweb.server.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.security.JsonWebToken;
import org.zoxweb.shared.security.JWTHeader;
import org.zoxweb.shared.security.JWTPayload;
import org.zoxweb.shared.security.SecurityConsts.JWTAlgorithm;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class JWTTest {

	private JsonWebToken jwt = null;
	@Before
	public void init()
	{
		JWTHeader header = new JWTHeader();
		
		
		header.setJWTAlgorithm(JWTAlgorithm.HS256);
		header.setType("JWT");
		JWTPayload payload = new JWTPayload();
		payload.setDomainID("xlogistx.io");
		payload.setAppID("xlogistx");
		payload.setRandom(new byte[] {0,1,2,3});
		payload.setSubjectID("support@xlogistx.io");
		jwt = new JsonWebToken();
		jwt.setHeader(header);
		jwt.setPayload(payload);
		
	}
	
	@Test
	public void toGSON() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException
	{
		String json = GSONUtil.toJSON(jwt, true, false, false, Base64Type.URL);
		System.out.println(json);
		JsonWebToken localJwt = GSONUtil.fromJSON(json, JsonWebToken.class, Base64Type.URL);
		json = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(json);
		
		System.out.println(localJwt.getPayload());
		
		JWTPayload payload = new JWTPayload();
		payload.setSub("1234567890");
		payload.setName("John Doe");
		payload.setAdmin(true);
		localJwt.setPayload(payload);
		json = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(json);
		String test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);

		System.out.println(CryptoUtil.decodeJWT("secret".getBytes(), test));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidDomain()
	{

		
		JWTPayload payload = new JWTPayload();
		
		payload.setDomainID("xlogistx");
		payload.setAppID("xlogistx");
		payload.setRandom(new byte[] {0,1,2,3});
		payload.setSubjectID("support@xlogistx.io");
	}
}
