package org.zoxweb.shared.security;

import org.zoxweb.shared.api.APIDataStore;
import org.zoxweb.shared.crypto.EncryptedKeyDAO;
import org.zoxweb.shared.data.UserIDDAO;
import org.zoxweb.shared.util.NVEntity;

public interface KeyMaker 
{
	EncryptedKeyDAO createUserIDKey(UserIDDAO userID, byte[]key)
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	EncryptedKeyDAO createNVEntityKey(APIDataStore<?> dataStore, NVEntity nve, byte[] key)
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	byte[] getKey(APIDataStore<?> dataStore, final byte[] key, String ...chainedIDs) 
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	byte[] getMasterKey()
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	EncryptedKeyDAO lookupEncryptedKeyDOA(APIDataStore<?> dataStore, NVEntity nve)
		throws NullPointerException, IllegalArgumentException, AccessException;
	
	EncryptedKeyDAO lookupEncryptedKeyDOA(APIDataStore<?> dataStore, String dataRefID)
		throws NullPointerException, IllegalArgumentException, AccessException;
	
}
