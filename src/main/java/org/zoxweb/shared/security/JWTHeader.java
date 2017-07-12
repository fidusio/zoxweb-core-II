package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.security.SecurityConsts.JWTAlgorithm;

@SuppressWarnings("serial")
public class JWTHeader
extends SetNameDescriptionDAO
{
	public enum Param
	implements GetNVConfig
	{
		ALG(NVConfigManager.createNVConfig("alg", "Algorithm", "Alg", true, true, JWTAlgorithm.class)),
		TYP(NVConfigManager.createNVConfig("typ", "Content type", "ContentType",false, false, String.class)),
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
	
	public static final NVConfigEntity NVC_JWT_HEADER = new NVConfigEntityLocal(
																					"jwt_header", 
																					null , 
																					"JWTHeader", 
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

	public JWTHeader()
	{
		super(NVC_JWT_HEADER);
		// TODO Auto-generated constructor stub
	}
	
	public JWTAlgorithm getJWTAlgorithm()
	{
		return lookupValue(Param.ALG);
	}
	
	public void setJWTAlgorithm(JWTAlgorithm type)
	{
		setValue(Param.ALG, type);
	}
	
	public String getType()
	{
		return lookupValue(Param.TYP);
	}
	
	public void setType(String type)
	{
		setValue(Param.TYP, type);
	}
	

}
