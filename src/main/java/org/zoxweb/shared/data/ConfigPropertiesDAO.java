package org.zoxweb.shared.data;

import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.NVGenericMap;
import org.zoxweb.shared.util.SetCanonicalID;
import org.zoxweb.shared.util.SharedUtil;


@SuppressWarnings("serial")
public class ConfigPropertiesDAO
extends SetNameDescriptionDAO
implements SetCanonicalID
{
  public enum Param
  implements GetNVConfig
  {
    CANONICAL_ID(NVConfigManager.createNVConfig("canonical_id", "Canonical ID", "CanonicalID", false, true, String.class)),
    PROPERTIES(NVConfigManager.createNVConfig("properties", "Configuration properties", "Properties", false, true, NVGenericMap.class)),
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
  
  
  
  /**
   * This NVConfigEntity type constant is set to an instantiation of a NVConfigEntityLocal object based on DataContentDAO.
   */
  public static final NVConfigEntity NVC_CONFIG_PROPERTIES_DAO = new NVConfigEntityLocal("config_properties_dao",
                                                                              null,
                                                                              "ConfigPropertiesDAO", 
                                                                              true,
                                                                              false,
                                                                              false,
                                                                              false,
                                                                              ConfigPropertiesDAO.class,
                                                                              SharedUtil.extractNVConfigs(Param.values()),
                                                                              null,
                                                                              false,
                                                                              SetNameDescriptionDAO.NVC_NAME_DESCRIPTION_DAO);
  
  public ConfigPropertiesDAO()
  {
    super(NVC_CONFIG_PROPERTIES_DAO);
  }
  
  protected ConfigPropertiesDAO(NVConfigEntity nvce)
  {
    super(nvce);
  }

  /**
   * Returns string representation of this class.
   */
  @Override
  public String toCanonicalID() 
  {
    return getCanonicalID();
  }
  
  /**
   *Returns canonical ID. 
   */
  @Override
  public String getCanonicalID() 
  {
    return lookupValue(Param.CANONICAL_ID);
  }

  /**
   * Sets canonical ID.
   */
  @Override
  public void setCanonicalID(String id) 
  {
    setValue(Param.CANONICAL_ID, id);
  }
  
  public NVGenericMap getProperties()
  {
    return (NVGenericMap) lookup(Param.PROPERTIES);
  }
}
