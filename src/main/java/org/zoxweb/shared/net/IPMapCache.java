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
package org.zoxweb.shared.net;

import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class IPMapCache
    implements IPMapStore
{

	private Map<String, String> ipMACCache = new HashMap<String, String>();
	private Set<String> exclusionFilter = new HashSet<String>();
	
	public IPMapCache()
    {

	}
	
	public synchronized boolean map(String ipAddress, String macAddress)
    {
		if (ipAddress != null && macAddress != null)
		{
			if (!exclusionFilter.contains(ipAddress) && !exclusionFilter.contains(macAddress))
			{
				synchronized(this)
				{
					ipMACCache.put(ipAddress, macAddress);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public synchronized void clear()
    {
		ipMACCache.clear();
	}
	
	public synchronized String lookupMAC(String ipAddress)
        throws IOException
    {
		return ipMACCache.get(ipAddress);
	}
	
	public int size()
    {
		return ipMACCache.size();
	}
	
	public  Set<String> exclusionFilter()
    {
		return exclusionFilter;
	}

	@Override
	public synchronized boolean removeIP(String ipAddress)
    {
		return (ipMACCache.remove(ipAddress) != null);
	}

}