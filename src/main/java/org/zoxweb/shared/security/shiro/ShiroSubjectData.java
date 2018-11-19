package org.zoxweb.shared.security.shiro;

import java.util.List;
import org.zoxweb.shared.data.PropertyDAO;
import org.zoxweb.shared.net.NIConfigDAO;
import org.zoxweb.shared.net.InetProp.InetProto;
import org.zoxweb.shared.util.AppGlobalID;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.SubjectID;
import org.zoxweb.shared.util.NVStringList;

@SuppressWarnings("serial")
public class ShiroSubjectData
extends PropertyDAO
implements AppGlobalID<String>, SubjectID<String>
{
  public enum Param
  implements GetNVConfig, GetName
  {
    SUBJET_ID(NVConfigManager.createNVConfig("subject_id", "Subject ID", "SubjectID", false, true, String.class)),
    APP_GID(NVConfigManager.createNVConfig("app_gid", "App global ID", "AppGID", false, true, InetProto.class)),
    ROLES(NVConfigManager.createNVConfig("roles", "Subject roles", "Roles", false, true, NVStringList.class)),
    PERMISSIONS(NVConfigManager.createNVConfig("permissions", "Subject permissions", "Permissions", false, true, NVStringList.class)),
 
    ;   
  
    private final NVConfig cType;
    
    Param(NVConfig c) {
      cType = c;
    }
    
    public NVConfig getNVConfig() {
      return cType;
    }

    @Override
    public String getName() {
      // TODO Auto-generated method stub
      return cType.getName();
    }
  
  }
  
  public static final NVConfigEntity NVC_SUBJECT_DATA = new NVConfigEntityLocal(null, null , null, true, false, false, false, NIConfigDAO.class, SharedUtil.extractNVConfigs(Param.SUBJET_ID, Param.APP_GID), null, false, PropertyDAO.NVC_PROPERTY_DAO);
  public ShiroSubjectData() {
    super(NVC_SUBJECT_DATA);
    // TODO Auto-generated constructor stub
  }

  public List<String> getPermissions()
  {
    return null;
  }
  
  public List<String> getRoles()
  {
    return null;
  }

  @Override
  public String getAppGID() {
    // TODO Auto-generated method stub
    return lookupValue((GetName)Param.APP_GID);
  }

  @Override
  public void setAppGID(String appGID) {
    // TODO Auto-generated method stub
    setValue(Param.APP_GID, appGID);
    
  }

  @Override
  public String getSubjectID() {
    // TODO Auto-generated method stub
    return lookupValue((GetName)Param.SUBJET_ID);
  }

  @Override
  public void setSubjectID(String id) {
    // TODO Auto-generated method stub
    setValue(Param.SUBJET_ID, id);
  }

}
