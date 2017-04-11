/*
 * Copyright (c) 2012-Sep 11, 2015 ZoxWeb.com LLC.
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

import org.zoxweb.shared.util.Const.Unit;

public interface UnitValue<V>
		extends SetValue<V> {

	/**
	 * Returns the unit.
	 * @return unit
	 */
	 Unit getUnit();

	/**
	 * Sets the unit.
	 * @param unit
	 */
	 void setUnit(Unit unit);

}