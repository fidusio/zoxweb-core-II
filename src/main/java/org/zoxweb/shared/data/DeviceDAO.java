package org.zoxweb.shared.data;


import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.DeviceID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.SubjectID;

@SuppressWarnings("serial")
public class DeviceDAO
	extends SetNameDescriptionDAO
	implements DeviceID<String>,
			   SubjectID<String>
{
	
	
	public enum Param
	implements GetNVConfig
	{
		DEVICE_ID(NVConfigManager.createNVConfig("device_id", "Device identifier", "DeviceID", true, false, true, String.class, null)),
		SUBJECT_ID(NVConfigManager.createNVConfig("subject_id", "Subject Identifier", "SubjectID", true, false, true, String.class, null)),
		PROPERTIES(NVConfigManager.createNVConfig("properties", "Device propeties", "Properties", false, true, true, false, String[].class, null)),
		
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
	

	public static final NVConfigEntity NVC_DEVICE_DAO = new NVConfigEntityLocal(
											            "DeviceDAO",
											            null ,
											            null,
											            true,
											            false,
											            false,
											            false,
											            DeviceDAO.class,
											            SharedUtil.extractNVConfigs(Param.values()),
											            null,
											            false,
											            SetNameDescriptionDAO.NVC_NAME_DAO);
	
	
	public DeviceDAO() {
		super(NVC_DEVICE_DAO);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSubjectID() {
		// TODO Auto-generated method stub
		return lookupValue(Param.SUBJECT_ID);
	}

	@Override
	public void setSubjectID(String id) {
		// TODO Auto-generated method stub
		setValue(Param.SUBJECT_ID, id);
	}

	@Override
	public String getDeviceID() {
		// TODO Auto-generated method stub
		return lookupValue(Param.DEVICE_ID);
	}

	@Override
	public void setDeviceID(String id) {
		// TODO Auto-generated method stub
		setValue(Param.DEVICE_ID, id);
	}

	
	@SuppressWarnings("unchecked")
	public ArrayValues<GetNameValue<String>> getProperties()
	{
		return (ArrayValues<GetNameValue<String>>) lookup(Param.PROPERTIES.getNVConfig().getName());
	}
	
	/**
	 * Sets the document properties.
	 * @param properties
	 */
	public synchronized void setProperties(ArrayValues<GetNameValue<String>> properties)
	{
		getProperties().add(properties.values(), true);	
	}
}
