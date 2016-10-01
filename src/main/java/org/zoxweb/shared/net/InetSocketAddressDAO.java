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
package org.zoxweb.shared.net;



import org.zoxweb.shared.data.SetNameDAO;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class InetSocketAddressDAO
extends SetNameDAO
{
	
	private static final NVConfig INET_ADDRESS = NVConfigManager.createNVConfig("inet_address", "The ip address","InetAddress",true, false, String.class);
	private static final NVConfig PORT = NVConfigManager.createNVConfig("port", "The port number","Port", true, false, int.class);
	public static final NVConfigEntity NVC_INET_SOCKET_ADDRESS_DAO = new NVConfigEntityLocal("inet_socket_address_dao", null , "InetSocketAddressDAO", true, false, false, false, InetSocketAddressDAO.class, SharedUtil.toNVConfigList(INET_ADDRESS, PORT), null, false, SetNameDAO.NVC_NAME_DAO);
	
	
	public InetSocketAddressDAO()
	{
		super(NVC_INET_SOCKET_ADDRESS_DAO);
	}
	
	
	public InetSocketAddressDAO(String addressPort)
	{
		this();
		String params[] = addressPort.split(":");
		setInetAddress(params[0]);
		if(params.length>1)
			setPort(Integer.parseInt(params[1]));
		else
			setPort(-1);
	}
	
	public InetSocketAddressDAO(String address, int port)
	{
		this();
		setInetAddress(address);
		setPort(port);
	}
	
	
	public String getInetAddress()
	{
		return lookupValue(INET_ADDRESS);
	}
	
	public void setInetAddress(String address)
	{
		setValue(INET_ADDRESS, address);
	}
	
	public int getPort() 
	{
		return lookupValue(PORT);
	}
	
	public void setPort(int port)
	{
		if (port < -1)
		{
			throw new IllegalArgumentException("Invalid port:" + port + " < 0 ");
		}
		setValue(PORT, port);
	}

	
	public boolean equals(Object o)
	{
		if (o != null && o instanceof InetSocketAddressDAO)
		{
			if (getInetAddress() != null && getInetAddress().equalsIgnoreCase(((InetSocketAddressDAO)o).getInetAddress()))
			{
				if (getPort() == ((InetSocketAddressDAO)o).getPort())
				{
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	public String toString()
	{
		return SharedUtil.toCanonicalID(':',getInetAddress(), getPort());
	}
	
}
