package org.zoxweb.shared.api;

import org.zoxweb.shared.data.AppDeviceDAO;
import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.SubjectAPIKey;

public interface APIAppManager
{
	
	SubjectAPIKey createAppDeviceDAO(AppDeviceDAO sak)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	SubjectAPIKey createSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	void deleteSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	void deleteSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	SubjectAPIKey lookupSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	void updateSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	JWT validateJWT(String token)
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	void setAPIDataStore(APIDataStore<?> ds)
			throws NullPointerException, IllegalArgumentException;
	
	APIDataStore<?> getAPIDataStore();
	
}
