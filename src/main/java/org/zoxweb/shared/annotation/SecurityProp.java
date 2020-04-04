package org.zoxweb.shared.annotation;
import org.zoxweb.shared.security.AuthenticationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author javaconsigliere@gmail.com
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityProp {
    AuthenticationType[] types() default{AuthenticationType.NONE};

    /**
     * List of permissions to be applied to the current function
     * @return list of permissions
     */
    String[] permissions() default {};

    /**
     * List of roles to be applied to the current function
     * @return list of roles
     */
    String[] roles() default {};
}
