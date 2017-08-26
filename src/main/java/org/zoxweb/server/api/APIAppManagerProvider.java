package org.zoxweb.server.api;

import org.zoxweb.shared.api.APIAppManager;
import org.zoxweb.shared.api.APIDataStore;
import org.zoxweb.shared.data.AppDeviceDAO;
import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.SubjectAPIKey;
import org.zoxweb.shared.util.SharedUtil;

public class APIAppManagerProvider
implements APIAppManager
{

	private volatile APIDataStore<?> ds;
	
	@Override
	public SubjectAPIKey createAppDeviceDAO(AppDeviceDAO add)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		SharedUtil.checkIfNulls("Null AppDeviceDAO", add);
		if (add.getAppID() == null || add.getDomainID() == null)
		{
			throw new IllegalArgumentException("AppID or DomainID null");
		}
		
		if (add.getDevice() == null)
		{
			throw new IllegalArgumentException("Device null");
		}
		
		// TODO Auto-generated method stub
		return createSubjectAPIKey(add);
	}
	
	@Override
	public SubjectAPIKey createSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public SubjectAPIKey lookupSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public JWT validateJWT(String token)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void setAPIDataStore(APIDataStore<?> ds)
			throws NullPointerException, IllegalArgumentException
	{
		// TODO Auto-generated method stub
		this.ds = ds;
	}

	@Override
	public APIDataStore<?> getAPIDataStore()
	{
		// TODO Auto-generated method stub
		return ds;
	}

	

}
