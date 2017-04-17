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
package org.zoxweb.shared.protocol;

import org.zoxweb.shared.util.GetName;

public enum MessageMarker
    implements GetName
{
	START,
	END,
	HEADER_START,
	HEADER_END,
	CONTENT_START,
	CONTENT_END;

	private final String name;

	MessageMarker()
    {
		this.name= name().toLowerCase();
	}

	@Override
	public String getName()
    {
		return name;
	}
	
	public String toString()
    {
		return getName();
	}

}