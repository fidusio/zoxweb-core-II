package org.zoxweb.shared.data;

import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntity.ArrayType;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.data.DataConst.Language;

/**
 * Created on 8/4/17
 */
@SuppressWarnings("serial")
public class UserPreferenceDAO
        extends SetNameDescriptionDAO {

    public enum Param
        implements GetNVConfig {

        DEFAULT_LANGUAGE(NVConfigManager.createNVConfig("language", "Default language", "DefaultLanguage", false, true, Language.class)),
        DEFAULT_SHIPPING_ADDRESS(NVConfigManager.createNVConfigEntity("shipping_address", "Default shipping address", "DefaultShippingAddress", false, true, AddressDAO.NVC_ADDRESS_DAO, ArrayType.NOT_ARRAY)),
        DEFAULT_BILLING_ADDRESS(NVConfigManager.createNVConfigEntity("billing_address", "Default billing address", "DefaultBillingAddress", false, true, AddressDAO.NVC_ADDRESS_DAO, ArrayType.NOT_ARRAY)),
        DEFAULT_CREDIT_CARD(NVConfigManager.createNVConfigEntity("credit_card", "Default credit card", "DefaultCreditCard", false, true, CreditCardDAO.NVC_CREDIT_CARD_DAO, ArrayType.NOT_ARRAY)),

        ;

        private final NVConfig nvc;

        Param(NVConfig nvc) {
            this.nvc = nvc;
        }

        public NVConfig getNVConfig() {
            return nvc;
        }
    }

    public static final NVConfigEntity NVC_USER_PREFERENCE_DAO = new NVConfigEntityLocal(
            "user_preference_dao",
            null,
            "UserPreferenceDAO",
            true,
            false,
            false,
            false,
            UserPreferenceDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );

    public UserPreferenceDAO()
    {
        super(NVC_USER_PREFERENCE_DAO);
    }

    public Language getDefaultLanguage()
    {
        return lookupValue(Param.DEFAULT_LANGUAGE);
    }

    public void setDefaultLanguage(Language language)
    {
        setValue(Param.DEFAULT_LANGUAGE, language);
    }

    public AddressDAO getDefaultShippingAddress()
    {
        return lookupValue(Param.DEFAULT_SHIPPING_ADDRESS);
    }

    public void setDefaultShippingAddress(AddressDAO address)
    {
        setValue(Param.DEFAULT_SHIPPING_ADDRESS, address);
    }

    public AddressDAO getDefaultBillingAddress()
    {
        return lookupValue(Param.DEFAULT_BILLING_ADDRESS);
    }

    public void setDefaultBillingAddress(AddressDAO address)
    {
        setValue(Param.DEFAULT_BILLING_ADDRESS, address);
    }

    public CreditCardDAO getDefaultCreditCard()
    {
        return lookupValue(Param.DEFAULT_CREDIT_CARD);
    }

    public void setDefaultCreditCard(CreditCardDAO creditCard)
    {
        setValue(Param.DEFAULT_CREDIT_CARD, creditCard);
    }


}