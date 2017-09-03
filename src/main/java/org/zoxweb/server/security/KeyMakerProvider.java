package org.zoxweb.server.security;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;

import java.util.HashMap;
import java.util.List;


import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.zoxweb.server.security.CryptoUtil;
import org.zoxweb.shared.api.APIDataStore;
import org.zoxweb.shared.crypto.EncryptedKeyDAO;
import org.zoxweb.shared.crypto.KeyLockType;
import org.zoxweb.shared.data.UserIDDAO;
import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.security.KeyMaker;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedUtil;

public final class KeyMakerProvider
implements KeyMaker
{
	
	public final static KeyMakerProvider SINGLETON = new KeyMakerProvider();
	private static final transient Logger log = Logger.getLogger("KeyMakerProvider");
	
	private volatile SecretKey masterKey = null;
	private HashMap<String, EncryptedKeyDAO> keyMap = new HashMap<String, EncryptedKeyDAO>();
	
	
	private KeyMakerProvider()
	{
		//log.info("key maker created");
	}
	
	
	
	public final synchronized void setMasterKey(KeyStore keystore, String alias, String aliasPassword) 
	{
		
		try
		{
			if (!keystore.containsAlias(alias))
			{
				throw new IllegalArgumentException("Alias for key not found");
			}
			
			masterKey = (SecretKey) keystore.getKey(alias, aliasPassword.toCharArray());
			log.info("MK loaded");
		}
		catch(UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e)
		{
			throw new AccessException(e.getMessage());
		}
		
	
	}
	
	public final byte[] getMasterKey()
			throws NullPointerException,
				   IllegalArgumentException,
				   AccessException
	{
		if (masterKey == null)
		{
			throw new AccessException("MasterKey not set");
//			synchronized(this)
//			{
//				if (masterKey == null)
//				{
//					try
//					{
//						File keyStoreFile = ApplicationConfigManager.SINGLETON.locateFile(null, "key_store");
//						if (keyStoreFile == null || !keyStoreFile.isFile())
//						{
//							throw new IOException("cache dir " + keyStoreFile + " is not a directory");
//						}
//						masterKey = (SecretKey) CryptoUtil.getKeyFromKeyStore(new FileInputStream(keyStoreFile),
//															 	  CryptoUtil.KEY_STORE_TYPE,
//															 	  ApplicationConfigManager.SINGLETON.loadDefault().lookupValue("key_store_password"), 
//															 	  ApplicationConfigManager.SINGLETON.loadDefault().lookupValue("mk_alias"),
//															 	  ApplicationConfigManager.SINGLETON.loadDefault().lookupValue("mk_alias_password"));
//						log.info("MK loaded");
//					}
//					catch(IOException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | CertificateException e)
//					{
//						throw new AccessException(e.getMessage());
//					}
//				}
//			}
		}
		
		return masterKey.getEncoded();
	}
	
	public EncryptedKeyDAO createUserIDKey(UserIDDAO userID, byte[]key)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		SharedUtil.checkIfNulls("User ID is null.", userID, key);
		
		if (userID.getUserID() == null)
		{
			throw new IllegalArgumentException("Get user ID is null.");
		}
		
		EncryptedKeyDAO ekd = null;
		try
		{
			ekd = CryptoUtil.createEncryptedKeyDAO(key);
		}
		catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException  e)
		{
			throw new AccessException(e.getMessage());
		}
		ekd.setObjectReference(userID);
		ekd.setKeyLockType(KeyLockType.USER_ID);
		ekd.setUserID(userID.getReferenceID());
		return ekd;
	}
	
	
	public EncryptedKeyDAO createNVEntityKey(APIDataStore<?> dataStore, NVEntity nve, byte[] key)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		SharedUtil.checkIfNulls("User ID is null.", nve, key);
		if (nve.getUserID() == null)
		{
			throw new IllegalArgumentException("Get user ID is null.");
		}
		
		EncryptedKeyDAO ekd = lookupEncryptedKeyDOA(dataStore, nve);
		try
		{	
			if (ekd == null)
			{
				ekd = CryptoUtil.createEncryptedKeyDAO(key);
				ekd.setObjectReference(nve);
				ekd.setKeyLockType(KeyLockType.USER_ID);
				ekd.setUserID(nve.getUserID());
				dataStore.insert(ekd);
			}
		}
		catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e)
		{
			throw new AccessException(e.getMessage());
		}
		return ekd;
	}
	
	public final  byte[] getKey(APIDataStore<?> dataStore, final byte[] key, String ...chainedIDs) 
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		SharedUtil.checkIfNulls("Null decryption key parameters", dataStore, chainedIDs);
		
		byte[] tempKey = key != null ? key : getMasterKey();
		
		for (String id : chainedIDs)
		{
			try 
			{
				
				EncryptedKeyDAO ekd = lookupEncryptedKeyDOA(dataStore, id);
				
				if (ekd == null)
				{

				}
				
				
				tempKey = CryptoUtil.decryptEncryptedDAO(ekd, tempKey);
			} 
			catch ( InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | SignatureException  e)
			{
				e.printStackTrace();
				throw new AccessException(e.getMessage());
			}
			
		}
		
		return tempKey;
	}
	
	
	
	public final EncryptedKeyDAO lookupEncryptedKeyDOA(APIDataStore<?> dataStore, NVEntity nve)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		SharedUtil.checkIfNulls("Null parameters", dataStore, nve);
		
		return lookupEncryptedKeyDOA(dataStore, nve.getReferenceID());
	}
	
	public synchronized final EncryptedKeyDAO lookupEncryptedKeyDOA(APIDataStore<?> dataStore, String dataRefID)
			throws NullPointerException, IllegalArgumentException, AccessException
	{
		SharedUtil.checkIfNulls("Null parameters", dataStore, dataRefID);
		EncryptedKeyDAO ekd = keyMap.get(dataRefID);
		
		if (ekd == null)
		{
			List<EncryptedKeyDAO> keyMatches = dataStore.searchByID(EncryptedKeyDAO.NVCE_ENCRYPTED_KEY_DAO, dataRefID);
			if (keyMatches == null || keyMatches.size() != 1)
			{
				return null;
			}
			ekd = keyMatches.get(0);
			keyMap.put(dataRefID, ekd);
		}
		
		return ekd;
	}
}
