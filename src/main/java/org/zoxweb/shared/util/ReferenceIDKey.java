/*
 * Copyright (c) 2012-2014 ZoxWeb.com LLC.
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

import java.io.Serializable;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
@SuppressWarnings("serial")
public class ReferenceIDKey
implements Serializable, ReferenceID<String>
{
	private ReferenceID<String> refID;
	
	public ReferenceIDKey()
	{
	}
	
	public ReferenceIDKey(ReferenceID<String> refID)
	{
		SharedUtil.checkIfNulls("RefID null", refID);
		if ( refID.getReferenceID() == null)
		{
			throw new NullPointerException("ReferenceID value null");
		}
		this.refID = refID;
	}
	
	
	public ReferenceIDKey(String refID)
	{
		SharedUtil.checkIfNulls("RefID null", refID);
		this.refID = new ReferenceIDPortable<String>(refID);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o)
	{
		if (o != null && refID!= null)
		{
			if (o == this || o.equals(refID))
			{
				return true;
			}
			else if (o instanceof String)
			{
				return o.equals(refID.getReferenceID());
			}
			else if (o instanceof ReferenceID)
			{
				return refID.getReferenceID().equals(((ReferenceID<String>)o).getReferenceID());
			}
			
		}
		return false;
	}
	
	
	public int hashCode()
	{
		return refID.getReferenceID().hashCode();
	}

	/**
	 * @see org.zoxweb.shared.util.ReferenceID#getReferenceID()
	 */
	@Override
	public String getReferenceID() {
		// TODO Auto-generated method stub
		return refID.getReferenceID();
	}

	/**
	 * @see org.zoxweb.shared.util.ReferenceID#setReferenceID(java.lang.Object)
	 */
	@Override
	public void setReferenceID(String id) {
		// TODO Auto-generated method stub
		refID.setReferenceID(id);
	}
	
	
	
	
	
	
}
