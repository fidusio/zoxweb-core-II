package org.zoxweb.shared.http;


import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.SharedUtil;

/**
 * This class was reintroduced for backward compatibility reason.
 * IT SHOULD NEVER BE USED and might delete in future releases.

 * @author mnael
 *
 */
@SuppressWarnings("serial")
@Deprecated
public class HTTPCallConfig
extends HTTPMessageConfig 
{
	
	/**
	 * This NVConfigEntity type constant is set to an instantiation of a NVConfigEntityLocal object based on AddressDAO.
	 */
	public static final NVConfigEntity NVC_HTTP_CALL_CONFIG = new NVConfigEntityLocal("http_call_config",
																					  null ,
																					  "HTTPCallConfig",
																					  true,
																					  false,
																					  false,
																					  false,
																					  HTTPCallConfig.class,
																					  SharedUtil.extractNVConfigs(Params.values()), null, false, HTTPMessageConfig.NVC_HTTP_MESSAGE_CONFIG);
	@Deprecated
	public HTTPCallConfig()
	{
		super(NVC_HTTP_CALL_CONFIG);
	}
}
