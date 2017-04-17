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
package org.zoxweb.shared.crypto;

import org.zoxweb.shared.security.AccessException;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class EncryptedKeyDAO
    extends EncryptedDAO
{

	protected enum Param
        implements GetNVConfig
    {
		KEY_LOCK_TYPE(NVConfigManager.createNVConfig("key_lock_type", "Key lock type", "KeyLockType", true, true, KeyLockType.class)),
		OBJECT_REFERENCE_CLASS_NAME(NVConfigManager.createNVConfig("object_reference_class_name", "Object reference class name", "ObjectReferenceClassName", true, true, String.class)),
		//OBJECT_REFERENCE(NVConfigManager.createNVConfigEntity("object_reference", "Object reference", "ObjectReference", false, true, NVEntity.class, null)),
		;

		private final NVConfig nvc;

        Param(NVConfig nvc)
        {
			this.nvc = nvc;
		}
		
		@Override
		public NVConfig getNVConfig()
        {
			return nvc;
		}
	} 

	public final static NVConfigEntity NVCE_ENCRYPTED_KEY_DAO = new NVConfigEntityLocal("encrypted_key_dao", null, "EncryptedKeyDAO", false, true, false, false, EncryptedKeyDAO.class, SharedUtil.extractNVConfigs(Param.values()), null, false, EncryptedDAO.NVCE_ENCRYPTED_DAO);
	
	
	public EncryptedKeyDAO()
    {
		super(NVCE_ENCRYPTED_KEY_DAO);
	}

	protected EncryptedKeyDAO(NVConfigEntity nvce)
    {
		super(nvce);
	}

	public KeyLockType getKeyLockType()
    {
		return lookupValue(Param.KEY_LOCK_TYPE);
	}
	
	public void setKeyLockType(KeyLockType klt)
    {
		setValue(Param.KEY_LOCK_TYPE, klt);
	}
	
//	public NVEntity getObjectReference()
//	{
//		return lookupValue(Params.OBJECT_REFERENCE);
//	}
	
	public void setObjectReference(NVEntity nve)
    {
		//setValue(Params.OBJECT_REFERENCE, nve);

        if (nve.getReferenceID() == null) {
			throw new AccessException("NVEntity reference ID not set.");
		}

		setReferenceID(nve.getReferenceID());
		setObjectReferenceClassName(nve.getClass().getName());
	}

	public String getObjectReferenceClassName()
    {
		return lookupValue(Param.OBJECT_REFERENCE_CLASS_NAME);
	}
	
	public void setObjectReferenceClassName(String classMame)
    {
		setValue(Param.OBJECT_REFERENCE_CLASS_NAME, classMame);
	}
	
//	@Override
//	public String getReferenceID()
//	{
//		NVEntity nve = getObjectReference();
//		if (nve != null && nve.getReferenceID() != null)
//		{
//			return nve.getReferenceID();
//		}
//		
//		throw new AccessException("Object reference not set yet");
//	}


}