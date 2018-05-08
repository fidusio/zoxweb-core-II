package org.zoxweb.shared.data;

import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.GetNVConfig;

import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedUtil;


@SuppressWarnings("serial")
public class ScheduleConfigDAO 
	extends TimeStampDAO 
{
	
	 public enum Param
	     implements GetNVConfig 
	 {
	
		 URL(NVConfigManager.createNVConfig("url", "URL.", "URL", true, false, false, true, String.class, FilterType.URL)),
		 ON_COMMAND(NVConfigManager.createNVConfig("on_command", "On command.", "OnCommand", true, false, false, true, String.class, null)),
		 OFF_COMMAND(NVConfigManager.createNVConfig("off_command", "Off Command.", "OffCommand", true, false, false, true, String.class, null)),
		 SCHEDULES(NVConfigManager.createNVConfigEntity("schedules", "Schedule in cron or time fomart", "Schedule", true, true, ScheduleTypeDAO.class, ArrayType.LIST)),
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
	
	
	public String getURL()
	{
		return lookupValue(Param.URL);
	}
	
	public void setURL(String url)
	{
		setValue(Param.URL, url);
	}
	
	public String getOnCommand()
	{
		return lookupValue(Param.ON_COMMAND);
	}
	
	public void setOnCommand(String onCommand)
	{
		setValue(Param.ON_COMMAND, onCommand);
	}
	
	public String getOffCommand()
	{
		return lookupValue(Param.OFF_COMMAND);
	}
	
	public void setOffCommand(String offCommand)
	{
		setValue(Param.OFF_COMMAND, offCommand);
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayValues<NVEntity> getSchedules()
	{
		return (ArrayValues<NVEntity>)lookup(Param.SCHEDULES);
	}
	
	
}
