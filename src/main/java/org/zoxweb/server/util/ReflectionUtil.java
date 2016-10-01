/*
 * Copyright (c) 2012-2015 ZoxWeb.com LLC.
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class ReflectionUtil 
{
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
}
