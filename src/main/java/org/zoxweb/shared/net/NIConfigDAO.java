package org.zoxweb.shared.net;

import java.io.IOException;
import org.zoxweb.shared.data.ConfigPropertiesDAO;
import org.zoxweb.shared.util.GetNVConfig;

import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;

import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.net.InetProp.InetProto;;

@SuppressWarnings("serial")
public class NIConfigDAO
  extends ConfigPropertiesDAO
{
  public enum Param
  implements GetNVConfig, GetName
  {
    NI_NAME(NVConfigManager.createNVConfig("ni_name", "Network interface physical name", "NIName", false, true, String.class)),
    INET_PROTO(NVConfigManager.createNVConfig("inet_proto", "Inet proptocol", "Proto", false, true, InetProto.class)),
    ADDRESS(NVConfigManager.createNVConfig("address", "Address", "Address", false, true, String.class)),
    NETMASK(NVConfigManager.createNVConfig("netmask", "Network Mask", "NetMask", false, true, String.class)),
    GATEWAY(NVConfigManager.createNVConfig("gateway", "Gateway", "Gateway", false, true, String.class)),
    NETWORK(NVConfigManager.createNVConfig("network", "Network", "Network", false, true, String.class)),
    USERNAME(NVConfigManager.createNVConfig("username", "PPPoE user name", "UserName", false, true, String.class)),
    PASSWORD(NVConfigManager.createNVConfig("password", "PPPoE passowrd", "Password", false, true, String.class)),
    DNS_SERVERS(NVConfigManager.createNVConfig("dns-nameservers", "DNS Name Servers", "DNSNameServers", false, true, String.class)),

    
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
  
  public static final NVConfigEntity NVC_NI_CONFIG_DAO = new NVConfigEntityLocal(null, null , null, true, false, false, false, NIConfigDAO.class, SharedUtil.extractNVConfigs(Param.NI_NAME, Param.INET_PROTO), null, false, ConfigPropertiesDAO.NVC_CONFIG_PROPERTIES_DAO);
  
  public NIConfigDAO()
  {
    super(NVC_NI_CONFIG_DAO);
  }
  
  
  
  public String getNIName()
  {
    return lookupValue(Param.NI_NAME.getName());
  }
  
  public void setNIName(String niName) 
  {
    setValue(Param.NI_NAME, niName);
  }
  
  public InetProto getInetProtocol()
  {
    return lookupValue(Param.INET_PROTO.getName());
  }
  
  public void setInteProtocol(InetProto proto)
  {
    setValue(Param.INET_PROTO, proto);
  }
  
  public String getAddress()
  {
    return getProperties().getValue(Param.ADDRESS.getName());
  }
  
  public void setAddress(String address) 
  {
    getProperties().add(Param.ADDRESS, address);
  }
  
  public String getNetmask()
  {
    return getProperties().getValue(Param.NETMASK);
  }
  
  public void setNetmask(String netmask) 
  {
    getProperties().add(Param.NETMASK, netmask);
  }
  
  public String getGateway()
  {
    return getProperties().getValue(Param.GATEWAY);
  }
  
  public void setGateway(String gateway) 
  {
    getProperties().add(Param.GATEWAY, gateway);
  }
  
  public String getDNSServers()
  {
    return getProperties().getValue(Param.DNS_SERVERS);
  }
  
  public void setDNSServers(String dnsServers) 
  {
    getProperties().add(Param.DNS_SERVERS, dnsServers);
  }
  
  public String getNetwork()
  {
    return getProperties().getValue(Param.NETWORK);
  }
  
  public void setNetwork(String network) 
  {
    getProperties().add(Param.NETWORK, network);
  }
  
  public String getUsername()
  {
    return getProperties().getValue(Param.USERNAME);
  }
  
  public void setUsername(String username) 
  {
    getProperties().add(Param.USERNAME, username);
  }
  
  public String getPassword()
  {
    return getProperties().getValue(Param.PASSWORD);
  }
  
  public void setPassword(String password) 
  {
    getProperties().add(Param.PASSWORD, password);
  }

  
  public String getAddressNetmask() throws IOException
  {
    return getAddress() + "/" + SharedNetUtil.toNetmaskIPV4(SharedNetUtil.getV4Address(getNetmask()));
  }
  
}
