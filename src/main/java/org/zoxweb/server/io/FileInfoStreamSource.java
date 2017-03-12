/*
 * Copyright 2012 ZoxWeb.com LLC.
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

import java.io.InputStream;

import org.zoxweb.shared.data.FileInfoDAO;

/**
 * 
 * @author mzebib
 *
 */
public class FileInfoStreamSource
{
	private FileInfoDAO fid;
	private InputStream srcInputStream;
	
	/**
	 * 
	 * @param fid
	 * @param is
	 */
	public FileInfoStreamSource(FileInfoDAO fid, InputStream is)
	{
		this.fid = fid;
		srcInputStream = is;
	}
	
	/**
	 * 
	 * @return input stream
	 */
	public InputStream getSourceInputStream()
	{
		return srcInputStream;
	}
	
	/**
	 * 
	 * @return file info dao
	 */
	public FileInfoDAO getFileInfoDAO()
	{
		return fid;
	}
	
	
	
}
