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
package org.zoxweb.server.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class CloseEnabledInputStream extends FilterInputStream
{

	protected boolean autoCloseable;
	
	
	public CloseEnabledInputStream(InputStream in)
	{
		this(in, true);
	}
	
	
	/**
	 * [Optional - Please state the purpose of this constructor; especially if multiple constructors exist.].
	 * 
	 * @param in
	 */
	public CloseEnabledInputStream(InputStream in, boolean autoClose)
	{
		super(in);
		this.autoCloseable = autoClose;
		// TODO Auto-generated constructor stub
	}

	
	public boolean isCloseEnabled()
	{
		return autoCloseable;
	}
	
	
	public void close()
		throws IOException
	{
		if (isCloseEnabled())
			super.close();
	}
}
