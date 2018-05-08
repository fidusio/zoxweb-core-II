package org.zoxweb.shared.data;

import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;


@SuppressWarnings("serial")
public class ScheduleConfigDAO 
	extends TimeStampDAO 
{
	 public enum Param
	     implements GetNVConfig 
	 {
	
	     SCHEDULE(NVConfigManager.createNVConfig("schedule", "Schedule in cron or time fomart", "Schedule", true, true, NVGenericMap.class)),
	     ;
	
	     private final NVConfig nvc;
	
	     Param(NVConfig nvc) {
	         this.nvc = nvc;
	     }
	
	     @Override
	     public NVConfig getNVConfig() {
	         return nvc;
	     }
	 }
	
	 public static final NVConfigEntity NVC_CRON_CONFIG_DAO = new NVConfigEntityLocal(
	         "schedule_config_dao",
	         null,
	         ScheduleConfigDAO.class.getSimpleName(),
	         true,
	         false,
	         false,
	         false,
	         ScheduleConfigDAO.class,
	         SharedUtil.extractNVConfigs(Param.values()),
	         null,
	         false,
	         TimeStampDAO.NVC_TIME_STAMP_DAO
	 );
	
	public ScheduleConfigDAO() 
	{
		super(NVC_CRON_CONFIG_DAO);
		// TODO Auto-generated constructor stub
	}
	
	
	public NVGenericMap getSchedule()
	{
		return (NVGenericMap) lookup(Param.SCHEDULE);
	}
	
	
}
