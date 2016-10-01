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
package org.zoxweb;

import org.zoxweb.shared.util.Const.Status;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class SwitchTest {

	public static void main(String ...args)
	{
		Status list [] = 
			{
				null,
				Status.ACTIVE,
				Status.EXPIRED,
			};
		
		
		for (Status st: list)
		{
			try
			{
			switch(st)
			{
			case ACTIVE:
				System.out.println("Status:" + st);
				break;
			case EXPIRED:
				System.out.println("Status:" + st);
				break;
			case INACTIVE:
				System.out.println("Status:" + st);
				break;
			case INVALID:
				System.out.println("Status:" + st);
				break;
			case PENDING:
				System.out.println("Status:" + st);
				break;
			case SUSPENDED:
				System.out.println("Status:" + st);
				break;
			default:
				System.out.println("Status:" + st);
				break;
			
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
