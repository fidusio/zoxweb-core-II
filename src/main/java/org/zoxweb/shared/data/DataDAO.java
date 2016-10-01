/*
 * Copyright (c) 2012-2016 ZoxWeb.com LLC.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.shared.data;




import org.zoxweb.shared.data.DataConst.DataParam;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SetCanonicalID;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;


/**
 * This class is used to define parameters used by devices 
 * which transmit messages. All message classes will extend
 * this class in order to obtain the applicable message parameters.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class DataDAO extends TimeStampDAO
implements SetCanonicalID
{
	
	//private static final NVConfig NVC_TIME_STAMP =  NVConfigManager.createNVConfig("time_stamp", "This is the time stamp for when the message was generated.","TimeStamp",true, false, Long.class);
	private static final NVConfig NVC_DATA =  NVConfigManager.createNVConfig("data", "This is the raw message in byte array format.","Data",true, false, byte[].class);
	private static final NVConfig NVC_SOURCE_ID =  NVConfigManager.createNVConfig("source_id", "This is the source ID to identify the device within the system.","SourceID",true, false, String.class);
	//private static final NVConfig NVC_SYSTEM_ID =  NVConfigManager.createNVConfig("system_id", "This is the system ID is the container of the source ID.","SystemID",true, false, String.class);
	
		
	
	public static final NVConfigEntity NVC_DATA_DAO = new NVConfigEntityLocal("data_dao", null , "DataDAO", true, false, false, false, DataDAO.class, SharedUtil.toNVConfigList(NVC_DATA, NVC_SOURCE_ID, DataParam.CANONICAL_ID.getNVConfig()), null, false, TimeStampDAO.NVC_TIME_STAMP_DAO);
	
	/**
	 * This constructor creates a MessageBase object
	 * based on a defined list and a set value for 
	 * the time stamp.
	 * @param list
	 */
	protected DataDAO(NVConfigEntity nvce) 
	{
		super(nvce);
		// TODO Auto-generated constructor stub
		setCreationTime(System.currentTimeMillis());
	}
	
	/**
	 * This is a default constructor that creates a
	 * MessageBase object.
	 */
	public DataDAO() 
	{
		super(NVC_DATA_DAO);
		setCreationTime(System.currentTimeMillis());
	}
	
	
	/**
	 * This method returns the system ID.
	 * @return
	 */
	public String getSystemID() 
	{
		return getCanonicalID();
	}

	/**
	 * This method sets the system ID.
	 * @param systemID
	 */
	public void setSystemID(String systemID) 
	{
		setCanonicalID(systemID);
	}

	public void setData(String data)
	{
		if(data != null)
		{
			setData(SharedStringUtil.getBytes(data));
		}
		else
		{
			setData((byte[])null);
		}
	}
	
	/**
	 * This method sets the data.
	 * @param dataArray
	 */
	public void setData(byte[] data)
	{
		setValue(NVC_DATA, data);
	}
	
	/**
	 * This method returns the data.
	 * @return
	 */
	public byte[] getData()
	{
		return lookupValue(NVC_DATA);
	}
	
	/**
	 * This method sets the source ID.
	 * @param id
	 */
	public void setSourceID(String sourceID)
	{
		setValue(NVC_SOURCE_ID, sourceID);
	}
	
	/**
	 * This method returns the source ID, the source id the uuid of a the device that have generated the message.
	 * <br> The list of possible device type that can generate such message are:
	 * <ul>
	 * <li> GPS module.
	 * <li> Temperature sensor.
	 * <li> Accelerometer module.
	 * <li> Gyroscope sensor.
	 * <li> or any device that can be integrated or supported by rubus pi project.
	 * </ul>
	 * @return
	 */
	public String getSourceID()
	{
		return lookupValue(NVC_SOURCE_ID);
	}

	@Override
	public String toCanonicalID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCanonicalID() 
	{
		// TODO Auto-generated method stub
		return lookupValue(DataParam.CANONICAL_ID);
	}

	@Override
	public void setCanonicalID(String id)
	{
		setValue(DataParam.CANONICAL_ID, id);
	}
	

}
