package org.zoxweb.shared.data;

import org.zoxweb.shared.util.Const.TimeUnitType;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class CurrentTimestamp
	extends SetNameDescriptionDAO
{
	

	public enum Param
	implements GetNVConfig
	{
		CURRENT_TIME(NVConfigManager.createNVConfig("current_time", "Current time value", "CurrentTime", true, false, false, true, Long.class, null)),
		UNIT(NVConfigManager.createNVConfig("unit", "current time value unit", "Unit", true, false, false, true, TimeUnitType.class, null)),
		SOURCE(NVConfigManager.createNVConfig("source", "current time value unit", "Source", false, false, String.class)),
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
	
	public static final NVConfigEntity NVC_CURRENT_TIMESTAMP = new NVConfigEntityLocal(
	        "current_timestamp",
	        null,
	        CurrentTimestamp.class.getSimpleName(),
	        true,
	        false,
	        false,
	        false,
	        CurrentTimestamp.class,
	        SharedUtil.extractNVConfigs(Param.values()),
	        null,
	        false,
	        SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
	);
	
	public CurrentTimestamp()
	{
		super(NVC_CURRENT_TIMESTAMP);
		// TODO Auto-generated constructor stub
	}
	
	public CurrentTimestamp(long currentTimeValue, TimeUnitType tut)
	{
		this(currentTimeValue, tut, null);
	}
	
	public CurrentTimestamp(long currentTimeValue, TimeUnitType tut, String source)
	{
		this();
		setValue(Param.CURRENT_TIME, currentTimeValue);
		setValue(Param.UNIT, tut);
		setValue(Param.SOURCE, source);
	}
	
	public long currentTime()
	{
		return lookupValue(Param.CURRENT_TIME);
	}
	
	public TimeUnitType unit()
	{
		return lookupValue(Param.UNIT);
	}
	
	
	
}
