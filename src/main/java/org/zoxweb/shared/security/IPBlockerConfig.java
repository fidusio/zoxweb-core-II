package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.AppConfig;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.Const.TimeInMillis;
import org.zoxweb.shared.filters.FloatRangeFilter;
import org.zoxweb.shared.filters.LongRangeFilter;

@SuppressWarnings("serial")
public class IPBlockerConfig
extends SetNameDescriptionDAO
implements AppConfig
{
	public enum Param
	implements GetNVConfig
	{
		AUTH_FILE(NVConfigManager.createNVConfig("file_name", "Security file name", "Filename", true, true, String.class)),
		AUTH_TOKEN(NVConfigManager.createNVConfig("auth_token", "Authentication token marker", "AuthenticationToken", true, true, String.class)),
		AUTH_VALUE(NVConfigManager.createNVConfig("auth_value", "Authentication value", "AuthenticationValue", true, true, String.class)),
		COMMAND(NVConfigManager.createNVConfig("command", "Command to be excuted", "Command", true, true, String.class)),
		COMMAND_TOKEN(NVConfigManager.createNVConfig("command_token", "Command token to be replaced", "CommandToken", true, true, String.class)),
		TIGGER_COUNT(NVConfigManager.createNVConfig("trigger_count", "Trigger count", "TriggerCount", true, true, long.class)),
		RESET_TIME(NVConfigManager.createNVConfig("reset_time", "Reset time in min", "TriggerCount", true, true, false, long.class, new LongRangeFilter(1, true, 15, true))),
		RATE(NVConfigManager.createNVConfig("rate", "Rate", "Rate", true, true, false, float.class, new FloatRangeFilter(1, true, 100, true))),
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
																				IPBlockerConfig.class, 
																				SharedUtil.extractNVConfigs(Param.values()), 
																				null, 
																				false, 
																				SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);
	
	
	
	public IPBlockerConfig() {
		super(NVC_IP_BLOCKER);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get the authentication file name that should be monitored
	 * @return
	 */
	public String getAuthFile()
	{
		return lookupValue(Param.AUTH_FILE);
	}
	
	public void setAuthFile(String filename)
	{
		setValue(Param.AUTH_FILE, filename);
	}
	
	/**
	 * The authentication token marker to detect bad login
	 * @return
	 */
	public String getAuthToken()
	{
		return lookupValue(Param.AUTH_TOKEN);
	}
	
	public void setAuthToken(String authToken)
	{
		setValue(Param.AUTH_TOKEN, authToken);
	}
	
	/**
	 * The authentication token value that should be extracted from the auth_token
	 * @return
	 */
	public String getAuthValue()
	{
		return lookupValue(Param.AUTH_VALUE);
	}
	
	public void setAuthValue(String authValue)
	{
		setValue(Param.AUTH_VALUE, authValue);
	}
	/**
	 * The command that need to be executed to block the attacker ip
	 * @return
	 */
	public String getCommand()
	{
		return lookupValue(Param.COMMAND);
	}
	
	public void setCommand(String command)
	{
		setValue(Param.COMMAND, command);
	}
	
	/**
	 * The token that needs to be injected
	 * @return
	 */
	public String getCommandToken()
	{
		return lookupValue(Param.COMMAND_TOKEN);
	}
	
	public void setCommandToken(String commandToken)
	{
		setValue(Param.COMMAND_TOKEN, commandToken);
	}
	
	/**
	 * How many failure to wait before trigger
	 * @return
	 */
	public long getTriggerCount()
	{
		return lookupValue(Param.TIGGER_COUNT);
	}
	
	public void setTriggerCount(long count)
	{
		setValue(Param.TIGGER_COUNT, count);
	}
	
	
	/**
	 * How many failure to wait before trigger
	 * @return
	 */
	public long getResetTime()
	{
		return lookupValue(Param.RESET_TIME);
	}
	
	public void setResetTime(long time)
	{
		setValue(Param.RESET_TIME, time);
	}
	
	public long getResetTimeInMillis()
	{
		long ret = getResetTime() * TimeInMillis.MINUTE.MILLIS;
		if (ret == 0)
		{
			ret = 10 * TimeInMillis.MINUTE.MILLIS;
		}
		
		return ret;
	}
	
	/**
	 * Attack rate to be confirmed as bad IP
	 * @return
	 */
	public float getRate()
	{
		return lookupValue(Param.RATE);
	}
	
	
	public void setRate(float rate)
	{
		setValue(Param.RATE, rate);
	}
	
	
	

}
