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

import org.zoxweb.shared.util.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author mzebib
 *
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class ReferenceIDDAO 
	extends NVEntity {

	public static final String COLUMN_REF_ID = "reference_id";

	public static final NVConfig NVC_REFERENCE_ID = NVConfigManager.createNVConfig("reference_id", "The reference id of the Object","ReferenceID", true, false, true, true, true, String.class, null);
	public static final NVConfig NVC_ACCOUNT_ID = NVConfigManager.createNVConfig("account_id", "The account id","AccountID", true, false, false, true, true, String.class, null);
	public static final NVConfig NVC_USER_ID = NVConfigManager.createNVConfig("user_id", "The user id","UserID", true, false, false, true, true, String.class, null);
	public static final NVConfigEntity NVC_REFERENCE_ID_DAO = new NVConfigEntityLocal(null, null , null, true, false, false, false,ReferenceIDDAO.class, SharedUtil.toNVConfigList(NVC_REFERENCE_ID, NVC_ACCOUNT_ID, NVC_USER_ID), null, false, null);
	
//	protected ReferenceIDDAO(List<NVConfigEntity> list){
//		super(SharedUtil.merge(list, NVC_REFERENCE_ID_DAO));
//	}

	protected ReferenceIDDAO(NVConfigEntity nvce) {
		super(nvce);
	}
	
	/**
	 * Returns the reference ID.
	 * @return 
	 */
	@Id
	@Column(name = COLUMN_REF_ID)
	public String getReferenceID() {
		return lookupValue(NVC_REFERENCE_ID);
	}

	/**
	 * Sets the reference ID.
	 * @param referenceID
	 */
	public void setReferenceID(String referenceID) {
		setValue(NVC_REFERENCE_ID, referenceID);
	}
	
	/**
	 * Returns the account ID.
	 * @return 
	 */
	public String getAccountID() {
		return lookupValue(NVC_ACCOUNT_ID);
	}

	/**
	 * Sets the account ID.
	 * @param id
	 */
	public void setAccountID(String id) {
		setValue(NVC_ACCOUNT_ID, id);
	}
	
	/**
	 * Returns the user ID.
	 * @return 
	 */
	public String getUserID() {
		return lookupValue(NVC_USER_ID);
	}

	/**
	 * Sets the user ID.
	 * @param id
	 */
	public void setUserID(String id) {
		setValue(NVC_USER_ID, id);
	}

}