package org.zoxweb.shared.data.shiro;

import java.util.List;

import org.zoxweb.shared.data.SetNameDAO;

import org.zoxweb.shared.util.CRUD;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class ShiroNVEntityCRUDs
	extends SetNameDAO
{
	
	
	public enum Params
	implements GetNVConfig
	{
		CRUDS(NVConfigManager.createNVConfig("cruds", "The supported permissions", "CRUDS", false, false, CRUD[].class)),
		;
		
		private final NVConfig cType;
		
		Params(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}
	}

	
	public static final NVConfigEntity NVC_SHIRO_NVENTITY_CRUDS = new NVConfigEntityLocal("shiro_nventity_cruds", null , null, true, false, false, false, ShiroNVEntityCRUDs.class, SharedUtil.extractNVConfigs(Params.values()), null, false, SetNameDAO.NVC_NAME_DAO);
	
	
	public ShiroNVEntityCRUDs()
	{
		super(NVC_SHIRO_NVENTITY_CRUDS);
	}
	
	
	
	protected ShiroNVEntityCRUDs(NVConfigEntity nvce) 
	{
		super(nvce);
		// TODO Auto-generated constructor stub
	}
	
	
	public final List<CRUD> getCRUDs()
	{
		return lookupValue(Params.CRUDS);
	}

	
	
	public boolean isPermitted(CRUD crud)
	{
		if (getCRUDs() != null)
		{
			for (CRUD c : getCRUDs())
			{
				if (crud == c)
					return true;
			}
		}
		return false;	
	}

}
