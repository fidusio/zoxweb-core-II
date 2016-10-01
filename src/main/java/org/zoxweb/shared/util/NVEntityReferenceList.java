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

import java.util.ArrayList;
import java.util.List;

/**
 * This class declares NVBase as a list of NVEntity type. 
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class NVEntityReferenceList 
	extends NVBase<List<NVEntity>>
	implements ArrayValues<NVEntity>
	
{
	//private NVConfigEntity nvce;
	
	/**
	 * Default constructor used for Java Bean Compiler.
	 */
	public NVEntityReferenceList()
	{
		
	}
	
	/**
	 * This constructor instantiates NVEntityReferenceList based on value of NVConfig type.
	 * @param config
	 */
	public NVEntityReferenceList(String name)
	{
		super( name, new ArrayList<NVEntity>());
		//nvce = (NVConfigEntity) config;
	}

	
	public NVEntity get(GetName getName)
	{
		if (getName != null && getName.getName() != null)
		{
			return get(getName.getName());
		}
		
		return null;
	}
	
	/**
	 * This method returns the NVConfig.
	 * @return
	 */
	@Override	
	public NVEntity get(String str)
	{
		return SharedUtil.lookup((List<? extends GetName>) value, str);
	}
	
	public int size()
	{
		return value.size();
	}
	public NVEntity[] values()
	{
		return value.toArray( new NVEntity[0]);
	}

	/**
	 * @see org.zoxweb.shared.util.ArrayValues#add(java.lang.Object)
	 */
	@Override
	public synchronized NVEntity add(NVEntity v)
	{
		// TODO Auto-generated method stub
		if  (value.add(v))
		{
			return v;
		}
		return null;
	}
	
	
	public synchronized NVEntity remove(String str)
	{
		NVEntity toRemove = get(str);
		if (toRemove != null)
		{
			remove(toRemove);
		}
		
		return toRemove;
	}
	/**
	 * @see org.zoxweb.shared.util.ArrayValues#remove(java.lang.Object)
	 */
	@Override
	public synchronized NVEntity remove(NVEntity v) {
		// TODO Auto-generated method stub
		if (value.remove(v))
		{
			return v;
		}
		else
		{
			// remove by ref id
			if (v != null && v.getReferenceID() != null)
			{
				for(NVEntity nve : values())
				{
					if (nve != null  && nve.getReferenceID() != null)
					{
						if (nve.getReferenceID().equals(v.getReferenceID()))
						{
							if (value.remove(nve))
							{
								return nve;
							}
						}
					}
				}
			}
		}
		return null;
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
	public synchronized void add(NVEntity[] vals, boolean clear) 
	{
		if (clear)
		{
			clear();
		}
		
		if ( vals != null)
		{
			for (NVEntity nve : vals)
			{
				add(nve);
			}
		}
		
	}
	
	
	
	@Override
	public List<NVEntity> search(String... criteria) {
		// TODO Auto-generated method stub
		return SharedUtil.search(value, criteria);
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
	public void setFixed(boolean isFixed) {
		// TODO Auto-generated method stub
		
	}

	
}
