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
package org.zoxweb.shared.util;

import java.util.List;
import java.util.Map;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
@SuppressWarnings("serial")
public class NVGetNameValueMap
extends NVBase<Map<GetName, GetNameValue<String>>>
implements ArrayValues<GetNameValue<String>>
{

	
	public NVGetNameValueMap()
	{
		
	}
	
	public NVGetNameValueMap(String name, Map<GetName, GetNameValue<String>> map)
	{
		super(name, map);
	}
	
	
	
	
	public GetNameValue<String> get(GetName getName)
	{
		if (getName != null && getName.getName() != null)
		{
			return get(getName.getName());
		}
		
		return null;
	}
	
	
	/**
	 * @see org.zoxweb.shared.util.ArrayValues#get(java.lang.String)
	 */
	@Override
	public GetNameValue<String> get(String name) {
		// TODO Auto-generated method stub
		return value.get(new GetNameKey(name, true));
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#size()
	 */
	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return value.size();
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#values()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GetNameValue<String>[] values()
	{
		// TODO Auto-generated method stub
		return value.values().toArray(new GetNameValue[0]);
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#add(java.lang.Object)
	 */
	@Override
	public synchronized GetNameValue<String> add(GetNameValue<String> v)
	{
		// TODO Auto-generated method stub
		return value.put(new GetNameKey(v, true), v);
	}

	
	public synchronized GetNameValue<String> remove(String name)
	{
		return value.remove(new GetNameKey(name, true));
	}
	
	
	/**
	 * @see org.zoxweb.shared.util.ArrayValues#remove(java.lang.Object)
	 */
	@Override
	public synchronized GetNameValue<String> remove(GetNameValue<String> v)
	{
		// TODO Auto-generated method stub
		return value.remove(new GetNameKey(v, true));
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#clear()
	 */
	@Override
	public void clear()
	{
		value.clear();
		
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#add(java.lang.Object[], boolean)
	 */
	@Override
	public void add(GetNameValue<String>[] vals, boolean clear)
	{
	
		if (clear)
		{
			clear();
		}
		
		if ( vals != null)
		{
			for (GetNameValue<String> gnv : vals)
			{
				add(gnv);
			}
		}
		
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#search(java.lang.String[])
	 */
	@Override
	public List<GetNameValue<String>> search(String... criteria)
	{
		// TODO Auto-generated method stub
		return SharedUtil.search(values(), criteria[0]);
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#isFixed()
	 */
	@Override
	public boolean isFixed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#setFixed(boolean)
	 */
	@Override
	public void setFixed(boolean isFixed)
	{
		// TODO Auto-generated method stub
		
	}

}
