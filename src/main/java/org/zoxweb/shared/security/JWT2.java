/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.shared.security;


import org.zoxweb.shared.data.SetNameDescriptionDAO;


import org.zoxweb.shared.util.DataEncoder;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.SharedUtil;


@SuppressWarnings("serial")
public class JWT2
extends SetNameDescriptionDAO
{

	



	public enum Param
	implements GetNVConfig
	{
		JWT_HEADER(NVConfigManager.createNVConfig("header", "Header", "Header", true, true, NVGenericMap.class)),
		JWT_PAYLOAD(NVConfigManager.createNVConfig("payload", "Payload", "Payload", false, false, NVGenericMap.class)),
		JWT_HASH(NVConfigManager.createNVConfig("hash", "hash", "Hash", false, false, String.class)),
		
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
																		JWT2.class, 
																		SharedUtil.extractNVConfigs(Param.values()), 
																		null, 
																		false, 
																		SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
	
	
	
	
	
	
	public JWT2()
	{
		super(NVC_JWT);
	}

	
	public NVGenericMap getHeader() {
		return (NVGenericMap) lookup(Param.JWT_HEADER);
	}

	

	public NVGenericMap getPayload() {
		return (NVGenericMap) lookup(Param.JWT_PAYLOAD);
	}

	
	
	public String getHash()
	{
		return lookupValue(Param.JWT_HASH);
	}
	
	
	public void setHash(String hash)
	{
		setValue(Param.JWT_HASH, hash);
	}

//    public static JWT2 createJWT(JWTAlgorithm algorithm, String subjectID, AppID<String> appID) {
//	    return createJWT(algorithm, subjectID, appID.getDomainID(), appID.getAppID());
//    }

//	public static JWT2 createJWT(JWTAlgorithm algorithm, String subjectID, String domainID, String appID) {
//        JWTHeader jwtHeader = new JWTHeader();
//        jwtHeader.setJWTAlgorithm(algorithm);
//        jwtHeader.setTokenType("JWT");
//
//        JWTPayload jwtPayload = new JWTPayload();
//        jwtPayload.setDomainID(domainID);
//        jwtPayload.setAppID(appID);
//        jwtPayload.setSubjectID(subjectID);
//        // trick for multi-threading safety 
//        DataEncoder<JWTPayload, JWTPayload> tempEncoder = PAYLOAD_ENCODER;
//        if (tempEncoder != null)
//        	jwtPayload = tempEncoder.encode(jwtPayload);
//        else
//        	jwtPayload.setIssuedAt(System.currentTimeMillis());
//
//        JWT jwt = new JWT();
//        jwt.setHeader(jwtHeader);
//        jwt.setPayload(jwtPayload);
//
//        return jwt;
//    }
	
	public static DataEncoder<JWTPayload, JWTPayload>  PAYLOAD_ENCODER = null;
}