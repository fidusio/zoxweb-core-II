package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;

import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class JWTDAO
extends SetNameDescriptionDAO
{
	public enum Param
	implements GetNVConfig
	{
		JWT(NVConfigManager.createNVConfigEntity("jwt", "JWT object", "JWT", true, false, JWT.class, null)),
		TOKEN(NVConfigManager.createNVConfig("token", "Original token", "Token", true, false, String.class)),
		SECRET(NVConfigManager.createNVConfig("secret", "The secret to validate the token", "Secret", true, false, byte[].class)),
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
	
	public static final NVConfigEntity NVC_JWT_TOKEN = new NVConfigEntityLocal("jwt_dao", 
																				null , 
																				"JWTDAO", 
																				true, 
																				false, 
																				false, 
																				false, 
																				JWTDAO.class, 
																				SharedUtil.extractNVConfigs(Param.values()), 
																				null, 
																				false, 
																				SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
	
	
	
	
	
	
	public JWTDAO()
	{
		super(NVC_JWT_TOKEN);
	}

	public JWTDAO(JWT jwt, String token, byte secret[])
	{
		this();
		setJWT(jwt);
		setToken(token);
		setSecret(secret);

	}
	
	
	public JWT getJWT()
	{
		return lookupValue(Param.JWT);
	}
	
	public void setJWT(JWT jwt)
	{
		setValue(Param.JWT, jwt);
	}
	
	public String getToken()
	{
		return lookupValue(Param.TOKEN);
	}
	
	public void setToken(String token)
	{
		setValue(Param.TOKEN, token);
	}
	
	public byte[] getSecret()
	{
		return lookupValue(Param.SECRET);
	}
	
	public void setSecret(byte[] secret)
	{
		setValue(Param.SECRET, secret);
	}
}
