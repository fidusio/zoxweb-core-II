package org.zoxweb.server.api;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.zoxweb.server.security.JWTProvider;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.api.APIAppManager;
import org.zoxweb.shared.data.AppDeviceDAO;
import org.zoxweb.shared.data.AppIDDAO;
import org.zoxweb.shared.data.DeviceDAO;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.JWTHeader;
import org.zoxweb.shared.security.JWTPayload;
import org.zoxweb.shared.security.SubjectAPIKey;
import org.zoxweb.shared.security.SecurityConsts.JWTAlgorithm;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

public class APIAppManagerTest {

	private String domainID = "xlogistx.io";
	private String appID = "xlogistx";
	private String subjectID = "xlogistx@xlogistx.io";
	APIAppManager aam = new APIAppManagerProvider();
	@Before
	public void init()
	{
		DeviceDAO dd = new DeviceDAO();
		AppIDDAO aid = new AppIDDAO();
		aid.setDomainAppID(domainID, appID);
		AppDeviceDAO add = new AppDeviceDAO();
		add.setAppIDDAO(aid);
		add.setDevice(dd);
		add.setSubjectID(subjectID);
		aam.createAppDeviceDAO(add);
		
		
	}
	
	@Test
	public void testToGSON() throws IOException
	{
		SubjectAPIKey sak  = aam.lookupSubjectAPIKey(subjectID, true);
		System.out.println(GSONUtil.toJSON(sak, false, false, false));
	}
	
	
	@Test
	public void jwtValidation() throws IOException
	{

		SubjectAPIKey sak  = aam.lookupSubjectAPIKey(subjectID, true);
		JWTHeader header = new JWTHeader();
		
		header.setJWTAlgorithm(JWTAlgorithm.HS256);
		header.setTokenType("JWT");
		JWTPayload payload = new JWTPayload();
		payload.setDomainID(domainID);
		payload.setAppID(appID);
		payload.setSubjectID(subjectID);
		JWT jwt = new JWT();
		jwt.setHeader(header);
		jwt.setPayload(payload);
		
		String token = JWTProvider.SINGLETON.encodeJWT(sak.getAPISecretAsBytes(), jwt);
		
		System.out.println(token);
		
		
		JWT validateJWT = aam.validateJWT(token);
		System.out.println(GSONUtil.toJSON(jwt, false, false, false, Base64Type.URL));
		System.out.println(GSONUtil.toJSON(validateJWT, false, false, false, Base64Type.URL));
	}
	
	
	
}
