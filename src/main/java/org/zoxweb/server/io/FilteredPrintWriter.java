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


import java.io.PrintWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.logging.Logger;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class FilteredPrintWriter
	extends PrintWriter
{
	private static final transient Logger log = Logger.getLogger(FilteredPrintWriter.class.getName());
	
	private MultiDigest mdw = new MultiDigest();
	
	/**
	 * [Optional - Please state the purpose of this constructor; especially if multiple constructors exist.].
	 * 
	 * @param out
	 */
	public FilteredPrintWriter(Writer out, MessageDigest ...digests)
	{
		super(out);
		// TODO Auto-generated constructor stub
		mdw.setMessageDigests(digests);
	}

	
	public void write(char buf[], int off, int len)
	{
		super.write(buf, off, len);
		mdw.update(buf, off, len);
	}
	
	public void close()
	{
		super.close();
		log.info("stat:"+mdw);
	}
	
	
}
