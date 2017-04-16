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
package org.zoxweb.shared.util;

import org.zoxweb.shared.util.Const.Unit;

/**
 * Default implementation of UnitValue interface.
 * @param <V>
 */
public class UnitValueDefault<V>
    implements UnitValue<V>
{

	private V value;
	private Unit unit;

	public UnitValueDefault()
    {

    }

    public UnitValueDefault(V value, Unit unit)
    {
	    setValue(value);
	    setUnit(unit);
    }

    /**
     * Returns the value.
     * @return
     */
	@Override
	public V getValue()
    {
		return value;
	}

    /**
     * Sets the value.
     * @param value
     */
    @Override
    public void setValue(V value)
    {
        this.value = value;
    }

    /**
     * Returns the unit.
     * @return
     */
	@Override
	public Unit getUnit()
    {
		return unit;
	}

    /**
     * Sets the unit.
     * @param unit
     */
	@Override
	public void setUnit(Unit unit)
    {
		this.unit = unit;
	}

	@Override
    public String toString()
    {
	    return getValue() + " " + getUnit();
    }

}