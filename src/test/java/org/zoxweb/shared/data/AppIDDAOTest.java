package org.zoxweb.shared.data;

import org.junit.Test;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.util.SharedBase64.Base64Type;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 7/3/17
 */
public class AppIDDAOTest {

    @Test
    public void testJSON() throws IOException {
        AppIDDAO appIDDAO = new AppIDDAO("zoxweb.org", "zoxweb");

        System.out.println(GSONUtil.toJSON(appIDDAO, true, false, false, Base64Type.URL));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testInvalidSetAppID() {
        AppIDDAO appIDDAO1 = new AppIDDAO();
        appIDDAO1.setAppID("zoxweb.org");
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testInvalidSetDomainID() {
        AppIDDAO appIDDAO1 = new AppIDDAO();
        appIDDAO1.setDomainID("zoxweb");
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testInvalidSetSubjectID() {
        AppIDDAO appIDDAO1 = new AppIDDAO();
        appIDDAO1.setSubjectID("zoxweb");
    }

    @Test
    public void testEquals() {
        AppIDDAO appIDDAO1 = new AppIDDAO("zoxweb.org", "zoxweb");
        AppIDDAO appIDDAO2 = new AppIDDAO("zoxweb.org", "zoxweb");

        assertTrue(appIDDAO1.equals(appIDDAO2));

        System.out.println(appIDDAO1.hashCode());
        System.out.println(appIDDAO2.hashCode());

        AppIDDAO appIDDAO3 = new AppIDDAO("zoxweb.org", "zoxweb");
        AppIDDAO appIDDAO4 = new AppIDDAO("zoxweb.org", "zoxweb2");
        assertFalse(appIDDAO3.equals(appIDDAO4));

        System.out.println(appIDDAO3.hashCode());
        System.out.println(appIDDAO4.hashCode());


        Set<AppIDDAO> appIDDAOSet = new HashSet<>();
        appIDDAOSet.add(appIDDAO1);
        System.out.println("Set size: " + appIDDAOSet.size());
        appIDDAOSet.add(appIDDAO2);
        System.out.println("Set size: " + appIDDAOSet.size());
        appIDDAOSet.add(appIDDAO3);
        System.out.println("Set size: " + appIDDAOSet.size());
        appIDDAOSet.add(appIDDAO4);
        System.out.println("Set size: " + appIDDAOSet.size());
    }

}