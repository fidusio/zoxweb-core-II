package org.zoxweb.server.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.shared.api.APIBatchResult;
import org.zoxweb.shared.api.APIConfigInfo;
import org.zoxweb.shared.api.APIDataStore;
import org.zoxweb.shared.api.APIException;
import org.zoxweb.shared.api.APIExceptionHandler;
import org.zoxweb.shared.api.APISearchResult;
import org.zoxweb.shared.db.QueryMarker;
import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.util.DynamicEnumMap;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedUtil;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("serial")
public class HibernateDataStore
        implements APIDataStore<SessionFactory>{

    private APIConfigInfo configInfo;
    private SessionFactory sessionFactory;
    private String name =  "HibernateDataStore";
    private String description = "Hibernate based data store";

    public static final String RESOURCE = "resource";

    public HibernateDataStore(APIConfigInfo configInfo) {
        this();
        setAPIConfigInfo(configInfo);
    }

    public HibernateDataStore() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void setDescription(String str) {
        this.description = str;
    }

    @Override
    public APIConfigInfo getAPIConfigInfo() {
        return configInfo;
    }

    @Override
    public void setAPIConfigInfo(APIConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    @Override
    public String toCanonicalID() {
        return null;
    }

    @Override
    public String getStoreName() {
        return null;
    }

    @Override
    public Set<String> getStoreTables() {
        return null;
    }

    @Override
    public SessionFactory connect() throws APIException {
        if (sessionFactory == null) {
            synchronized (this) {
                if (sessionFactory == null) {
                    if (configInfo == null || configInfo.getConfigParameters() == null) {
                        throw new NullPointerException("Missing configuration info.");
                    }

                    String resource = SharedUtil.lookupValue(configInfo.getConfigParameters().get(RESOURCE));

                    SharedUtil.checkIfNulls("Resource (e.g. hibernate.cfg.xml) is null.", resource);

                    Configuration configuration = new Configuration();

                    try {
                            sessionFactory = configuration.configure(resource).buildSessionFactory();
                    } catch (HibernateException e) {
                        throw new APIException("Connect failed: " + e.getMessage());
                    }
                }
            }
        }

        return sessionFactory;
    }

    @Override
    public void close() throws APIException {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
                sessionFactory = null;
            } catch (HibernateException e) {
                e.printStackTrace();
                throw new APIException("Disconnect failed: " + e.getMessage());
            }
        }
    }

    @Override
    public SessionFactory newConnection() throws APIException {
        return null;
    }

    @Override
    public List search(NVConfigEntity nvce, List fieldNames, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public boolean isProviderActive() {
        return false;
    }

    @Override
    public APIExceptionHandler getAPIExceptionHandler() {
        return null;
    }

    @Override
    public void setAPIExceptionHandler(APIExceptionHandler exceptionHandler) {

    }

    @Override
    public List search(String className, List fieldNames, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public Object lookupProperty(GetName propertyName) {
        return null;
    }

    @Override
    public long lastTimeAccessed() {
        return 0;
    }

    @Override
    public long inactivityDuration() {
        return 0;
    }

    @Override
    public APISearchResult batchSearch(NVConfigEntity nvce, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public APISearchResult batchSearch(String className, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public APIBatchResult nextBatch(APISearchResult results, int startIndex, int batchSize) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public List userSearch(String userID, NVConfigEntity nvce, List fieldNames, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public List userSearch(String userID, String className, List fieldNames, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public List searchByID(NVConfigEntity nvce, String... ids) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public List searchByID(String className, String... ids) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public List userSearchByID(String userID, NVConfigEntity nvce, String... ids) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

    @Override
    public NVEntity insert(NVEntity nve) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        SharedUtil.checkIfNulls("NVEntity is null.", nve);

        Session session = null;
        Transaction transaction = null;

        if (nve.getReferenceID() == null) 
        {
            nve.setReferenceID(UUID.randomUUID().toString());
        }
        
        if(nve.getGlobalID() == null)
        {
        	 nve.setGlobalID(UUID.randomUUID().toString());
        }

        try 
        {
        	
        	session = connect().openSession();
            transaction = session.beginTransaction();
            session.save(nve);
            transaction.commit();
        } 
        catch (HibernateException e) 
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            throw new APIException("Insert failed: " + e.getMessage());
        } 
        finally 
        {
        	IOUtil.close(session);
        }

        return nve;
    }

    @Override
    public boolean delete(NVEntity nve, boolean withReference) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        SharedUtil.checkIfNulls("NVEntity is null.", nve);

        Session session = null; 
        Transaction transaction = null;

        try 
        {
        	session = connect().openSession();
            transaction = session.beginTransaction();
            session.delete(nve);
            transaction.commit();
        }
        catch (HibernateException e) 
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            throw new APIException("Delete failed: " + e.getMessage());
        } 
        finally 
        {
        	IOUtil.close(session);
        }

        return true;
    }

    @Override
    public boolean delete(NVConfigEntity nvce, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return false;
    }

    @Override
    public NVEntity update(NVEntity nve) throws NullPointerException, IllegalArgumentException, APIException {
        SharedUtil.checkIfNulls("NVEntity is null.", nve);

        Session session = null;
        Transaction transaction = null;

        try 
        {
        	session = connect().openSession();
            transaction = session.beginTransaction();
            session.update(nve);
            transaction.commit();
        } 
        catch (HibernateException e) 
        {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new APIException("Updated failed: " + e.getMessage());
        } finally 
        {
        	IOUtil.close(session);
        }

        return nve;
    }

    @Override
    public NVEntity patch(NVEntity nve, boolean updateTS, boolean sync, boolean updateRefOnly, boolean includeParam, String... nvConfigNames) throws NullPointerException, IllegalArgumentException, APIException {
        return null;
    }

    @Override
    public long countMatch(NVConfigEntity nvce, QueryMarker... queryCriteria) throws NullPointerException, IllegalArgumentException, APIException {
        return 0;
    }

    @Override
    public Object lookupByReferenceID(String metaTypeName, Object objectId) {
        SharedUtil.checkIfNulls("Meta type name is null.", metaTypeName);
        SharedUtil.checkIfNulls("Reference ID is null.", objectId);

        if (!(objectId instanceof String)) {
            throw new IllegalArgumentException("Invalid reference ID class type: " + objectId.getClass() + ", expected: " + String.class);
        }

        String referenceID = (String) objectId;

        Session session = null;

        Transaction transaction = null;
        NVEntity nve;

        try {
        	session = connect().openSession();
            session.setDefaultReadOnly(true);
            transaction = session.beginTransaction();
            nve = (NVEntity) session.get(metaTypeName, referenceID);
            transaction.commit();
        } catch (HibernateException e)
        {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
            throw new APIException("Lookup failed: " + e.getMessage());
        } 
        finally 
        {
        	IOUtil.close(session);
        }

        return nve;
    }

    @Override
    public Object lookupByReferenceID(String metaTypeName, Object objectId, Object projection) {
        return null;
    }

    @Override
    public DynamicEnumMap insertDynamicEnumMap(DynamicEnumMap dynamicEnumMap) throws NullPointerException, IllegalArgumentException, APIException {
        return null;
    }

    @Override
    public DynamicEnumMap updateDynamicEnumMap(DynamicEnumMap dynamicEnumMap) throws NullPointerException, IllegalArgumentException, APIException {
        return null;
    }

    @Override
    public DynamicEnumMap searchDynamicEnumMapByName(String name) throws NullPointerException, IllegalArgumentException, APIException {
        return null;
    }

    @Override
    public void deleteDynamicEnumMap(String name) throws NullPointerException, IllegalArgumentException, APIException {

    }

    @Override
    public List<DynamicEnumMap> getAllDynamicEnumMap(String domainID, String userID) throws NullPointerException, IllegalArgumentException, AccessException, APIException {
        return null;
    }

}