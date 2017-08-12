package org.zoxweb.shared.security;

import org.zoxweb.shared.data.TimeStampDAO;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.SubjectID;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedStringUtil;


/**
 * Created on 7/13/17
 */
@SuppressWarnings("serial")
public class SubjectAPIKey
    extends TimeStampDAO
    implements SubjectID<String> {

    public enum Param
        implements GetNVConfig {

        SUBJECT_ID(NVConfigManager.createNVConfig("subject_id", "Subject ID", "SubjectID", true, false, true, String.class, null)),
        API_KEY(NVConfigManager.createNVConfig("api_key", "API Key", "APIKey", true, false, false, String.class, FilterType.ENCRYPT)),

        ;

        private final NVConfig nvc;

        Param(NVConfig nvc) {
            this.nvc = nvc;
        }

        @Override
        public NVConfig getNVConfig() {
            return nvc;
        }
    }

    public static final NVConfigEntity NVC_SUBJECT_API_KEY = new NVConfigEntityLocal(
            "subject_api_key",
            null,
            SubjectAPIKey.class.getSimpleName(),
            true,
            false,
            false,
            false,
            SubjectAPIKey.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            TimeStampDAO.NVC_TIME_STAMP_DAO
    );

    public SubjectAPIKey() {
        super(NVC_SUBJECT_API_KEY);
    }

    protected SubjectAPIKey(NVConfigEntity nvce) {
        super(nvce);
    }

    @Override
    public String getSubjectID() {
        return lookupValue(Param.SUBJECT_ID);
    }

    @Override
    public void setSubjectID(String id) {
        setValue(Param.SUBJECT_ID, id);
    }

    public String getAPIKey() {
        return lookupValue(Param.API_KEY);
    }
    
    
    public byte[] getAPIKeyAsBytes()
    {
    	String key = getAPIKey();
    	if (key != null)
        {
            return SharedStringUtil.getBytes(key);
        }

    	return null;
    }

    public void setAPIKey(String apiKey) {
        setValue(Param.API_KEY, apiKey);
    }

}