/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.server.util;


import org.zoxweb.shared.util.MetaToken;
import org.zoxweb.shared.util.SharedUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 * 
 * @author javaconsigliere
 *
 */
public class ReflectionUtil
{

	public static class AnnotationMap
	{

		private final Class<?> clazz;
		private Annotation[] classAnnotations;
		private Map<Method, Annotation[]> methodsAnnotations = new LinkedHashMap<Method, Annotation[]>();

		public AnnotationMap(Class<?> c)
		{
			SharedUtil.checkIfNulls("class can't be null", c);
			this.clazz = c;
		}

		public Class<?> getAnnotatedClass()
		{
			return clazz;
		}

		public Annotation[] getClassAnnotations()
		{
			return classAnnotations;
		}

		public Map<Method, Annotation[]> getMethodsAnnotations()
		{
			return methodsAnnotations;
		}

		public boolean equals(Object o)
		{
			if(o instanceof AnnotationMap)
				if(((AnnotationMap) o).getAnnotatedClass().equals(getAnnotatedClass()))
					return true;


			return false;
		}

		public String toString()
		{
			return clazz.getName() + "," + Arrays.toString(classAnnotations) + ":" + methodsAnnotations;
		}

		public boolean isClassAnnotatedBy(Class<? extends Annotation> c)
		{
			SharedUtil.checkIfNulls("Class can't be null", c);
			if(classAnnotations != null) {
				for (Annotation a : classAnnotations) {
					if (a.annotationType().equals(c))
						return true;
				}
			}
			return false;
		}

		public boolean isMethodAnnotatedBy(Method m, Class<? extends Annotation> c)
		{
			SharedUtil.checkIfNulls("Method or class can't be null", m,c);
			Annotation[] annotations = methodsAnnotations.get(m);
			if(annotations != null)
			{
				for (Annotation a : annotations) {
					if (a.annotationType().equals(c))
						return true;
				}
			}
			return false;
		}

//		public boolean isMethodAnnotatedBy(String m, Class<? extends Annotation> c)
//		{
//			SharedUtil.checkIfNulls("Method or class can't be null", m,c);
//			Annotation[] annotations = methodsAnnotations.get(m);
//			if(annotations != null)
//			{
//				for (Annotation a : annotations) {
//					if (a.annotationType().equals(c))
//						return true;
//				}
//			}
//			return false;
//		}


	}

	private ReflectionUtil()
    {
		
	}

	public static Object updateFinalStatic(Class<?> clazz, String fieldName, Object newValue) 
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
	
		Field field = clazz.getDeclaredField(fieldName);
		boolean fieldAccessible = field.isAccessible();
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		boolean modifierAccessible = modifiersField.isAccessible();
		modifiersField.setAccessible(true);
		int oldModifier = field.getModifiers();
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		   
		//field.setInt( field, field.getModifiers() & ~Modifier.FINAL );
		field.set(null, newValue);
		modifiersField.setInt(field, oldModifier);
		
		Object ret =  field.get(null);
		field.setAccessible(fieldAccessible);
		modifiersField.setAccessible(modifierAccessible);
		
		return ret;
   }
	
	
	
	
	
	public static <T extends Annotation> T getAnnotationFromMethod(Method m, Class<T> t)
	{
		return m.getAnnotation(t);
	}
	
	public static <T extends Annotation>  Map<Method, T> scanMethodsAnnotation(Class<?> c, Class<T> t)
	{
		Map<Method, T> ret = new HashMap<Method, T>();
		
		for(Method m : c.getMethods())
		{
			T a = m.getAnnotation(t);
			if (a != null)
			{
				ret.put(m, a);
			}
		}
		
		return ret;
	}

	public static AnnotationMap scanClassAnnotations(Class<?> c, Class<? extends Annotation>...annotationTypes)
	{
		AnnotationMap ret = new AnnotationMap(c);
		ret.classAnnotations =  matchAnnotations(c.getAnnotations(), annotationTypes);
		Method[] methods = c.getMethods();


		for(Method method : methods)
		{
			if(Modifier.isPublic(method.getModifiers())) {
				Annotation[] methodAnnotations = matchAnnotations(method.getDeclaredAnnotations(), annotationTypes);
				if(methodAnnotations != null)
					ret.methodsAnnotations.put(method, methodAnnotations);
			}
		}


		if(ret.classAnnotations != null || ret.methodsAnnotations.size() > 0)
			return ret;

		return null;
	}

	public static Annotation[] matchAnnotations(Annotation[] annotations, Class<? extends Annotation>...annotationTypes)
	{
		Set<Annotation> match = new HashSet<Annotation>();
		for(Annotation a : annotations)
		{
			for(Class<?> c : annotationTypes){
				if (a.annotationType().equals(c))
				{
					match.add(a);
					break;
				}
			}
		}
		if(match.size() > 0)
			return match.toArray(new Annotation[0]);
		return null;
	}

}