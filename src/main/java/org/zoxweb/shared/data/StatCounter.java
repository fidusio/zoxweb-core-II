package org.zoxweb.shared.data;



import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.Const.TimeUnitType;

@SuppressWarnings("serial")
public class StatCounter
	extends TimeStampDAO
{

	
	
	public enum Param
	implements GetNVConfig
	{
		TS_UNIT(NVConfigManager.createNVConfig("ts_unit", "TimeStampUnit.", "TimeStampUnit", true, false, TimeUnitType.class)),
		COUNTER(NVConfigManager.createNVConfig("counter", "Counter.", "Counter", true, true, long.class)),	
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
	
	public static final NVConfigEntity NVC_STAT_COUNTER_DAO = new NVConfigEntityLocal(
	        "stat_counter",
	        null,
	        StatCounter.class.getSimpleName(),
	        true,
	        false,
	        false,
	        false,
	        StatCounter.class,
	        SharedUtil.extractNVConfigs(Param.values()),
	        null,
	        false,
	        TimeStampDAO.NVC_TIME_STAMP_DAO
	);

	public StatCounter()
	{
		super(NVC_STAT_COUNTER_DAO);
		// TODO Auto-generated constructor stub
		
	}
	
	public StatCounter(TimeUnitType tut)
	{
		this();
		switch(tut)
		{
		case MILLIS:
			setCreationTime(System.currentTimeMillis());
			break;
		case NANOS:
			setCreationTime(System.nanoTime());
			break;
		
		}
		setTimeStampUnit(tut);
		increment(0);
	}
	
	
	public long increment()
	{
		return increment(1);
	}

	
	public synchronized long increment(long increment)
	{
		long val = getCounter() + increment;
		setCounter(val);
		switch(getTimeStampUnit())
		{
		case MILLIS:
			setLastTimeUpdated(System.currentTimeMillis());
			break;
		case NANOS:
			setLastTimeUpdated(System.nanoTime());
			break;
		}
		return val;
	}
	
	public long getCounter()
	{
		return lookupValue(Param.COUNTER);
	}
	
	public synchronized void setCounter(long counter)
	{
		setValue(Param.COUNTER, counter);
	}
	
	public void setTimeStampUnit(TimeUnitType tut)
	{
		setValue(Param.TS_UNIT, tut);
	}
	
	public TimeUnitType getTimeStampUnit()
	{
		return lookupValue(Param.TS_UNIT);
	}
	
	public double rate()
	{
		double rate = (getLastTimeUpdated() - getCreationTime())/getCounter();
		
		return rate;
	}
}
