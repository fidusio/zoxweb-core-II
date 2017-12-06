package org.zoxweb.shared.data;

import org.junit.Test;

import org.zoxweb.shared.security.model.SecurityModel;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AppAccessModeTest {

    @Test
    public void testCreateAppAccessMode() {
        String subjectID = UUID.randomUUID() + "@zoxweb.com";

        AppAccessMode appAccessMode = new AppAccessMode();
        appAccessMode.setSubjectID(subjectID);
        appAccessMode.getRolesAsList().getValue().add(SecurityModel.Role.APP_ADMIN);
        appAccessMode.getRolesAsList().getValue().add(SecurityModel.Role.APP_USER);
        appAccessMode.getRolesAsList().getValue().add(SecurityModel.Role.APP_SERVICE_PROVIDER);

        assertNotNull(appAccessMode);
        assertNotNull(appAccessMode.getSubjectID());
        assertEquals(subjectID, appAccessMode.getSubjectID());
        assertTrue(!appAccessMode.getRolesAsList().getValue().isEmpty());
        assertEquals(3, appAccessMode.getRolesAsList().getValue().size());
        assertEquals(SecurityModel.Role.APP_ADMIN, appAccessMode.getRolesAsList().getValue().get(0));
        assertEquals(SecurityModel.Role.APP_USER, appAccessMode.getRolesAsList().getValue().get(1));
        assertEquals(SecurityModel.Role.APP_SERVICE_PROVIDER, appAccessMode.getRolesAsList().getValue().get(2));
        assertNotNull(appAccessMode.geRoles());
        assertTrue(appAccessMode.geRoles().length > 0);

        for (SecurityModel.Role role : appAccessMode.geRoles()) {
            System.out.println("Role: " + role);
        }
    }

}
