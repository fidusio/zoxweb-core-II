package org.zoxweb.shared.api;

import org.zoxweb.shared.data.AppDeviceDAO;
import org.zoxweb.shared.data.UserIDDAO;
import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.SubjectAPIKey;

public interface APIAppManager
{
	
	SubjectAPIKey createAppDeviceDAO(AppDeviceDAO sak)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	SubjectAPIKey createSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void deleteSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void deleteSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	SubjectAPIKey lookupSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void updateSubjectAPIKey(SubjectAPIKey sak)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	JWT validateJWT(String token)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void setAPIDataStore(APIDataStore<?> ds)
			throws NullPointerException, IllegalArgumentException;
	
	APIDataStore<?> getAPIDataStore();
	
	
	UserIDDAO createUser(String subjectID, String password)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void resetPassword(String subjectID, String oldPasswordToken, String newPassword)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	
}
