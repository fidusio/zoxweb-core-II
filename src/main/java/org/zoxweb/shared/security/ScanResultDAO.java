package org.zoxweb.shared.security;



import org.zoxweb.shared.data.CanonicalIDDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.Const.ScanStatus;
/**
 * The ScanResultDAO object contains the scan result session of a file or stream
 * @author mnael
 *
 */
@SuppressWarnings("serial")
public class ScanResultDAO
	extends CanonicalIDDAO
{
	public enum Params
	implements GetNVConfig
	{
		
		
		LENGTH(NVConfigManager.createNVConfig("length", "The length of the data", "Length", false, true, Long.class)),
		STATUS(NVConfigManager.createNVConfig("status", "OK result no infection.", "status", false, true, ScanStatus.class)),
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
	
	/**
	 * Length in bytes of the scan
	 * @return
	 */
	public long getLength()
	{
		return lookupValue(Params.LENGTH);
	}
	
	public void setLength(long length)
	{
		setValue(Params.LENGTH, length);
	}
	
	/**
	 * @return true id the scan result is clean
	 */
	public ScanStatus getStatus()
	{
		return lookupValue(Params.STATUS);
	}
	
	public void setStatus(ScanStatus stat)
	{
		setValue(Params.STATUS, stat);
	}
	
	/**
	 * @return the scan duration in millis
	 */
	public long getScanDuration()
	{
		return lookupValue(Params.SCAN_DURATION);
	}
	
	public void setScanDuration(long scanDuration)
	{
		setValue(Params.SCAN_DURATION, scanDuration);
	}
	
	/**
	 * @return the scan native result
	 */
	public String getResult()
	{
		return lookupValue(Params.RESULT);
	}
	
	public void setResult(String scanResult)
	{
		setValue(Params.RESULT, scanResult);
	}
}
