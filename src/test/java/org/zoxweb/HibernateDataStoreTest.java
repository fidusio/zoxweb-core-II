package org.zoxweb;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zoxweb.server.db.HibernateDataStore;
import org.zoxweb.shared.api.APIConfigInfo;
import org.zoxweb.shared.api.APIConfigInfoDAO;
import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.util.NVPair;

public class HibernateDataStoreTest {

    private static HibernateDataStore dataStore;
    private static final String RESOURCE_FILE = "hibernate.cfg.xml";

    @BeforeClass
    public static void setUp() {
//    	try
    	{
    		APIConfigInfo configInfo = new APIConfigInfoDAO();
    		configInfo.getConfigParameters().add(new NVPair("resource", RESOURCE_FILE));

    		dataStore = new HibernateDataStore(configInfo);
    		dataStore.connect();
    	}
//    	catch(Throwable e)
//    	{
//    		e.printStackTrace();
//    		
//    	}
    	System.out.println("Setup done");
    }

    @AfterClass
    public static void tearDown() {
        dataStore.close();
    }

    @Test
    public void testInsert() {
        AddressDAO addressDAO = new AddressDAO();
        addressDAO.setCity("Los Angeles");
        addressDAO.setStateOrProvince("CA");
        addressDAO.setCountry("USA");

        addressDAO = (AddressDAO) dataStore.insert(addressDAO);
        Assert.assertNotNull(addressDAO);
        Assert.assertNotNull(addressDAO.getReferenceID());
    }

    @Test
    public void testRead() {
        AddressDAO addressDAO = new AddressDAO();
        addressDAO.setCity("Los Angeles");
        addressDAO.setStateOrProvince("CA");
        addressDAO.setCountry("USA");
        addressDAO = (AddressDAO) dataStore.insert(addressDAO);

        AddressDAO result = (AddressDAO) dataStore.lookupByReferenceID(AddressDAO.class.getName(), addressDAO.getReferenceID());
        Assert.assertNotNull(result);

        System.out.println("Result: " + result);
    }

    @Test
    public void testUpdate() {
        AddressDAO addressDAO = new AddressDAO();
        addressDAO.setCity("Los Angeles");
        addressDAO.setStateOrProvince("CA");
        addressDAO.setCountry("USA");
        addressDAO = (AddressDAO) dataStore.insert(addressDAO);

        System.out.println("Original NVE: " + dataStore.lookupByReferenceID(AddressDAO.class.getName(), addressDAO.getReferenceID()));

        addressDAO.setCity("New York");
        addressDAO.setStateOrProvince("NY");
        addressDAO = (AddressDAO) dataStore.update(addressDAO);

        System.out.println("Updated NVE: " + dataStore.lookupByReferenceID(AddressDAO.class.getName(), addressDAO.getReferenceID()));
    }

    @Test
    public void testDelete() {
        AddressDAO addressDAO = new AddressDAO();
        addressDAO.setCity("Los Angeles");
        addressDAO.setStateOrProvince("CA");
        addressDAO.setCountry("USA");
        addressDAO = (AddressDAO) dataStore.insert(addressDAO);

        dataStore.delete(addressDAO, true);
        addressDAO = (AddressDAO) dataStore.lookupByReferenceID(AddressDAO.class.getName(), addressDAO.getReferenceID());
        Assert.assertNull(addressDAO);
    }

}