package org.zoxweb.server.api;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.zoxweb.server.security.CryptoUtil;
import org.zoxweb.server.security.JWTProvider;
import org.zoxweb.server.security.UserIDCredentialsDAO;
import org.zoxweb.shared.api.APIAppManager;
import org.zoxweb.shared.api.APIDataStore;
import org.zoxweb.shared.api.APIException;
import org.zoxweb.shared.crypto.CryptoConst;
import org.zoxweb.shared.crypto.PasswordDAO;
import org.zoxweb.shared.data.AppDeviceDAO;
import org.zoxweb.shared.data.AppIDDAO;
import org.zoxweb.shared.data.UserIDDAO;
import org.zoxweb.shared.data.UserInfoDAO;
import org.zoxweb.shared.data.UserPreferenceDAO;
import org.zoxweb.shared.db.QueryMatchString;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.security.AccessSecurityException;
import org.zoxweb.shared.security.JWT;
import org.zoxweb.shared.security.SubjectAPIKey;
import org.zoxweb.shared.util.Const.LogicalOperator;
import org.zoxweb.shared.util.Const.RelationalOperator;
import org.zoxweb.shared.util.Const.Status;
import org.zoxweb.shared.util.MetaToken;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class APIAppManagerProvider
    implements APIAppManager {

    private volatile APIDataStore<?> dataStore;
    private HashMap<String, SubjectAPIKey> cache = new HashMap<String, SubjectAPIKey>();


    public APIAppManagerProvider() {

    }

    
    public APIDataStore<?> getAPIDataStore() {
        return dataStore;
    }

 
    public synchronized void setAPIDataStore(APIDataStore<?> dataStore)
            throws NullPointerException, IllegalArgumentException {
        this.dataStore = dataStore;
    }

    
    public SubjectAPIKey createAppDeviceDAO(AppDeviceDAO appDeviceDAO)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException{
        SharedUtil.checkIfNulls("AppDeviceDAO is null", appDeviceDAO);

        if (appDeviceDAO.getAppID() == null || appDeviceDAO.getDomainID() == null) {
            throw new IllegalArgumentException("AppID or DomainID null");
        }

        if (appDeviceDAO.getDevice() == null) {
            throw new IllegalArgumentException("Device null");
        }

        return createSubjectAPIKey(appDeviceDAO);
    }

  
    public SubjectAPIKey createSubjectAPIKey(SubjectAPIKey subjectAPIKey)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        SharedUtil.checkIfNulls("Null SubjectAPIKey", subjectAPIKey);

        if (subjectAPIKey.getSubjectID() == null) {
            subjectAPIKey.setSubjectID(UUID.randomUUID().toString());
        }

        if (subjectAPIKey.getAPISecret() == null) {
            try {
                subjectAPIKey.setAPISecret(CryptoUtil.generateKey(256, CryptoUtil.AES).getEncoded());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        if (subjectAPIKey.getStatus() == null) {
            subjectAPIKey.setStatus(Status.ACTIVE);
        }

        subjectAPIKey = dataStore.insert(subjectAPIKey);

        synchronized (cache) {
            cache.put(subjectAPIKey.getSubjectID(), subjectAPIKey);
        }

        return SubjectAPIKey.copy(subjectAPIKey);
    }

  
    public void deleteSubjectAPIKey(String subjectID)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {

        synchronized (cache) {
            cache.remove(subjectID);
        }

    }

   
    public void deleteSubjectAPIKey(SubjectAPIKey subjectAPIKey)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {

        if (subjectAPIKey != null) {
            deleteSubjectAPIKey(subjectAPIKey.getUserID());
        }
    }

    
    public SubjectAPIKey lookupSubjectAPIKey(String subjectID)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        synchronized (cache) {
            return cache.get(subjectID);
        }
    }

    @Override
    public void updateSubjectAPIKey(SubjectAPIKey subjectAPIKey)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        synchronized (cache) {
            cache.put(subjectAPIKey.getSubjectID(), subjectAPIKey);
        }

    }

   
    public JWT validateJWT(String token)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException{
        SharedUtil.checkIfNulls("Null Token", token);
        JWT jwt = null;
        try {
            jwt = CryptoUtil.parseJWT(token);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new AccessSecurityException("Invalid token");
        }

        if (jwt.getPayload().getSubjectID() == null) {
            throw new AccessSecurityException("Missing subject id");
        }

        // lookup subject id
        SubjectAPIKey subjectAPIKey = lookupSubjectAPIKey(jwt.getPayload().getSubjectID());

        if (subjectAPIKey == null) {
            throw new AccessSecurityException("Subject not found: " + jwt.getPayload().getSubjectID());
        }

        if (subjectAPIKey.getStatus() != Status.ACTIVE) {
            throw new AccessSecurityException("Invalid SubjectAPIKey: " + subjectAPIKey.getStatus());
        }

        if (subjectAPIKey instanceof AppDeviceDAO) {
            // validate domainID and AppID
            AppDeviceDAO add = (AppDeviceDAO) subjectAPIKey;
            if (!SharedStringUtil.equals(add.getDomainID(), jwt.getPayload().getDomainID(), true) ||
                    !SharedStringUtil.equals(add.getAppID(), jwt.getPayload().getAppID(), true)) {
                throw new AccessSecurityException("Invalid AppID");
            }
        }

        return JWTProvider.SINGLETON.decodeJWT(subjectAPIKey.getAPISecretAsBytes(), token);
    }

    @Override
    public void resetPassword(String subjectID)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {

    }

    @Override
    public UserIDDAO createUserIDDAO(String subjectID, String password)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {


        return null;
    }


    @Override
    public UserIDDAO createUserIDDAO(UserIDDAO userIDDAO, String password)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        SharedUtil.checkIfNulls("UserIDDAO is null.", userIDDAO);
        SharedUtil.checkIfNulls("Password is null.", password);

        password = FilterType.PASSWORD.validate(password);

        try {
            userIDDAO = dataStore.insert(userIDDAO);

            UserIDCredentialsDAO userIDCredentials = new UserIDCredentialsDAO();
            userIDCredentials.setReferenceID(userIDDAO.getReferenceID());
            userIDCredentials.setUserID(userIDDAO.getReferenceID());
            userIDCredentials.setLastStatusUpdateTimestamp(System.currentTimeMillis());
            userIDCredentials.setUserStatus(UserIDCredentialsDAO.UserStatus.ACTIVE);
            PasswordDAO passwordDAO = CryptoUtil.hashedPassword(CryptoConst.MDType.SHA_512, 0, 8196, password);
            passwordDAO.setUserID(userIDDAO.getReferenceID());
            userIDCredentials.setPassword(passwordDAO);
            dataStore.insert(userIDCredentials);
            userIDCredentials.getPassword().setReferenceID(userIDCredentials.getReferenceID());
            dataStore.update(userIDCredentials);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AccessException(e.getMessage());
        }

        return userIDDAO;
    }

    @Override
    public UserIDDAO lookupUserIDDAO(String subjectID)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public UserPreferenceDAO lookupUserPreferenceDAO(String subjectID)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public UserPreferenceDAO lookupUserPreferenceDAO(AppIDDAO appIDDAO, UserIDDAO userIDDAO)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        SharedUtil.checkIfNulls("AppIDDAO is null", appIDDAO);
        SharedUtil.checkIfNulls("UserIDDAO is null", userIDDAO);

        UserPreferenceDAO ret = null;

        List<UserPreferenceDAO> result = dataStore.search(UserPreferenceDAO.NVC_USER_PREFERENCE_DAO, null,
                new QueryMatchString(RelationalOperator.EQUAL, userIDDAO.getReferenceID(), MetaToken.USER_ID),
                LogicalOperator.AND,
                new QueryMatchString(RelationalOperator.EQUAL, appIDDAO.getReferenceID(), UserPreferenceDAO.Param.APP_ID.getNVConfig(), MetaToken.REFERENCE_ID)
        );

        if (result != null && !result.isEmpty()) {
            ret = result.get(0);
        }

        return ret;
    }

    @Override
    public void changePassword(String subjectID, String oldPassword, String newPassword)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {

    }

    @Override
    public <V extends NVEntity> V create(V nve)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {

        return null;
    }

    @Override
    public <V extends NVEntity> List<V> lookup(String subjectID, Class<V> classType)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public <V extends NVEntity> V update(V nve)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public <V extends NVEntity> boolean delete(V nve)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return false;
    }

    @Override
    public AppIDDAO lookupAppIDDAO(String domainID, String appID)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        SharedUtil.checkIfNulls("Domain ID is null", domainID);
        SharedUtil.checkIfNulls("App ID is null", appID);

        List<AppIDDAO> result = dataStore.search(AppIDDAO.NVC_APP_ID_DAO, null,
                new QueryMatchString(RelationalOperator.EQUAL, domainID, AppIDDAO.Param.DOMAIN_ID),
                LogicalOperator.AND,
                new QueryMatchString(RelationalOperator.EQUAL, appID, AppIDDAO.Param.APP_ID));

        if (result == null || result.isEmpty()) {
            throw new APIException("AppIDDAO not found");
        }

        return result.get(0);
    }

    @Override
    public SubjectAPIKey registerSubjectAPIKey(UserInfoDAO userInfoDAO, AppDeviceDAO appDeviceDAO, String username, String password)
            throws NullPointerException, IllegalArgumentException, AccessException, APIException {

        // Procedure
        // 1. Validation (Check null, validate password, etc.)
        // 2. Lookup AppIDDAO based on domain ID and app ID (if not found, throw error)
        // 3. Lookup UserIDDAO based on username (if found, throw already found error)
        // 4. Create UserIDDAO with username and UserInfoDAO
        // 5. Create Credentials with password
        // 6. Create AppDeviceDAO
        // 7. Create UserPreferenceDAO

        SharedUtil.checkIfNulls("UserInfoDAO is null", userInfoDAO);
        SharedUtil.checkIfNulls("AppDeviceDAO is null", appDeviceDAO);
        SharedUtil.checkIfNulls("AppIDDAO is null", appDeviceDAO.getAppIDDAO());

        if (SharedStringUtil.isEmpty(username) || SharedStringUtil.isEmpty(password)) {
            throw new NullPointerException("Username and/or password is null");
        }

        String domainID = appDeviceDAO.getDomainID();
        String appID = appDeviceDAO.getAppID();

        AppIDDAO appIDDAO = lookupAppIDDAO(domainID, appID);

        // Search UserIDDAO based on username (if found throw error)
        List<UserIDDAO> userIDDAOResult = dataStore.search(UserIDDAO.NVC_USER_ID_DAO, null,
                new QueryMatchString(RelationalOperator.EQUAL, username, UserIDDAO.Param.PRIMARY_EMAIL));

        if (userIDDAOResult != null && !userIDDAOResult.isEmpty()) {
            throw new APIException("UserIDDAO already exists");
        }

        UserIDDAO userIDDAO = new UserIDDAO();
        userIDDAO.setPrimaryEmail(username);
        userIDDAO.setUserInfo(userInfoDAO);
        userIDDAO = createUserIDDAO(userIDDAO, password);

        // Lookup UserPreferenceDAO based on AppIDDAO and UserIDDAO
        UserPreferenceDAO userPreferenceDAO = lookupUserPreferenceDAO(appIDDAO, userIDDAO);
        if (userPreferenceDAO == null) {
            // Does not exist, create UserPreferenceDAO
            userPreferenceDAO = new UserPreferenceDAO();
            userPreferenceDAO.setUserID(userIDDAO.getReferenceID());
            userPreferenceDAO.setAppIDDAO(appIDDAO);

            dataStore.insert(userPreferenceDAO);
        }

        // Create AppDeviceDAO
        appDeviceDAO.setUserID(userIDDAO.getReferenceID());
        appDeviceDAO = (AppDeviceDAO) createAppDeviceDAO(appDeviceDAO);

        return appDeviceDAO;
    }

}