package org.zoxweb.server.security;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.JWTHeader;
import org.zoxweb.shared.security.JWTPayload;
import org.zoxweb.shared.security.SecurityConsts.JWTAlgorithm;

public class JWTTest {

	private JWT jwt = null;
	@Before
	public void init()
	{
		JWTHeader header = new JWTHeader();
		JWTPayload payload = new JWTPayload();
		
		header.setJWTAlgorithm(JWTAlgorithm.HS256);
		header.setType("JWT");
		payload.setDomainID("xlogistx.io");
		payload.setAppID("xlogistx");
		payload.setRandom(new byte[] {0,1,2,3});
		payload.setSubjectID("support@xlogistx.io");
		jwt = new JWT();
		jwt.setHeader(header);
		jwt.setPayload(payload);
		
	}
	
	@Test
	public void toGSON() throws IOException
	{
		System.out.println(GSONUtil.toJSON(jwt, true, false, false));
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
