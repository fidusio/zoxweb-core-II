package org.zoxweb.shared.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentDataType 
{
	boolean autoConvert() default true;
	Class<?> inputFormat();
 	Class<?> returnType();
 	boolean  returnRequired() default true;
	String[] pemissions() default {};
	String[] roles() default {};
}
