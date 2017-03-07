package org.zoxweb.shared.security;



import org.zoxweb.shared.data.CanonicalIDDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class ScanResultDAO
	extends CanonicalIDDAO
{
	public enum Params
	implements GetNVConfig
	{
		
		
		LENGTH(NVConfigManager.createNVConfig("length", "The length of the data", "Length", false, true, Long.class)),
		IS_CLEAN(NVConfigManager.createNVConfig("is_clean", "True if the scan result is OK.", "IsClean", false, true, Boolean.class)),
		SCAN_DURATION(NVConfigManager.createNVConfig("scan_duration", "The scan duration in millis", "ScanDuration", false, true, Long.class)),
		RESULT(NVConfigManager.createNVConfig("result", "The result of the scan", "Result", false, true, String.class)),
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
	
	public static final NVConfigEntity NVC_SCAN_RESULT_DAO = new NVConfigEntityLocal(
																					"scan_result_dao", 
																					null , 
																					"ScanResultDAO", 
																					true, 
																					false, 
																					false, 
																					false, 
																					ScanResultDAO.class, 
																					SharedUtil.extractNVConfigs(Params.values()), 
																					null, 
																					false, 
																					CanonicalIDDAO.NVC_CANONICAL_ID_DAO
																				);
	
	public ScanResultDAO()
	{
		super(NVC_SCAN_RESULT_DAO);
	}
	
	public long getLength()
	{
		return lookupValue(Params.LENGTH);
	}
	
	public void setLength(long length)
	{
		setValue(Params.LENGTH, length);
	}
	
	public boolean isClean()
	{
		return lookupValue(Params.IS_CLEAN);
	}
	
	public void setClean(boolean clean)
	{
		setValue(Params.IS_CLEAN, clean);
	}
	
	public long getScanDuration()
	{
		return lookupValue(Params.SCAN_DURATION);
	}
	
	public void setScanDuration(long scanDuration)
	{
		setValue(Params.SCAN_DURATION, scanDuration);
	}
	
	
	public String getResult()
	{
		return lookupValue(Params.RESULT);
	}
	
	public void setResult(String scanResult)
	{
		setValue(Params.RESULT, scanResult);
	}
}
