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
package org.zoxweb.server.filters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.zoxweb.shared.filters.ValueFilter;


@SuppressWarnings("serial")
public class DateTimeValueFilter
	implements ValueFilter<String, Long>
{

	private SimpleDateFormat sdf = null;

	public DateTimeValueFilter(String pattern, String timezone)
	{
		sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
	}

	/**
	 * @see org.zoxweb.shared.util.CanonicalID#toCanonicalID()
	 * @return
	 */
	@Override
	public String toCanonicalID()
	{
		return null;
	}

	/**
	 * @see org.zoxweb.shared.filters.ValueFilter#validate(java.lang.Object)
	 * @param in value to be validated
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	@Override
	public Long validate(String in)
        throws NullPointerException, IllegalArgumentException
	{
		try
		{
			return sdf.parse(in).getTime();
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * @see org.zoxweb.shared.filters.ValueFilter#isValid(java.lang.Object)
	 * @param in value to be checked
	 * @return
	 */
	@Override
	public boolean isValid(String in)
	{
		return false;
	}

}