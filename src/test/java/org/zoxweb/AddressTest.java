/*
 * Copyright (c) 2012-May 27, 2014 ZoxWeb.com LLC.
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
package org.zoxweb;

import java.io.IOException;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.data.UserInfoDAO;
import org.zoxweb.shared.util.SharedUtil;

/**
 * @author mzebib
 *
 */
public class AddressTest 
{

	public static void main(String[] args) throws IOException
	{
		try
		{
			AddressDAO address = new AddressDAO();
			
			address.setStreet("1802 Purdue Ave. Suite A");
			address.setCity("Los Angeles");
			address.setStateOrProvince("CA");
			address.setCountry("USA");
			
			address.setZIPOrPostalCode("90025");
			
			
			System.out.println(address);
			String json = GSONUtil.toJSON(address, true);
			System.out.println( json);
			try {
				AddressDAO addressFromJSON = GSONUtil.fromJSON( json, AddressDAO.class);
				System.out.println( addressFromJSON);
	//			for ( NVBase<?> nvb : addressFromJSON.getAttributes().values())
	//			{
	//				System.out.println(nvb.getClass() + " name:" + nvb.getName() + " value:" + nvb.getValue());
	//				
	//			}
				
				System.out.println(SharedUtil.toDebugString(addressFromJSON));
				UserInfoDAO uid = new UserInfoDAO();
				uid.getListOfAddresses().add( address);
				System.out.println(SharedUtil.toDebugString(uid));
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
