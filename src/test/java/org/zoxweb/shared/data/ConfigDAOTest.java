package org.zoxweb.shared.data;

import java.util.List;

import org.zoxweb.server.shiro.ZWEhCacheManager;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.util.NVInt;

public class ConfigDAOTest 
{
	public static void main(String ...args)
	{
		try
		{
			ConfigDAO cd = new ConfigDAO();
			cd.setName("SimpleTest-1");
			cd.setBeanClassName(ZWEhCacheManager.class);
			cd.getProperties().add(new NVInt("port", 8080));
			
			String json = GSONUtil.toJSON(cd, true, false, true, null);
			cd.setName("SimpleTest-2");
			json += GSONUtil.toJSON(cd, true, false, true, null);
			System.out.println(json);
			
			
			List<ConfigDAO> results = GSONUtil.fromJSONs(json,null, ConfigDAO.class);
			System.out.println(results);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
