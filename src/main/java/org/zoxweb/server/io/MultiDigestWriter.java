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

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.MessageDigest;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class MultiDigestWriter
	extends FilterWriter
	implements MultiDigestInterface
{

	
	private MultiDigest md = new MultiDigest();
	/**
	 * [Optional - Please state the purpose of this constructor; especially if multiple constructors exist.].
	 * 
	 * @param out
	 */
	public MultiDigestWriter(Writer out, MessageDigest ...digests)
	{
		super(out);
		// TODO Auto-generated constructor stub
		md.setMessageDigests(digests);
	}
	
	
	
	public void write(char cbuf[], int off, int len) throws IOException
	{
		out.write(cbuf, off, len);
		md.update(cbuf, off, len);
	}



	/**
	 * @see org.zoxweb.server.io.MultiDigestInterface#setMessageDigests(java.security.MessageDigest[])
	 */
	@Override
	public void setMessageDigests(MessageDigest[] digests) {
		// TODO Auto-generated method stub
		md.setMessageDigests(digests);
	}



	/**
	 * @see org.zoxweb.server.io.MultiDigestInterface#getMessageDigests()
	 */
	@Override
	public MessageDigest[] getMessageDigests() {
		// TODO Auto-generated method stub
		return md.getMessageDigests();
	}



	/**
	 * @see org.zoxweb.server.io.MultiDigestInterface#on(boolean)
	 */
	@Override
	public void on(boolean on) {
		// TODO Auto-generated method stub
		md.on(on);
	}



	/**
	 * @see org.zoxweb.server.io.MultiDigestInterface#totalBytes()
	 */
	@Override
	public long totalBytes() {
		// TODO Auto-generated method stub
		return md.totalBytes();
	}

}
