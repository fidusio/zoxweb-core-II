package org.zoxweb.server.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
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
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class JWTTest {

	private JWT jwtHS256 = null;
	private JWT jwtNONE = null;
	private long index = 0;
	
	@Before
	public void init()
	{
		JWTHeader header = new JWTHeader();
		
		header.setJWTAlgorithm(JWTAlgorithm.HS256);
		header.setTokenType("JWT");
		JWTPayload payload = new JWTPayload();
		payload.setDomainID("xlogistx.io");
		payload.setAppID("xlogistx");
		payload.setNonce(index++);
		//payload.setRandom(new byte[] {0,1,2,3});
		payload.setSubjectID("support@xlogistx.io");
		jwtHS256 = new JWT();
		jwtHS256.setHeader(header);
		jwtHS256.setPayload(payload);
		
		
		header = new JWTHeader();
		header.setJWTAlgorithm(JWTAlgorithm.none);
		payload = new JWTPayload();
		payload.setDomainID("xlogistx.io");
		payload.setAppID("xlogistx");
		payload.setNonce(index++);
		//payload.setRandom(new byte[] {0,1,2,3});
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
		
		System.out.println("ToGSON:***************************************************************");
		JWT localJwt = GSONUtil.fromJSON(json, JWT.class, Base64Type.URL);
		json = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(json);
		
		System.out.println(localJwt.getPayload());
		
		String test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);
		
		JWTPayload payload = new JWTPayload();
		payload.setDomainID("xlogistx.io");
		payload.setAppID("xlogistx");
		payload.setSubjectID("batata@xlogistx.io");
		payload.setName("John Doe");
		payload.setAdmin(true);
		payload.setNotBefore(System.currentTimeMillis());
		payload.setNonce(index++);
		
		//payload.setRandom(new byte[] {0,1,2,3});
		localJwt.setPayload(payload);
		
		NVGenericMap nvgm = payload.getNVGenericMap();
		NVPair nvp = (NVPair)nvgm.get("name");
		nvp.setValue("Mario");
		json = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(json);
		test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);

		System.out.println(CryptoUtil.decodeJWT("secret", test));
		
		String payloadJSON = GSONUtil.toJSON(payload, true, false, true, Base64Type.URL);
		System.out.println("-------------------------------------------------------------------");
		System.out.println(payloadJSON);
	
		
		NVGenericMap gm = GSONUtil.fromJSONGenericMap(payloadJSON,(NVConfigEntity)payload.getNVConfig(), Base64Type.URL);
		//System.out.println(gm);
		NVGenericMapTest.printValue(gm);
		
		
		JWTPayload tempJWTP = new  JWTPayload();
		gm.add(new NVPair("http://toto.com", "batata"));
		tempJWTP.setNVGenericMap(gm);
		System.out.println(tempJWTP);
	
		tempJWTP.getNotBefore();
		System.out.println("genericMapToJSON:" + GSONUtil.toJSONGenericMap(tempJWTP.getNVGenericMap(), false, false, false, Base64Type.URL));
		System.out.println( GSONUtil.toJSON(tempJWTP, false, false, true, Base64Type.URL));
		
		System.out.println("---------------++++++++++++++++++++----------------------------------------------------");
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
	
	
	
	@Test
	public void testJWTHS256() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException
	{
		
		System.out.println("--------------------------------------------------------------");
		String jsonHS256 = GSONUtil.toJSON(jwtHS256, false, false, false, Base64Type.URL);
		System.out.println(jsonHS256);
		JWT localJwt = GSONUtil.fromJSON(jsonHS256, JWT.class, Base64Type.URL);
		jsonHS256 = GSONUtil.toJSON(localJwt, true, false, false, Base64Type.URL);
		System.out.println(jsonHS256);
		
		System.out.println(localJwt.getPayload());
		
		String test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);
		

		jsonHS256 = GSONUtil.toJSON(localJwt, false, false, false, Base64Type.URL);
		System.out.println(jsonHS256);
		test = CryptoUtil.encodeJWT("secret", localJwt);
		System.out.println(test);

		System.out.println(CryptoUtil.decodeJWT("secret", test));
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	
	@Test
	public void testJWTGWT() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException
	{
		
		System.out.println("--------------------------------------------------------------");
		//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE1MDg0NjA1MzMzNDEsImRvbWFpbiI6Inhsb2dpc3R4LmlvIiwiYXBwIjoieGxvZ2lzdHgifQ.oxqpJP18sxq51qNt8_kisYeS0oR31QL2DrP-M_wYaO0";
		String gwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE1MDg0NzMyMzU0OTMsImRvbWFpbiI6Inhsb2dpc3R4LmlvIiwiYXBwIjoieGxvZ2lzdHgifQ.MrrlKa90ut2RBcfJ61lXB7ScwHikPyvkvpd_DVxPnFg";
		JWT decoded = CryptoUtil.decodeJWT("secret", gwtToken);		
		String test = CryptoUtil.encodeJWT("secret", decoded);
		System.out.println("TESTGWT  :" + gwtToken);
		System.out.println("local:" + test);
		System.out.println(decoded.getPayload());
		System.out.println(GSONUtil.toJSON(decoded, false, false, false, Base64Type.URL));
		System.out.println("Are equals:" + test.equals(gwtToken));
		Assert.assertEquals("2 tokens equals", test, gwtToken);
		decoded.getPayload().getNVGenericMap().add(new NVPair("mario", "taza"));
		String json = GSONUtil.toJSON(decoded, false, false, true, Base64Type.URL);
		System.out.println(json);
		JWT fromJSON = GSONUtil.fromJSON(json);
		json = GSONUtil.toJSON(fromJSON, false, false, false, Base64Type.URL);
		System.out.println(json);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	
	
	
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidDomain()
	{

		
		JWTPayload payload = new JWTPayload();
		
		payload.setDomainID("xlogistx");
		payload.setAppID("xlogistx");
		//payload.setRandom(new byte[] {0,1,2,3});
		payload.setSubjectID("support@xlogistx.io");
	}
}
