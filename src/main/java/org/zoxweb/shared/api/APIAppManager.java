package org.zoxweb.shared.api;

import org.zoxweb.shared.data.AppDeviceDAO;
import org.zoxweb.shared.data.UserIDDAO;
import org.zoxweb.shared.data.UserPreferenceDAO;
import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.SubjectAPIKey;
import org.zoxweb.shared.util.NVEntity;

public interface APIAppManager
{

	/**
	 * Create a userDAO, the creation requires persistence of the following:
	 * <ol>
	 * <li> UserIDDAO the user id
	 * <li> UserInfoDAO the user data addresses, cc etc
	 * <li> UserIDCredentialsDAO the password info
	 * <li> UserPreferenceDAO his/her preferences
	 * </ol>
	 * 
	 * @param userDAO
	 * @return persisted UserIDDOA
	 * @throws NullPointerException
	 * @throws IllegalArgumentException if the user already exist
	 * @throws AccessSecurityException 
	 */
	UserIDDAO createUserID(UserIDDAO userDAO, String password)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	/**
	 * Lookup a userID
	 * @param subjectID
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessSecurityException only superAdmin can do lookup
	 */
	UserIDDAO lookupUserID(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	/**
	 * Lookup a user preference DAO
	 * @param subjectID
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws AccessSecurityException
	 */
	UserPreferenceDAO lookupUserPreference(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;

	/**
	 * Update a data object
	 * @param nve
	 * @return
	 */
	<V extends NVEntity> V update(V nve);
	
	SubjectAPIKey createAppDeviceDAO(AppDeviceDAO subjectAPIKey)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	SubjectAPIKey createSubjectAPIKey(SubjectAPIKey subjectAPIKey)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void deleteSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void deleteSubjectAPIKey(SubjectAPIKey subjectAPIKey)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	SubjectAPIKey lookupSubjectAPIKey(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void updateSubjectAPIKey(SubjectAPIKey subjectAPIKey)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	JWT validateJWT(String token)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;

    APIDataStore<?> getAPIDataStore();

	void setAPIDataStore(APIDataStore<?> ds)
			throws NullPointerException, IllegalArgumentException;

	UserIDDAO createUser(String subjectID, String password)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	void resetPassword(String subjectID)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;
	
	
	void changePassword(String subjectID, String oldPassword, String newPassword)
			throws NullPointerException, IllegalArgumentException, AccessSecurityException;

}
