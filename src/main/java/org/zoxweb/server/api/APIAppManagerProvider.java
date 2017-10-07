package org.zoxweb.server.api;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.zoxweb.server.security.CryptoUtil;
import org.zoxweb.server.security.JWTProvider;
import org.zoxweb.shared.api.APIAppManager;
import org.zoxweb.shared.api.APIDataStore;
import org.zoxweb.shared.data.AppDeviceDAO;
import org.zoxweb.shared.data.UserIDDAO;
import org.zoxweb.shared.data.UserInfoDAO;
import org.zoxweb.shared.data.UserPreferenceDAO;
import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.SubjectAPIKey;
import org.zoxweb.shared.util.Const.Status;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class APIAppManagerProvider
    implements APIAppManager
{
	
	private volatile APIDataStore<?> dataStore;
	
	private HashMap<String, SubjectAPIKey> cache = new HashMap<>();
	
	
	
	@Override
	public SubjectAPIKey createAppDeviceDAO(AppDeviceDAO add)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
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
		
		return createSubjectAPIKey(add);
	}
	
	@Override
	public SubjectAPIKey createSubjectAPIKey(SubjectAPIKey subjectAPIKey)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
	{
		SharedUtil.checkIfNulls("Null SubjectAPIKey", subjectAPIKey);

		if (subjectAPIKey.getSubjectID() == null)
		{
			// the uuid if null
            subjectAPIKey.setSubjectID(UUID.randomUUID().toString());
		}
		
		if (subjectAPIKey.getAPIKey() == null)
		{
            subjectAPIKey.setAPIKey(UUID.randomUUID().toString());
		}

		if (subjectAPIKey.getAPISecret() == null)
		{
			try {
                subjectAPIKey.setAPISecret(CryptoUtil.generateKey(256, CryptoUtil.AES).getEncoded());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		
		if (subjectAPIKey.getStatus() == null)
		{
            subjectAPIKey.setStatus(Status.ACTIVE);
		}
		
		synchronized(cache)
		{
			cache.put(subjectAPIKey.getSubjectID(), subjectAPIKey);
		}

		return subjectAPIKey;
	}

	@Override
	public void deleteSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
	{
		synchronized(cache)
		{
			cache.remove(subjectID);
		}

	}

	@Override
	public void deleteSubjectAPIKey(SubjectAPIKey subjectAPIKey)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
	{
		if (subjectAPIKey != null)
		{
			deleteSubjectAPIKey(subjectAPIKey.getUserID());
		}
	}

	@Override
	public SubjectAPIKey lookupSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException 
	{
		synchronized(cache)
		{
			return cache.get(subjectID);
		}
	}

	@Override
	public void updateSubjectAPIKey(SubjectAPIKey subjectAPIKey)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
	{
		synchronized(cache)
		{
			cache.put(subjectAPIKey.getSubjectID(), subjectAPIKey);
		}

	}

	@Override
	public JWT validateJWT(String token)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
	{
		SharedUtil.checkIfNulls("Null Token", token);
		JWT jwt = null;
		try 
		{
			jwt = CryptoUtil.parseJWT(token);
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new AccessSecurityException("Invalid token");
		}
		
		if (jwt.getPayload().getSubjectID() == null)
		{
			throw new AccessSecurityException("Missing subject id");
		}
		
		// lookup subject id
		SubjectAPIKey subjectAPIKey = lookupSubjectAPIKey(jwt.getPayload().getSubjectID());

		if (subjectAPIKey == null)
		{
			throw new AccessSecurityException("Subject Not found: " + jwt.getPayload().getSubjectID());
		}
		
		if (subjectAPIKey.getStatus() != Status.ACTIVE)
		{
			throw new AccessSecurityException("Invalid SubjectAPIKey: " + subjectAPIKey.getStatus());
		}
		
		if (subjectAPIKey instanceof AppDeviceDAO)
		{
			// validate domainID and AppID
			AppDeviceDAO add = (AppDeviceDAO) subjectAPIKey;
			if (!SharedStringUtil.equals(add.getDomainID(), jwt.getPayload().getDomainID(), true) ||
			    !SharedStringUtil.equals(add.getAppID(), jwt.getPayload().getAppID(), true))
			{
				throw new AccessSecurityException("Invalid AppID");
			}
		}
		
		return JWTProvider.SINGLETON.decodeJWT(subjectAPIKey.getAPISecretAsBytes(), token);
	}

    @Override
    public APIDataStore<?> getAPIDataStore()
    {
        return dataStore;
    }

	@Override
	public synchronized void setAPIDataStore(APIDataStore<?> dataStore)
			throws NullPointerException, IllegalArgumentException
	{
		this.dataStore = dataStore;
	}

	@Override
	public UserIDDAO createUser(String subjectID, String password)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
	{
		return null;
	}

	@Override
	public void resetPassword(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException 
	{

	}

	@Override
	public UserIDDAO createUserID(UserIDDAO userDAO, String password)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
    {
		return null;
	}

	@Override
	public UserIDDAO lookupUserID(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
    {
		return null;
	}

	@Override
	public UserPreferenceDAO lookupUserPreference(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException
    {
		return null;
	}

	@Override
	public void changePassword(String subjectID, String oldPassword, String newPassword)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException {

	}

    @Override
    public <V extends NVEntity> V create(V nve)
    {
        return null;
    }

    @Override
    public  <V extends NVEntity> List<V> lookup(String subjectID, Class<V> classType)
    {
	    return null;
    }

    @Override
    public <V extends NVEntity> V update(V nve)
    {
        return null;
    }

    @Override
    public <V extends NVEntity> boolean delete(V nve)
    {
	    return false;
    }

	@Override
	public SubjectAPIKey registerSubjectAPIKey(UserInfoDAO userInfoDAO, AppDeviceDAO appDeviceDAO, String token)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException {
		// TODO Auto-generated method stub
		return null;
	}


}