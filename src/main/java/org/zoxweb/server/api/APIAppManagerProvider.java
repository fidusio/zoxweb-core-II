package org.zoxweb.server.api;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

import org.zoxweb.server.security.CryptoUtil;
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
	
	private HashMap<String, SubjectAPIKey> cache = new HashMap<>();
	
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
		SharedUtil.checkIfNulls("Null SubjectAPIKey", sak);
		if (sak.getSubjectID() == null)
		{
			// the uuid if null
			sak.setSubjectID(UUID.randomUUID().toString());
		}
		
		if (sak.getAPIKey() == null)
		{
			sak.setAPIKey(UUID.randomUUID().toString());
		}
		if (sak.getAPISecret() == null)
		{
			try {
				sak.setAPISecret(CryptoUtil.generateKey(256, CryptoUtil.AES).getEncoded());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cache.put(sak.getSubjectID(), sak);
		
		// TODO Auto-generated method stub
		return sak;
	}

	@Override
	public void deleteSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		cache.remove(subjectID);

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
		cache.put(sak.getSubjectID(), sak);

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
