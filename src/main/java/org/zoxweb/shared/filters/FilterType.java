/*
 * Copyright 2012 ZoxWeb.com LLC.
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
package org.zoxweb.shared.filters;

import java.math.BigDecimal;

import org.zoxweb.shared.data.DataConst;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

/**
 * The filter type enum that implements ValueFilter which is has string set as 
 * both the input and output.
 * @author mzebib
 *
 */
public enum FilterType
	implements ValueFilter<String,String>
{
	/**
	 * The binary (usually byte array) value.
	 */
	BINARY
	{
		/**
		 * Validate the object
		 * @param in value to be validated
		 * @return validated acceptable value
		 * @throws NullPointerException if in is null
		 * @throws IllegalArgumentException if in is invalid
		 */
		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return in;
		}
		
		/**
		 * Check if the value is valid
		 * @param in value to be checked
		 * @return true if valid false if not
		 */
		public boolean isValid(String in)
		{
			return true;
		}
		
	},
	
	/**
	 * The BigDecimal value.
	 */
	BIG_DECIMAL
	{
		/**
		 * Validate the object
		 * @param in value to be validated
		 * @return validated acceptable value
		 * @throws NullPointerException if in is null
		 * @throws IllegalArgumentException if in is invalid
		 */
		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return "" + BigDecimalFilter.SINGLETON.validate(in);
		}
		
		/**
		 * Check if the value is valid
		 * @param in value to be checked
		 * @return true if valid false if not
		 */
		public boolean isValid(String in)
		{
			return BigDecimalFilter.SINGLETON.isValid(in);
		}
		
		
	},
	
	/**
	 * This is a boolean value.
	 */
	BOOLEAN
	{
		/**
		 * Validate the object
		 * @param in value to be validated
		 * @return validated acceptable value
		 * @throws NullPointerException if in is null
		 * @throws IllegalArgumentException if in is invalid
		 */
		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return "" + Boolean.valueOf( in);
		}
		
		
		/**
		 * Check if the value is valid
		 * @param in value to be checked
		 * @return true if valid false if not
		 */
		public boolean isValid(String in)
		{
			return true;
		}
	
	}
	,
	
	/**
	 * The clear string.
	 */
	CLEAR
	{
		
	},
	DOMAIN 
	{
		//private static final String DOMAIN_NAME_PATTERN = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
		private static final String DOMAIN_NAME_PATTERN = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,65}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$";
		
		public String validate(String inParam)
				throws  NullPointerException, IllegalArgumentException
		{
			String in = SharedStringUtil.trimOrNull(inParam);
			SharedUtil.checkIfNulls("Null or empty input.", in);
			in = in.toLowerCase();
			
	    	if (FilterType.URL.isValid(in))
	    	{
	    		in = FilterType.URL.validate(in);
	    		
		    	int index = in.indexOf("://");

		    	if (index != -1)
		    	{
		    		// keep everything after the "://"
		    		in = in.substring(index + 3);
		    	}

		    	index = in.indexOf('/');

		    	if (index != -1) 
		    	{
		    	    // keep everything before the '/'
		    		in = in.substring(0, index);
		    	}
	    	}
	    	else if (FilterType.EMAIL.isValid(in))
	    	{
	    		in = FilterType.EMAIL.validate(in);
	    		in = SharedStringUtil.valueAfterRightToken(in, "@");
	    	}
	    	
	    	if (!in.matches(DOMAIN_NAME_PATTERN))
	    	{
	    		throw new IllegalArgumentException("Invalid input: " + inParam);
	    	}

	    	// check for and remove a preceding 'www'
	    	// followed by any sequence of characters (non-greedy)
	    	// followed by a '.'
	    	// from the beginning of the string
	    	in = in.replaceFirst("^www.*?\\.", "");
	    	
	    	if (!DataConst.DomainExtension.isValidExtension(in))
	    	{
	    		throw new IllegalArgumentException("Invalid input: " + inParam);
	    	}
	    	
	    	String[] results = in.split("\\.");

	    	if (results.length >= 2)
	    	{
	    		StringBuilder sb = new StringBuilder();
	    		sb.append(results[results.length - 2]);
	    		sb.append(".");
	    		sb.append(results[results.length - 1]);
	    		
	    		in = sb.toString();
	    	}
	    	
	    	if (in != null)
	    	{
	    		return in;
	    	}
	    	else
	    	{
	    		throw new IllegalArgumentException("Invalid input: " + inParam);
	    	}
		}
			
		public  boolean isValid(String in)
		{
			try
			{
				validate(in);
			}
			catch (Exception e)
			{
				return false;
			}
			
			return true;
		}
		
	},
	/**
	 * The a domain/account ID.
	 */
	DOMAIN_ACCOUNT_ID
	{
		public static final  String PATTERN = "[www.]?[-a-zA-Z0-9][-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|]";
		public static final int MAX_LENGTH = 4096;
		
		public String validate(String str)
			throws  NullPointerException, IllegalArgumentException
		{
			str = SharedStringUtil.trimOrNull(str);
			SharedUtil.checkIfNulls("URL address null or empty", str);
			
			if (str.matches(PATTERN))
			{
				if (str.length() > MAX_LENGTH)
					throw new IllegalArgumentException("URL length > max length " + str.length() + ":" + str );
				
				return str.toLowerCase();
			}
			else
			{
				throw new IllegalArgumentException("Invalid URL syntax " + str);
			}
		}
		
		public  boolean isValid(String str)
		{
			str = SharedStringUtil.trimOrNull(str);
			
			if (str != null)
			{
				return str.matches(PATTERN) && !(str.length() > MAX_LENGTH);
			}
			
			return false;
		}

	},
	
	/**
	 * This is a double value.
	 */
	DOUBLE
	{
		/**
		 * Validate the object
		 * @param in value to be validated
		 * @return validated acceptable value
		 * @throws NullPointerException if in is null
		 * @throws IllegalArgumentException if in is invalid
		 */
		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return "" + Double.valueOf(in);
		}
		
		
		/**
		 * Check if the value is valid
		 * @param in value to be checked
		 * @return true if valid false if not
		 */
		public boolean isValid( String in)
		{
			try
			{
				Double.valueOf(in);
			}
			catch (Exception e)
			{
				return false;
			}
			
			return true;
		}
		
	},
	
	/**
	 * This is an email value.
	 */
	EMAIL
	{
		public static final String REGEXP ="^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))$";
		public static final int MAX_LENGTH = 254;
		
		public String validate(String str)
			throws  NullPointerException, IllegalArgumentException
		{
			str = SharedStringUtil.trimOrNull(str);
			SharedUtil.checkIfNulls("Email address null or empty", str);
			
			if (str.matches(REGEXP))
			{
				if (str.length() > MAX_LENGTH)
					throw new IllegalArgumentException("Email length > max length " + str.length() + ":" + str );
				
				return str.toLowerCase();
			}
			else
			{
				throw new IllegalArgumentException("Invalid email");
			}
		}
		
		public boolean isValid(String str)
		{
			str = SharedStringUtil.trimOrNull(str);
			
			if (str != null)
			{
				return str.matches(REGEXP) && !(str.length() > MAX_LENGTH);
			}
			
			return false;
		}
		
	},
	
	/**
	 * This is a URL value.
	 */
	URL
	{
		public static final  String PATTERN = "^(https?|ftp|file)://[-a-zA-Z0-9][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
				//"^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		//"(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?"
		//"^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?$_iuS";
				                         //"_^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?$_iuS";
		public static final int MAX_LENGTH = 4096;
		
		public String validate(String str)
			throws  NullPointerException, IllegalArgumentException
		{
			str = SharedStringUtil.trimOrNull(str);
			SharedUtil.checkIfNulls("URL address null or empty", str);
			
			if (str.matches(PATTERN))
			{
				if (str.length() > MAX_LENGTH)
					throw new IllegalArgumentException("URL length > max length " + str.length() + ":" + str );
				
				return str.toLowerCase();
			}
			else
			{
				throw new IllegalArgumentException("Invalid URL: " + str);
				
			}
		}
		
		public  boolean isValid(String str)
		{
			str = SharedStringUtil.trimOrNull(str);
			
			if (str != null)
			{
				return str.matches(PATTERN) && !(str.length() > MAX_LENGTH);
			}
			
			return false;
		}
		
	},
	
	/**
	 * This field is encrypted but value clear to the user.
	 */
	ENCRYPT,
	
	/**
	 * This field is encrypted but hidden from the user.
	 */
	ENCRYPT_MASK,
	
	/**
	 * This is a file.
	 */
	FILE,
	
	/**
	 * This is a float value.
	 */
	FLOAT
	{
		/**
		 * Validate the object
		 * @param in value to be validated
		 * @return validated acceptable value
		 * @throws NullPointerException if in is null
		 * @throws IllegalArgumentException if in is invalid
		 */
		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return ""+Float.valueOf(in);
		}
		
		
		/**
		 * Check if the value is valid
		 * @param in value to be checked
		 * @return true if valid false if not
		 */
		public boolean isValid(String in)
		{
			try
			{
				Float.valueOf(in);
			}
			catch (Exception e)
			{
				return false;
			}
			
			return true;
		}
		
	},
	
	/**
	 * This is a hashed field.
	 */
	HASHED,
	
	HIDDEN
	{

		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return null;
		}	
		
		public boolean isValid(String in)
		{
			return SharedStringUtil.isEmpty(in);
		}	
	},
	
	
	/**
	 * This is an integer field.
	 */
	INTEGER
	{
		/**
		 * Validate the object
		 * @param in value to be validated
		 * @return validated acceptable value
		 * @throws NullPointerException if in is null
		 * @throws IllegalArgumentException if in is invalid
		 */
		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return "" + Integer.valueOf(in);
		}
		
		
		/**
		 * Check if the value is valid
		 * @param in value to be checked
		 * @return true if valid false if not
		 */
		public boolean isValid(String in)
		{
			try
			{
				Integer.valueOf(in);
			}
			catch (Exception e)
			{
				return false;
			}
			
			return true;
		}
		
	},
	
	/**
	 * This is a long value.
	 */
	LONG
	{
		/**
		 * Validate the object
		 * @param in value to be validated
		 * @return validated acceptable value
		 * @throws NullPointerException if in is null
		 * @throws IllegalArgumentException if in is invalid
		 */
		public String validate(String in) 
				throws NullPointerException, IllegalArgumentException
		{
			return "" + Long.valueOf(in);
		}
		
		
		/**
		 * Check if the value is valid
		 * @param in value to be checked
		 * @return true if valid false if not
		 */
		public boolean isValid(String in)
		{
			try
			{
				Long.valueOf(in);
			}
			catch (Exception e)
			{
				return false;
			}
			
			return true;
		}
				
	},
	
	/**
	 * This is the password field.
	 */
	PASSWORD
	{
		//public static final String REGEXP ="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
		public static final String REGEXP ="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,64})";
		public static final int MIN_LENGTH = 8;
		
		public String validate(String str)
			throws  NullPointerException, IllegalArgumentException
		{
			str = SharedStringUtil.trimOrNull(str);
			SharedUtil.checkIfNulls("Password null or empty", str);
			
			if (str.matches(REGEXP))
			{
				if (str.length() < MIN_LENGTH)
					throw new IllegalArgumentException("Password length < min length " + str.length() + ":" + str );
				
				return str;
			}
			else
			{
				throw new IllegalArgumentException("Invalid password: " + str);
				
			}
		}
		
		public boolean isValid(String str)
		{
			str = SharedStringUtil.trimOrNull(str);
			
			if (str != null)
			{
				return str.matches(REGEXP) && !(str.length() < MIN_LENGTH);
			}
			
			return false;
		}
		
	};
	

	
	/**
	 * Validate the object
	 * @param in value to be validated
	 * @return validated acceptable value
	 * @throws NullPointerException if in is null
	 * @throws IllegalArgumentException if in is invalid
	 */
	public String validate(String in) 
			throws NullPointerException, IllegalArgumentException
	{
		return in;
	}
	
	
	/**
	 * Check if the value is valid
	 * @param in value to be checked
	 * @return true if valid false if not
	 */
	public boolean isValid(String in)
	{
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public String toCanonicalID() 
	{
		return name();
	}
	
	/**
	 * This method maps the given class to the applicable primitive type. Otherwise, returns null.
	 * @param clazz
	 * @return
	 */
	public static FilterType mapPrimitiveFilterType(Class<?> clazz)
	{
		if (String.class.equals(clazz))
		{
			return FilterType.CLEAR;
		}
		
		if (Integer.class.equals(clazz) || int.class.equals(clazz))
		{
			return FilterType.INTEGER;
		}
		
		if (Long.class.equals(clazz) || long.class.equals(clazz))
		{
			return FilterType.LONG;
		}
		
		if (Double.class.equals(clazz) || double.class.equals(clazz))
		{
			return FilterType.DOUBLE;
		}
		
		if (Float.class.equals(clazz) || float.class.equals(clazz))
		{
			return FilterType.FLOAT;
		}
		
		if (Boolean.class.equals(clazz) || boolean.class.equals(clazz))
		{
			return FilterType.BOOLEAN;
		}
		
		if (BigDecimal.class.equals(clazz))
		{
			return FilterType.BIG_DECIMAL;
		}
		
		return null;
		
	}
	
	/**
	 * This method maps the given NVConfig to the applicable primitive type. Otherwise, returns null. 
	 * @param nvc
	 * @return
	 */
	public static FilterType mapPrimitiveFilterType(NVConfig nvc)
	{
		return mapPrimitiveFilterType(nvc.getMetaType());
	}
	
	/**
	 * This method converts the string value to given NVConfig type.
	 * @param nvc
	 * @param value
	 * @return
	 */
	public static Object stringToValue(NVConfig nvc, String value)
	{
		return stringToValue(nvc.getMetaType(), value);
	}
	
	/**
	 * This method converts the string value to given class type.
	 * @param clazz
	 * @param value
	 * @return
	 */
	public static Object stringToValue(Class<?> clazz, String value)
	{
		if (value != null)
		{
			if (Integer.class.equals(clazz) || int.class.equals(clazz))
			{
				return Integer.valueOf(value);
			}
			
			if (Long.class.equals(clazz) || long.class.equals(clazz))
			{
				return Long.valueOf(value);
			}
			
			if (Double.class.equals(clazz) || double.class.equals(clazz))
			{
				return Double.valueOf(value);
			}
			
			if (Float.class.equals(clazz) || float.class.equals(clazz))
			{
				return Float.valueOf(value);
			}
			
			if (Boolean.class.equals(clazz) || boolean.class.equals(clazz))
			{
				return Boolean.valueOf(value);
			}
			
			if (BigDecimal.class.equals(clazz))
			{
				return new BigDecimal(value);
			}
		}
		return value;
	}
	
}