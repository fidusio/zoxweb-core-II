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

import java.util.Comparator;

public abstract class ValueGetterComparator<I,O>
    implements Comparator<I>
{
    public static class StringValueGetterComparator<I>
        extends ValueGetterComparator<I, String>
    {

		public StringValueGetterComparator(boolean ascending, ValueGetter<I, String> valueGetter)
        {
		    super(ascending, valueGetter);
		}

		@Override
		public int compare(I o1, I o2)
        {
			String st1 = o1 != null ?  valueGetter.getValue(o1) : null;
			String st2 = o2 != null ?  valueGetter.getValue(o2) : null;
			int ret = NVConfigComparators.STRING.COMPARATOR.compare(st1, st2);

			if (!ascending)
			{
				ret = -ret;
			}
			
			return ret;
		}
	}

	protected final ValueGetter<I,O> valueGetter;
	protected final boolean ascending;

	public ValueGetterComparator(boolean ascending, ValueGetter<I,O> valueGetter)
    {
		this.valueGetter = valueGetter;
		this.ascending = ascending;
	}

}