/*
 * Copyright (c) 2012-2016 ZoxWeb.com LLC.
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
package org.zoxweb.shared.data;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.CRUD;
import org.zoxweb.shared.util.DisplayUnit;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
//import org.zoxweb.shared.util.NVConfigEntity.ArrayType;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
//import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedUtil;

/**
 * 
 * @author mzebib
 *
 */
@SuppressWarnings("serial")
public class DocumentOperationDAO 
	extends SetNameDescriptionDAO
{
	
	public enum Params
		implements GetNVConfig
	{
		X_COORDINATE(NVConfigManager.createNVConfig("x_coordinate", "The x-coordinate", "X coordinate", true, true, String.class)),
		Y_COORDINATE(NVConfigManager.createNVConfig("y_coordinate", "The y-coordinate", "Y coordinate", true, true, String.class)),
		UNIT(NVConfigManager.createNVConfig("unit", "Coordinate unit", "Unit", true, false, DisplayUnit.class)),
		OPERATION(NVConfigManager.createNVConfig("operation", "Action required", "Operation", false, false, CRUD.class)),
		//DOCUMENT_REF_ID(NVConfigManager.createNVConfig("document_dao_reference_id", "Document reference ID", "DocumentReferenceID", false, false, String.class)),
		FOLDER_REF_ID(NVConfigManager.createNVConfig("folder_dao_reference_id", "Folder reference ID", "FolderReferenceID", true, false, String.class)),
		API_CONFIG_REF_ID(NVConfigManager.createNVConfig("api_config_dao_reference_id", "APIConfigInfo reference ID", "APIConfigReferenceID", true, false, String.class)),
		
		;
		
		private final NVConfig cType;
		
		Params(NVConfig c)
		{
			cType = c;
		}
		
		public NVConfig getNVConfig() 
		{
			return cType;
		}


	}

	public static final NVConfigEntity NVC_DOCUMENT_OPERATION_DAO = new NVConfigEntityLocal(
																								"document_operation_dao", 
																								null , 
																								"DocumentOperationDAO", 
																								true, 
																								false, 
																								false, 
																								false, 
																								DocumentOperationDAO.class, 
																								SharedUtil.extractNVConfigs(Params.values()), 
																								null, 
																								false, 
																								SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
																							);
	
	
	/**
	 * The default constructor.
	 */
	public DocumentOperationDAO()
	{
		super(NVC_DOCUMENT_OPERATION_DAO);

	}
	
	/**
	 * Gets the x-coordinate value.
	 * @return
	 */
	public String getXCoordinate() 
	{
		return lookupValue(Params.X_COORDINATE);
	}
	
	/**
	 * Sets the x-coordinate value.
	 * @param x
	 */
	public void setXCoordinate(String x) 
	{
		setValue(Params.X_COORDINATE, x);
	}
	
	/**
	 * Gets the y-coordinate value.
	 * @return
	 */
	public String getYCoordinate() 
	{
		return lookupValue(Params.Y_COORDINATE);
	}
	
	/**
	 * Sets the y-coordinate value.
	 * @param y
	 */
	public void setYCoordinate(String y) 
	{
		setValue(Params.Y_COORDINATE, y);
	}
	
	/**
	 * Gets the FolderInfoDAO object reference ID.
	 * @return
	 */
	public String getFolderInfoReferenceID() 
	{
		return lookupValue(Params.FOLDER_REF_ID);
	}
	
	/**
	 * Sets the FolderInfoDAO object reference ID.
	 * @param refID
	 */
	public void setFolderInfoReferenceID(String refID)
	{
		setValue(Params.FOLDER_REF_ID, refID);
	}
	
	/**
	 * Gets the APIConfigInfo object reference ID.
	 * @return
	 */
	public String getAPIConfigInfoReferenceID() 
	{
		return lookupValue(Params.API_CONFIG_REF_ID);
	}
	
	/**
	 * Sets the APIConfigInfo object reference ID.
	 * @param refID
	 */
	public void setAPIConfigInfoReferenceID(String refID)
	{
		setValue(Params.API_CONFIG_REF_ID, refID);
	}
	
	/**
	 * Gets the display unit.
	 * @return
	 */
	public DisplayUnit getUnit()
	{
		return lookupValue(Params.UNIT);
	}
	
	/**
	 * Sets the display unit.
	 * @param unit
	 */
	public void setUnit(DisplayUnit unit)
	{
		setValue(Params.UNIT, unit);
	}
	
	/**
	 * Gets the requested operation on the document (if null the interpretation is service specific).
	 * @return
	 */
	public CRUD getOperation()
	{
		return lookupValue(Params.OPERATION);
	}
	
	/**
	 * Sets the operation.
	 * @param op
	 */
	public void setOperation(CRUD op)
	{
		setValue(Params.OPERATION, op);
	}
	
	/**
	 * Gets the document reference ID.
	 * @return
	 */
	public String getDocumentInfoReferenceID()
	{
		return getReferenceID();
	}
	
	/**
	 * Sets the document reference ID.
	 * @param refID
	 */
	public void setDocumentInfoReferenceID(String refID)
	{
		setReferenceID(refID);
	}
	
}