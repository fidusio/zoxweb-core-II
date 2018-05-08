package org.zoxweb.shared.data;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.util.GSONUtil;

public class ScheduleConfigTest {
	
	public static void main(String ...args)
	{
		try
		{
			int index = 0;
			String filename = args[index++];
			ScheduleConfigDAO config = GSONUtil.fromJSON(IOUtil.inputStreamToString(filename), ScheduleConfigDAO.class);
			System.out.println(config.getSchedule());
			String json = GSONUtil.toJSON(config, true, false, false);
			System.out.println(json);
			config = GSONUtil.fromJSON(json, ScheduleConfigDAO.class);
			System.out.println(config.getSchedule());
			System.out.println(config.getSchedule().getClass());
			System.out.println(config.getSchedule().get("oprerating_schedules").getClass());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
