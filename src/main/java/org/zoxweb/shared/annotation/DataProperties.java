package org.zoxweb.shared.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataProperties 
{
	
	boolean  authRequired() default false;
	Class<?> inputFormat();
 	Class<?> dataType();
 	boolean  dataRequired() default true;
 	boolean  dataAutoConvert() default true;
	//String[] pemissions() default {};
	//String[] roles() default {};
}
