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
package org.zoxweb.server.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


import java.util.Map;

import org.zoxweb.shared.api.APIException;
import org.zoxweb.shared.api.APIFileInfoMap;
import org.zoxweb.shared.api.APIServiceProvider;
import org.zoxweb.shared.security.AccessException;


/**
 * The API document store interface which extends API service provider interface.
 * @author mzebib
 *
 * @param <V>
 */
public interface APIDocumentStore<V>
	extends APIServiceProvider<V>
{
	/**
	 * This method creates a file.
	 * @param folderID
	 * @param file
	 * @param is
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public APIFileInfoMap createFile(String folderID, APIFileInfoMap file, InputStream is, boolean closeStream)  
			throws NullPointerException, IllegalArgumentException, IOException, AccessException, APIException;
	
	
	
	/**
	 * 
	 * Create a folder  
	 * @param folderFullPath
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws AccessException
	 */
	public APIFileInfoMap createFolder(String folderFullPath)  
			throws NullPointerException, IllegalArgumentException, IOException, AccessException , APIException;
	
	/**
	 * This method reads a file.
	 * @param map
	 * @param os
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public APIFileInfoMap readFile(APIFileInfoMap map, OutputStream os, boolean closeStream) 
			throws NullPointerException, IllegalArgumentException, IOException, AccessException, APIException;
	
	/**
	 * This method updates a file.
	 * @param map
	 * @param is
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public APIFileInfoMap updateFile(APIFileInfoMap map, InputStream is, boolean closeStream) 
			throws NullPointerException, IllegalArgumentException, IOException, AccessException, APIException;
	
	/**
	 * This method deletes a file.
	 * @param map
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public void deleteFile(APIFileInfoMap map) 
			throws NullPointerException, IllegalArgumentException, IOException, AccessException, APIException;
	
	/**
	 * This method returns a list of files.
	 * @return
	 */
	public Map<String, APIFileInfoMap> discover()
		throws IOException, AccessException, APIException;
	

	/**
	 * This method is used to search for list of files given arguments.
	 * @param args
	 */
	public List<APIFileInfoMap> search(String... args) 
			throws NullPointerException, IllegalArgumentException, IOException, AccessException, APIException;
	
	
	
}
