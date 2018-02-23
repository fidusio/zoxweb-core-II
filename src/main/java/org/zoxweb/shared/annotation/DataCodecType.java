package org.zoxweb.shared.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataCodecType 
{
	Class<?> inputFormat();
 	Class<?> returnType();
	String[] pemissions() default {};
	String[] roles() default {};
}
