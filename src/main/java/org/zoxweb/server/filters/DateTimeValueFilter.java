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
package org.zoxweb.server.filters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.zoxweb.shared.filters.ValueFilter;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
@SuppressWarnings("serial")
public class DateTimeValueFilter
	implements ValueFilter<String, Long>
{
	private SimpleDateFormat  sdf = null;
	public DateTimeValueFilter(String pattern, String tz)
	{
		sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone(tz));
	}
	
	
	/**
	 * @see org.zoxweb.shared.util.CanonicalID#toCanonicalID()
	 */
	@Override
	public String toCanonicalID()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.zoxweb.shared.filters.ValueFilter#validate(java.lang.Object)
	 */
	@Override
	public Long validate(String in) throws NullPointerException,
			IllegalArgumentException
	{
		// TODO Auto-generated method stub
		try {
			return sdf.parse(in).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * @see org.zoxweb.shared.filters.ValueFilter#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(String in) {
		// TODO Auto-generated method stub
		return false;
	}

}
