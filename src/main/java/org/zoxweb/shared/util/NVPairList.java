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
package org.zoxweb.shared.util;


import java.util.List;

/**
 * This class declares NVBase as a list of NVPair type.
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class NVPairList
	extends NVBase<List<NVPair>>
	implements ArrayValues<NVPair>
{
	
	private boolean isFixed = false;
	/**
	 * Default constructor used for Java Bean Compiler.
	 */
	public NVPairList()
	{
		
	}
	
	/**
	 * This NVPairList constructor instantiates NVBase class with given name and value parameters.
	 * @param name
	 * @param v
	 */
	public NVPairList(String name, List<NVPair> v)
	{
		super(name, v);
	}

	/**
	 * If the value is true
	 * <ol>
	 * <li> The size of NVPair value should not be modified, the ADD/REMOVE must not be applied
	 * <li> The FilterValue of every contained NVPair cannot be modified
	 * <li> Only the value of the NVPair object can be updated
	 * </ol>
	 * @return
	 */
	public boolean isFixed() 
	{
		return isFixed;
	}

	/**
	 * 
	 * @param isFixed
	 */
	public void setFixed(boolean isFixed)
	{
		this.isFixed = isFixed;
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#values()
	 */
	@Override
	public NVPair[] values() 
	{
		// TODO Auto-generated method stub
		return value.toArray(new NVPair[0]);
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
	 * @see org.zoxweb.shared.util.ArrayValues#add(java.lang.Object)
	 */
	@Override
	public NVPair add(NVPair v) 
	{
		// TODO Auto-generated method stub
		return value.add(v) ? v : null;
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#remove(java.lang.Object)
	 */
	@Override
	public synchronized NVPair remove(NVPair v)
	{
		// TODO Auto-generated method stub
		return value.remove(v) ? v : null;
	}
	
	@Override
	public synchronized NVPair remove(String str)
	{
		return remove(get(str));
	}

	public NVPair get(GetName getName)
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
	public NVPair get(String str)
	{
		// TODO Auto-generated method stub
		return SharedUtil.lookup(value, str);
	}
	
	
	/**
	 * @see org.zoxweb.shared.util.ArrayValues#clear()
	 */
	@Override
	public void clear() 
	{
		// TODO Auto-generated method stub
		value.clear();
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#add(java.lang.Object[], boolean)
	 */
	@Override
	public synchronized void add(NVPair[] vals, boolean clear) 
	{
		// TODO Auto-generated method stub
		if (clear)
		{
			clear();
		}
		
		if ( vals != null)
		{
			for (NVPair nvp : vals)
			{
				add(nvp);
			}
		}
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#search(java.lang.String[])
	 */
	@Override
	public List<NVPair> search(String... criteria) 
	{
		return SharedUtil.lookup(value, criteria[0]);	
	}
}
