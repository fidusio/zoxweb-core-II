package org.zoxweb.shared.api;

import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.security.JWTToken;
import org.zoxweb.shared.util.CRUD;
import org.zoxweb.shared.util.Const.LogicalOperator;
import org.zoxweb.shared.util.NVBase;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.NVPair;

public interface APISecurityManager<S> 
{
	
	Object encryptValue(APIDataStore<?> dataStore, NVEntity container, NVConfig nvc, NVBase<?> nvb, byte msKey[])
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	Object decryptValue(APIDataStore<?> dataStore, NVEntity container, NVBase<?> nvb, Object value, byte msKey[])
			throws NullPointerException, IllegalArgumentException, AccessException;
	
	 String decryptValue(APIDataStore<?> dataStore, NVEntity container, NVPair nvp, byte msKey[])
				throws NullPointerException, IllegalArgumentException, AccessException;
	 
	 Object decryptValue(String userID, APIDataStore<?> dataStore, NVEntity container, Object value, byte msKey[])
				throws NullPointerException, IllegalArgumentException, AccessException;
	 
	 NVEntity decryptValues(APIDataStore<?> dataStore, NVEntity container, byte msKey[])
				throws NullPointerException, IllegalArgumentException, AccessException;
	 
	 void associateNVEntityToSubjectUserID(NVEntity nve, String userID);
	 
	 String currentSubjectID()
			 throws AccessException;
	 
	 String currentUserID()
			 throws AccessException;
	 
	 String currentDomainID()
			 throws AccessException;
	 
	 String currentAppID()
			 throws AccessException;
	 
	 String currentJWTSubjectID()
			 throws AccessException;
	 
	 S getDaemonSubject();
	 void setDaemonSubject(S subject);
	 
	 boolean isNVEntityAccessible(NVEntity nve, CRUD ...permissions);
	 
	 boolean isNVEntityAccessible(LogicalOperator lo, NVEntity nve, CRUD ...permissions);
	 
	 boolean isNVEntityAccessible(String nveRefID, String nveUserID, CRUD ...permissions);
	 
	 
	 String checkNVEntityAccess(NVEntity nve, CRUD ...permissions)
				throws NullPointerException, IllegalArgumentException, AccessException;
	 String checkNVEntityAccess(LogicalOperator lo, NVEntity nve, CRUD ...permissions)
				throws NullPointerException, IllegalArgumentException, AccessException;
	 String checkNVEntityAccess(String nveRefID, String nveUserID, CRUD ...permissions)
				throws NullPointerException, IllegalArgumentException, AccessException;
	 
	 void checkSubject(String subjectID)
				throws NullPointerException, AccessException;
	 
	 String checkNVEntityAccess(String nveRefID, CRUD ...permissions)
				throws NullPointerException, IllegalArgumentException, AccessException;
	 void checkPermissions(String ...permissions)
			 throws NullPointerException, AccessException;
	 
	 S login(String subjectID, String credentials, String domainID, String appID, boolean autoLogin);
	 S login(JWTToken jwtToken);
	 
	 void logout();
	 
}
