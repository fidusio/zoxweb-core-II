/*
 * Copyright (c) 2012-Oct 28, 2015 ZoxWeb.com LLC.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.FormInfoDAO;
import org.zoxweb.shared.data.PhoneDAO;
import org.zoxweb.shared.util.NVEntity;

/**
 * @author mzebib
 *
 */
public class JSONMapTest 
{

	public static void main(String[] args) 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("String example", "Mustapha");
		map.put("Boolean example", true);

		
		List<String> list = new ArrayList<String>();
		list.add("10");
		list.add("100");
		list.add("1000");
		
		map.put("Numbers", list);
		
		
		PhoneDAO phone1 = new PhoneDAO();
		phone1.setName("MZ Phone");
		phone1.setCountryCode("+1");
		phone1.setAreaCode("310");
		phone1.setNumber("3027883");
		
		map.put("Phone", phone1);
		
		
		List<NVEntity> phoneList = new ArrayList<NVEntity>();
		phoneList.add(phone1);
		
		PhoneDAO phone2 = new PhoneDAO();
		phone2.setName("My Phone");
		phone2.setCountryCode("+1");
		phone2.setAreaCode("650");
		phone2.setNumber("4298644");
		
		FormInfoDAO formInfo = new FormInfoDAO();
		formInfo.setFormReference(phone2);
		
		phoneList.add(formInfo);
		
		map.put("Phone List", phoneList);

		try
		{
			// To JSON
			String toJSON = GSONUtil.toJSONMap(map);
			System.out.println("ToJSON: \n" + toJSON);
			
			//	From JSON
			Map<String, ?> fromMap = GSONUtil.fromJSONMap(toJSON);
			String fromJSON = GSONUtil.toJSONMap(fromMap);
			System.out.println("FromJSON: \n" + "HashMap: "+ fromMap + "\nJSON: " + fromJSON);
			System.out.println("\nAre fromJSON and toJSON equal? " + fromJSON.equals(toJSON));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
	}

}