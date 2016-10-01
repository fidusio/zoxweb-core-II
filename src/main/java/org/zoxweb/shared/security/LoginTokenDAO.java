package org.zoxweb.shared.security;

import java.io.Serializable;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

/**
 * The login token data access object.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class LoginTokenDAO 
	extends SetNameDescriptionDAO
	implements Serializable
{
	
	/**
	 * This enum contains login token parameters.
	 * @author mzebib
	 *
	 */
	public enum Param
		implements GetNVConfig
	{
		LAST_NAME(NVConfigManager.createNVConfig("last_name", "Last name", "LastName", false, true, String.class)),
		LOGIN_ID(NVConfigManager.createNVConfig("login_id", "Login ID", "LoginID", false, true, String.class)),
		SESSION_ID(NVConfigManager.createNVConfig("session_id", "Session ID", "SessionID", false, true, String.class)),
		
		;
		
		private final NVConfig cType;
		
		Param(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}
	}
	
	public static final NVConfigEntity NVC_LOGIN_IN_DAO = new NVConfigEntityLocal(
																					"login_token_dao", 
																					null , 
																					"LoginTokenDAO", 
																					true, 
																					false, 
																					false, 
																					false, 
																					LoginTokenDAO.class, 
																					SharedUtil.extractNVConfigs(Param.values()), 
																					null, 
																					false, 
																					SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																				);

	/**
	 * The default constructor.
	 */
	public LoginTokenDAO()
	{
		super(NVC_LOGIN_IN_DAO);
	}
	
	
	
	/**
	 * Gets last name.
	 * 
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lookupValue(Param.LAST_NAME);
	}
	
	/**
	 * Sets last name.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName)
	{
		setValue(Param.LAST_NAME, lastName);
	}
	
	/**
	 * Gets the login ID.
	 * 
	 * @return
	 */
	public String getLoginID() 
	{
		return lookupValue(Param.LOGIN_ID);
	}
	
	/**
	 * Sets the login ID.
	 * 
	 * @param loginID
	 */
	public void setLoginID(String loginID)
	{
		setValue(Param.LOGIN_ID, loginID);
	}
	
	
	/**
	 * Gets the current session ID optional.
	 * 
	 * @return
	 */
	public String getSessionID() 
	{
		return lookupValue(Param.SESSION_ID);
	}
	
	/**
	 * Sets the session ID.
	 * 
	 * @param sessionID
	 */
	public void setSessionID(String sessionID)
	{
		setValue(Param.SESSION_ID, sessionID);
	}
	
}