package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;

import org.zoxweb.shared.util.AppID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.security.SecurityConsts.JWTAlgorithm;

@SuppressWarnings("serial")
public class JWT
extends SetNameDescriptionDAO
{

	public enum JWTToken
	{
		HEADER,
		PAYLOAD,
		HASH
	}



	public enum Param
	implements GetNVConfig
	{
		JWT_HEADER(NVConfigManager.createNVConfigEntity("header", "Header", "Header", true, true, JWTHeader.class, null)),
		JWT_PAYLOAD(NVConfigManager.createNVConfigEntity("payload", "Payload", "Payload", false, false, JWTPayload.class, null)),
		JWT_HASH(NVConfigManager.createNVConfig("hash", "hash", "Hash", false, false, byte[].class)),
		
		;
		
		private final NVConfig nvc;
		
		Param(NVConfig nvc)
		{
	        this.nvc = nvc;
		}
		
		public NVConfig getNVConfig() 
		{
			return nvc;
		}
	}
	
	public static final NVConfigEntity NVC_JWT = new NVConfigEntityLocal("jwt", 
																		null , 
																		"JWT", 
																		true, 
																		false, 
																		false, 
																		false, 
																		JWT.class, 
																		SharedUtil.extractNVConfigs(Param.values()), 
																		null, 
																		false, 
																		SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
	
	
	
	
	
	
	public JWT()
	{
		super(NVC_JWT);
	}

	public JWT(JWTHeader header, JWTPayload payload)
	{
		this();
		setHeader(header);
		setPayload(payload);

	}
	public JWTHeader getHeader() {
		return lookupValue(Param.JWT_HEADER);
	}

	public void setHeader(JWTHeader header) {
		setValue(Param.JWT_HEADER, header);
	}

	public JWTPayload getPayload() {
		return lookupValue(Param.JWT_PAYLOAD);
	}

	public void setPayload(JWTPayload payload) {
		setValue(Param.JWT_PAYLOAD, payload);
	}
	
	
	public byte[] getHash()
	{
		return lookupValue(Param.JWT_HASH);
	}
	
	
	public void setHash(byte[] hash)
	{
		setValue(Param.JWT_HASH, hash);
	}

    public static JWT createJWT(JWTAlgorithm algorithm, String subjectID, AppID<String> appID) {
	    return createJWT(algorithm, subjectID, appID.getDomainID(), appID.getAppID());
    }

	public static JWT createJWT(JWTAlgorithm algorithm, String subjectID, String domainID, String appID) {
        JWTHeader jwtHeader = new JWTHeader();
        jwtHeader.setJWTAlgorithm(algorithm);
        jwtHeader.setType("JWT");

        JWTPayload jwtPayload = new JWTPayload();
        jwtPayload.setDomainID(domainID);
        jwtPayload.setAppID(appID);
        jwtPayload.setSubjectID(subjectID);

        JWT jwt = new JWT();
        jwt.setHeader(jwtHeader);
        jwt.setPayload(jwtPayload);

        return jwt;
    }

}