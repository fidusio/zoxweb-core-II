package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;


import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class IPBlockerConfig
extends SetNameDescriptionDAO
{
	public enum Param
	implements GetNVConfig
	{
		AUTH_FILE(NVConfigManager.createNVConfig("file_name", "Security file name", "Filename", true, true, String.class)),
		AUTH_TOKEN(NVConfigManager.createNVConfig("auth_token", "Authentication token marker", "AuthenticationToken", true, true, String.class)),
		AUTH_VALUE(NVConfigManager.createNVConfig("auth_value", "Authentication value", "AuthenticationValue", true, true, String.class)),
		COMMAND(NVConfigManager.createNVConfig("command", "Command to be excuted", "Command", true, true, String.class)),
		COMMAND_TOKEN(NVConfigManager.createNVConfig("command_token", "Command token to be replaced", "CommandToken", true, true, String.class)),
		MINUTES(NVConfigManager.createNVConfig("minutes", "Minutes for waiting", "Minutes", true, true, long.class)),
		RATE(NVConfigManager.createNVConfig("rate", "Rate", "Rate", true, true, float.class)),
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
	
	public static final NVConfigEntity NVC_IP_BLOCKER = new NVConfigEntityLocal(
																					"ip_blocker_config", 
																					null , 
																					"IPBlockerConfig", 
																					true, 
																					false, 
																					false, 
																					false, 
																					JWTHeader.class, 
																					SharedUtil.extractNVConfigs(Param.values()), 
																					null, 
																					false, 
																					SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);
	
	
	
	public IPBlockerConfig() {
		super(NVC_IP_BLOCKER);
		// TODO Auto-generated constructor stub
	}
	
	
	public String getAuthfile()
	{
		return lookupValue(Param.AUTH_FILE);
	}
	
	public String getAuthToken()
	{
		return lookupValue(Param.AUTH_TOKEN);
	}
	
	public String getAuthValue()
	{
		return lookupValue(Param.AUTH_VALUE);
	}
	
	public String getCommand()
	{
		return lookupValue(Param.COMMAND);
	}
	
	public String getCommandToken()
	{
		return lookupValue(Param.COMMAND_TOKEN);
	}
	
	public long getMinutes()
	{
		return lookupValue(Param.MINUTES);
	}
	
	public float getRate()
	{
		return lookupValue(Param.RATE);
	}
	
	
	

}
