package org.zoxweb.shared.http;

import org.zoxweb.shared.data.SetNameDescriptionDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class HTTPDefaultResponseDAO
    extends SetNameDescriptionDAO
{
    public enum Param
            implements GetNVConfig
    {

        STATUS_CODE(NVConfigManager.createNVConfig("status_code", "Status code", "StatusCode", false, true, HTTPStatusCode.class)),
        MESSAGE(NVConfigManager.createNVConfig("message", "Message", "Message", false, true, String.class)),

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

    public static final NVConfigEntity NVC_HTTP_DEFAULT_RESPONSE_DAO = new NVConfigEntityLocal(
            "http_default_response_dao",
            null,
            HTTPDefaultResponseDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            HTTPDefaultResponseDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO
    );

    public HTTPDefaultResponseDAO(HTTPStatusCode statusCode, String message)
    {
        this();
        setStatusCode(statusCode);
        setMessage(message);
    }

    /**
     * The default constructor.
     */
    public HTTPDefaultResponseDAO()
    {
        super(NVC_HTTP_DEFAULT_RESPONSE_DAO);
    }

    public HTTPStatusCode getStatusCode()
    {
        return lookupValue(Param.STATUS_CODE);
    }

    public void setStatusCode(HTTPStatusCode code)
    {
        setValue(Param.STATUS_CODE, code);
    }

    public String getMessage()
    {
        return lookupValue(Param.MESSAGE);
    }

    public void setMessage(String message)
    {
        setValue(Param.MESSAGE, message);
    }

}