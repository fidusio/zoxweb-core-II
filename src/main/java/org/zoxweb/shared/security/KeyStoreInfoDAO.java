package org.zoxweb.shared.security;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class KeyStoreInfoDAO
    extends SetNameDescriptionDAO {

    public enum Param
        implements GetNVConfig
    {
        KEY_STORE(NVConfigManager.createNVConfig("key_store", "Key store", "KeyStore", true, true, false, String.class, null)),
        KEY_STORE_PASSWORD(NVConfigManager.createNVConfig("key_store_password", "Key store password", "KeyStorePassword", true, true, false, String.class, null)),
        ALIAS(NVConfigManager.createNVConfig("alias", "Alias", "Alias", true, true, false, String.class, null)),
        ALIAS_PASSWORD(NVConfigManager.createNVConfig("alias_password", "Alias password", "AliasPassword", true, true, false, String.class, null)),

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
        return SharedStringUtil.hexToBytes(lookupValue(Param.KEY_STORE_PASSWORD));
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

    public String getAliasPassword()
    {
        return lookupValue(Param.ALIAS_PASSWORD);
    }

    public byte[] getAliasPasswordAsBytes()
    {
        return SharedStringUtil.hexToBytes(lookupValue(Param.ALIAS_PASSWORD));
    }

    public void setAliasPassword(String aliasPassword)
    {
        setValue(Param.ALIAS_PASSWORD, aliasPassword);
    }

    public void setAliasPassword(byte[] aliasPassword)
    {
        setValue(Param.ALIAS_PASSWORD, SharedStringUtil.bytesToHex(aliasPassword));
    }

}