package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;

import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class JsonWebToken
extends SetNameDescriptionDAO
{
	
	public enum Param
	implements GetNVConfig
	{
		JWT_HEADER(NVConfigManager.createNVConfigEntity("header", "Header", "Header", true, true, JWTHeader.class, null)),
		JWT_PAYLOAD(NVConfigManager.createNVConfigEntity("payload", "Payload", "Payload",false, false, JWTPayload.class, null)),
		
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
																		JsonWebToken.class, 
																		SharedUtil.extractNVConfigs(Param.values()), 
																		null, 
																		false, 
																		SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
	
	
	
	
	
	
	public JsonWebToken()
	{
		super(NVC_JWT);
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
}
