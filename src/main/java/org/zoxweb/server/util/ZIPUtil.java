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
package org.zoxweb.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

/**
 * [Please state the purpose for this class or method because it will help the team for future maintenance ...].
 * 
 */
public class ZIPUtil 
{
	
	
	public static byte[] gzip(String str)
			throws NullPointerException, IllegalArgumentException, IOException
	{
		return gzip(SharedStringUtil.getBytes(str));
	}
	
	public static byte[] gzip(byte[] content)
			throws NullPointerException, IllegalArgumentException, IOException
	{
		SharedUtil.checkIfNulls("Can't zip null content", content);
		if (content.length == 0)
		{
			throw new IllegalArgumentException("Content is empty");
		}
		ByteArrayOutputStream output = null;
		GZIPOutputStream gzipOutputStream = null;
		try
		{
			output = new ByteArrayOutputStream(content.length);
			gzipOutputStream = new GZIPOutputStream(output);
			gzipOutputStream.write(content);
			gzipOutputStream.flush();
			gzipOutputStream.finish();
			return output.toByteArray();
		}
		finally
		{
			IOUtil.close(gzipOutputStream);
		}
	}
	
	public static byte[] gunzip(byte[] content)
		throws NullPointerException, IllegalArgumentException, IOException
	{
		SharedUtil.checkIfNulls("Can't zip null content", content);
		if (content.length == 0)
		{
			throw new IllegalArgumentException("Content is empty");
		}

		GZIPInputStream gzipInputStream = null;
		try
		{
			return IOUtil.inputStreamToByteArray(new GZIPInputStream( new ByteArrayInputStream(content)), false).toByteArray();
		}
		finally
		{
			IOUtil.close(gzipInputStream);
		}
	}
	
	
	
	
}
