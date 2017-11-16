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
 */package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class KeyStoreInfoDAO
    extends SetNameDescriptionDAO {

    public enum Param
        implements GetNVConfig
    {
        KEY_STORE(NVConfigManager.createNVConfig("key_store", "Key store", "KeyStore", true, true, false, String.class, null)),
        KEY_STORE_PASSWORD(NVConfigManager.createNVConfig("key_store_password", "Key store password", "KeyStorePassword", true, true, false, String.class, null)),
        ALIAS(NVConfigManager.createNVConfig("alias", "Alias", "Alias", true, true, false, String.class, null)),
        KEY_PASSWORD(NVConfigManager.createNVConfig("key_password", "key password", "KeyPassword", true, true, false, String.class, null)),

        ;

        private final NVConfig nvc;

        Param(NVConfig nvc)
        {
            this.nvc = nvc;
        }

        public NVConfig getNVConfig()
        {
            return nvc;
        }
    }

    public static final NVConfigEntity NVC_KEY_STORE_INFO_DAO = new NVConfigEntityLocal(
            "key_store_info_dao",
            null ,
            KeyStoreInfoDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            KeyStoreInfoDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );

    public KeyStoreInfoDAO()
    {
        super(NVC_KEY_STORE_INFO_DAO);
    }

    public String getKeyStore()
    {
        return lookupValue(Param.KEY_STORE);
    }

    public void setKeyStore(String keyStore)
    {
        setValue(Param.KEY_STORE, keyStore);
    }

    public String getKeyStorePassword()
    {
        return lookupValue(Param.KEY_STORE_PASSWORD);
    }

    public byte[] getKeyStorePasswordAsBytes()
    {
        return SharedStringUtil.hexToBytes(getKeyStorePassword());
    }

    public void setKeyStorePassword(String keyStorePassword)
    {
        setValue(Param.KEY_STORE_PASSWORD, keyStorePassword);
    }

    public void setKeyStorePassword(byte[] keyStorePassword)
    {
        setValue(Param.KEY_STORE_PASSWORD, SharedStringUtil.bytesToHex(keyStorePassword));
    }

    public String getAlias()
    {
        return lookupValue(Param.ALIAS);
    }

    public void setAlias(String alias)
    {
        setValue(Param.ALIAS, alias);
    }

    public String getKeyPassword()
    {
        return lookupValue(Param.KEY_PASSWORD);
    }

    public byte[] getKeyPasswordAsBytes()
    {
        return SharedStringUtil.hexToBytes(getKeyPassword());
    }

    public void setKeyPassword(String aliasPassword)
    {
        setValue(Param.KEY_PASSWORD, aliasPassword);
    }

    public void setKeyPassword(byte[] aliasPassword)
    {
        setValue(Param.KEY_PASSWORD, SharedStringUtil.bytesToHex(aliasPassword));
    }

}