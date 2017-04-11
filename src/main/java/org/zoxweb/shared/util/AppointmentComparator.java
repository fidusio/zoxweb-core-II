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

import java.util.Comparator;

import org.zoxweb.shared.util.Const.RelationalOperator;

public class AppointmentComparator
		implements Comparator<Appointment> {

	private RelationalOperator equality;
	
	public AppointmentComparator() {
		this( RelationalOperator.EQUAL);
	}
	
	public AppointmentComparator(RelationalOperator equality) {
		this.equality = equality;
	}

	@Override
	public int compare(Appointment o1, Appointment o2) {
		int result = 0;

		if (o1 != null && o2 != null) {
			result = (int)(o1.getExpirationInMillis() - o2.getExpirationInMillis());

			if (result == 0) {
				switch(equality)
				{
				case EQUAL:
					break;
				case LTE:
				case LT:
					result = -1;
					break;
				case GTE:
				case GT:
					result = 1;
					break;
				default:
					throw new IllegalArgumentException("Operator not supported:" + equality);
				}
			}
		}
			
		return result;
	}

}