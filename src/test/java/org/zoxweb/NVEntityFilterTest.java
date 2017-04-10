/*
 * Copyright (c) 2012-Sep 22, 2015 ZoxWeb.com LLC.
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
package org.zoxweb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.zoxweb.shared.data.FileInfoDAO;
import org.zoxweb.shared.data.FolderInfoDAO;
import org.zoxweb.shared.data.FormInfoDAO;
import org.zoxweb.shared.data.PhoneDAO;
import org.zoxweb.shared.filters.NVEntityFilter;

/**
 * @author mzebib
 *
 */
public class NVEntityFilterTest
{

	@Before
	public void setUp() 
			throws Exception 
	{
		
	}
	
	@After
	public void tearDown() 
			throws Exception 
	{
		
	}

	@Test
	public void test1() 
	{
		NVEntityFilter filter = new NVEntityFilter(FormInfoDAO.NVC_FORM_INFO_DAO);
		
		System.out.println("Supported NVConfigEntity: " + filter.toCanonicalID());
		
		FolderInfoDAO folderInfo = new FolderInfoDAO();
		System.out.println("NVE: " + folderInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(folderInfo));
		
		FileInfoDAO fileInfo = new FileInfoDAO();
		System.out.println("NVE: " + fileInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(fileInfo));
		
		FormInfoDAO formInfo = new FormInfoDAO();
		System.out.println("NVE: " + formInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(formInfo));
	}
	
	@Test
	public void test2() 
	{
		NVEntityFilter filter = new NVEntityFilter(FolderInfoDAO.NVC_FOLDER_INFO_DAO, FileInfoDAO.NVC_FILE_INFO_DAO, FormInfoDAO.NVC_FORM_INFO_DAO);
		
		System.out.println("\nSupported NVConfigEntity: " + filter.toCanonicalID());
		
		FolderInfoDAO folderInfo = new FolderInfoDAO();
		System.out.println("NVE: " + folderInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(folderInfo));
		
		FileInfoDAO fileInfo = new FileInfoDAO();
		System.out.println("NVE: " + fileInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(fileInfo));
		
		FormInfoDAO formInfo = new FormInfoDAO();
		System.out.println("NVE: " + formInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(formInfo));
	}
	
	@Test
	public void test3() 
	{
		NVEntityFilter filter = new NVEntityFilter();
		
		System.out.println("\nSupported NVConfigEntity: " + filter.toCanonicalID());
		
		FolderInfoDAO folderInfo = new FolderInfoDAO();
		System.out.println("NVE: " + folderInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(folderInfo));
		
		FileInfoDAO fileInfo = new FileInfoDAO();
		System.out.println("NVE: " + fileInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(fileInfo));
		
		FormInfoDAO formInfo = new FormInfoDAO();
		System.out.println("NVE: " + formInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(formInfo));
	}
	
	@Test
	public void test4() 
	{
		NVEntityFilter filter = new NVEntityFilter(PhoneDAO.NVC_PHONE_DAO);
		
		System.out.println("\nSupported NVConfigEntity: " + filter.toCanonicalID());
		
		FolderInfoDAO folderInfo = new FolderInfoDAO();
		System.out.println("NVE: " + folderInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(folderInfo));
		
		FileInfoDAO fileInfo = new FileInfoDAO();
		System.out.println("NVE: " + fileInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(fileInfo));
		
		FormInfoDAO formInfo = new FormInfoDAO();
		System.out.println("NVE: " + formInfo.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(formInfo));
		
		PhoneDAO phone = new PhoneDAO();
		System.out.println("NVE: " + phone.getClass().getSimpleName() + "  Is Valid? " + filter.isValid(phone));
	}
	
}