package org.zoxweb.shared.net;

import java.io.IOException;
import java.util.Arrays;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class SharedNetUtil
{
  public static final byte MASK_VALS[]= {0,(byte)128,(byte)192,(byte)224,(byte)240,(byte)248,(byte)252,(byte)254, (byte)255};
  
  private SharedNetUtil() {};
  
  public static byte[] getV4Address(String ipV4)
          throws IOException
  {
    String bytes[] = ipV4.split("\\.");
    if (bytes.length != 4)
    {
      throw new IllegalArgumentException("Invalid ip address:" + ipV4 + " length:" + bytes.length);
    }
    byte ret[] = new byte[4];
    for (int i=0; i < bytes.length; i++)
    {
      int val = Integer.parseInt(bytes[i]);
      if (val < 0 || val > 255)
      {
        throw new IOException("Invalid ip address:" + ipV4);
      }
      ret[i] = (byte)val;
    }
    return ret;
  }
  
  public static boolean validateV4Netmask(byte netmask[])
  {
    if (netmask.length != 4)
    {
      throw new IllegalArgumentException("Invalid netmask length:" + netmask.length);
    }
    
    for(int i=0; i< netmask.length; i++)
    {
      if (i!=0)
      {
        if (SharedUtil.toUnsignedInt(netmask[i-1]) < 255)
        {
          if (netmask[i] != 0)
          {
            return false;
          }
          else
          {
            continue;
          }
        }
      }
      
      boolean test = false;
      for(int j=0; j < MASK_VALS.length; j++)
      {
        
        if (netmask[i] == MASK_VALS[j])
        {
          test = true;
          break;
        }
      }
      if (!test)
        return false;
    }
    
    
    return true;
  }
  
  
  public static byte[]getNetwork(byte[] address, byte[] netmask)
      throws IOException
  {
    byte[] networkBytes = new byte[address.length];
    
    for (int i = 0; i < networkBytes.length; i++)
    {
        networkBytes[i] = (byte)(address[i] & netmask[i]);
    }
    return networkBytes;        
  }
  
  public static String toV4Address(byte[] address)
    throws IOException
  {
    SharedUtil.checkIfNulls("Null address", address);
    if (address.length != 4)
      throw new IOException("Invalid address length:" + address.length);
    
    StringBuilder sb = new StringBuilder();
    int index = 0;
    
    sb.append(SharedStringUtil.toString(address[index++]));
    sb.append('.');
    sb.append(SharedStringUtil.toString(address[index++]));
    sb.append('.');
    sb.append(SharedStringUtil.toString(address[index++]));
    sb.append('.');
    sb.append(SharedStringUtil.toString(address[index++]));
    
    return sb.toString();
  }
  
  public static boolean belongsToNetwork(byte[] address, byte[] netmask, byte[] network)
      throws IOException
  {
    SharedUtil.checkIfNulls("Network or IP adress can't be null", network, address);
    byte[] tempNetwork = null;
    if ( netmask != null)
    {
        tempNetwork = getNetwork(address, netmask);
    }
    else
    {
        //log.info("networkmask: null");
        tempNetwork = address;
    }
    return Arrays.equals(network, tempNetwork);
  }
  
  public static boolean validateNIConfig(NIConfigDAO nicd) throws IOException
  {
    SharedUtil.checkIfNulls("NIConfigDAO null", nicd, nicd.getInetProtocol());
    
    switch(nicd.getInetProtocol())
    {
      case DHCP:
        break;
      case STATIC:
        if (SharedStringUtil.isEmpty(nicd.getAddress()) || SharedStringUtil.isEmpty(nicd.getNetmask()))
        {
          throw new IOException("Network missing:" + SharedUtil.toCanonicalID(',', nicd.getAddress(), nicd.getNetmask()));
        }
        byte address[] = getV4Address(nicd.getAddress());
        byte netmask[] = getV4Address(nicd.getNetmask());
        if(!validateV4Netmask(netmask))
        {
          return false;
        }
        byte gateway[] = nicd.getGateway() != null ?  getV4Address(nicd.getGateway()) : null;
        byte network[] = getNetwork(address, netmask);
        if (gateway != null)
        {
          // gateway must not be equal to address
          if (Arrays.equals(address, gateway))
          {
            throw new IOException("address and gateway equals:" + toV4Address(address));
          }
          if (!(belongsToNetwork(address, netmask, network) && 
              belongsToNetwork(gateway, netmask, network)))
          {
            throw new IOException("BAD Network config:" + SharedUtil.toCanonicalID(',', nicd.getAddress(), nicd.getNetmask(), nicd.getGateway()));
          }
        }         
 
        break;
      default:
        return false;
    }
    
    return true;
  }
  
}
