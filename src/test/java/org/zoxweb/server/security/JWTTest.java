package org.zoxweb.server.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.JWTHeader;
import org.zoxweb.shared.security.JWTPayload;
import org.zoxweb.shared.security.SecurityConsts.JWTAlgorithm;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.NVGenericMapTest;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class JWTTest {

	private JWT jwtHS256 = null;
	private JWT jwtNONE = null;
	
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
		jwtHS256 = new JWT();
		jwtHS256.setHeader(header);
		jwtHS256.setPayload(payload);
		
		
		header = new JWTHeader();
		header.setJWTAlgorithm(JWTAlgorithm.none);
		payload = new JWTPayload();
		payload.setDomainID("xlogistx.io");
		payload.setAppID("xlogistx");
		payload.setRandom(new byte[] {0,1,2,3});
		payload.setSubjectID("none@xlogistx.io");
		jwtNONE = new JWT();
		jwtNONE.setHeader(header);
		jwtNONE.setPayload(payload);
		
	}
	
	@Test
	public void toGSON() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException
	{
		String json = GSONUtil.toJSON(jwtHS256, true, false, false, Base64Type.URL);
		System.out.println(json);
		
		
		JWT localJwt = GSONUtil.fromJSON(json, JWT.class, Base64Type.URL);
		json = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(json);
		
		System.out.println(localJwt.getPayload());
		
		String test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);
		
		JWTPayload payload = new JWTPayload();
		payload.setDomainID("xlogistx.io");
		payload.setAppID("xlogistx");
		payload.setSubjectID("support@xlogistx.io");
		payload.setName("John Doe");
		payload.setAdmin(true);
		payload.setRandom(new byte[] {0,1,2,3});
		localJwt.setPayload(payload);
		json = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(json);
		test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);

		System.out.println(CryptoUtil.decodeJWT("secret", test));
		
		String payloadJSON = GSONUtil.toJSON(payload, false, false, true, Base64Type.URL);
		System.out.println("-------------------------------------------------------------------");
		System.out.println(payloadJSON);
	
		
		NVGenericMap gm = GSONUtil.genericMapFromJSON(payloadJSON,(NVConfigEntity)payload.getNVConfig(), Base64Type.URL);
		//System.out.println(gm);
		NVGenericMapTest.printValue(gm);
		
		System.out.println("-------------------------------------------------------------------");
	}
	
	
	@Test
	public void testJWTNone() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException
	{
		String jsonNone = GSONUtil.toJSON(jwtNONE, true, false, false, Base64Type.URL);
		System.out.println(jsonNone);
		JWT localJwt = GSONUtil.fromJSON(jsonNone, JWT.class, Base64Type.URL);
		jsonNone = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(jsonNone);
		
		System.out.println(localJwt.getPayload());
		
		String test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);
		

		jsonNone = GSONUtil.toJSON(localJwt, false, false, false, Base64Type.URL);
		System.out.println(jsonNone);
		test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);

		System.out.println(CryptoUtil.decodeJWT((String)null, test));
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
