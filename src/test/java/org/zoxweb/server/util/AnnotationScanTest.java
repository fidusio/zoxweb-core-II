package org.zoxweb.server.util;

import org.junit.jupiter.api.Test;
import org.zoxweb.shared.annotation.EndPointProp;
import org.zoxweb.shared.annotation.SecurityProp;
import org.zoxweb.shared.http.HTTPMethod;
import org.zoxweb.shared.security.AuthenticationType;

public class AnnotationScanTest {

    @SecurityProp(authentications = {AuthenticationType.DOMAIN}, roles = "admin")
    public static class ToBeTested
    {
        @EndPointProp(uris = "/profile/{profileId}", name = "profileReader", methods = {HTTPMethod.GET})
        @SecurityProp(authentications = {AuthenticationType.ALL})
        public String getProfile(String profileId)
        {
            return "str";
        }

        public void noAnnotations(){};
        @Deprecated
        private void notToBeReported(){}


        @EndPointProp(uris = "/check-user/{user}", name = "CheckUser", methods = {HTTPMethod.POST})
        @SecurityProp(authentications = {AuthenticationType.ALL}, permissions= "self, admin")
        public static boolean checkStatus(String user)
        {
            return true;
        }

    }

    @Test
    public void annotationScan()
    {
       ReflectionUtil.AnnotationMap result =  ReflectionUtil.scanClassAnnotations(ToBeTested.class, EndPointProp.class, Deprecated.class, SecurityProp.class);
       assert(result!=null);
       assert(result.getClassAnnotations() != null);
       assert(result.getMethodsAnnotations().size() > 0);
       assert(result.isClassAnnotatedBy(SecurityProp.class));
       assert(!result.isClassAnnotatedBy(Deprecated.class));
    }
}
