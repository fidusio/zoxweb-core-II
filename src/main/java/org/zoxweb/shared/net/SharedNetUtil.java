package org.zoxweb.shared.net;

import java.io.IOException;
import java.util.Arrays;
import org.zoxweb.shared.util.SharedUtil;

public class SharedNetUtil
{
  private SharedNetUtil() {};
  
  public static byte[] getV4Address(String ipV4)
  {
    String bytes[] = ipV4.split("\\.");
    if (bytes.length != 4)
    {
      throw new IllegalArgumentException("Invalid ip address:" + ipV4 + " length " + bytes.length);
    }
    byte ret[] = new byte[4];
    for (int i=0; i < bytes.length; i++)
    {
      int val = Integer.parseInt(bytes[i]);
      if (val < 0 || val > 255)
      {
        throw new IllegalArgumentException("Invalid ip address:" + ipV4);
      }
      ret[i] = (byte)val;
    }
    return ret;
  }
  
  
  public static byte[] getNetwork(byte[] addressBytes, byte[] maskBytes) 
      throws IOException
  {
    byte[] networkBytes = new byte[addressBytes.length];
    
    for (int i = 0; i < networkBytes.length; i++)
    {
        networkBytes[i] = (byte)(addressBytes[i] & maskBytes[i]);
    }
    return networkBytes;        
  }
  
  
  public static boolean belongsToNetwork(byte[] network, byte[] networkMask, byte[] ipAddress)
      throws IOException
  {
    SharedUtil.checkIfNulls("Network or IP adress can't be null", network, ipAddress);
    byte[] tempNetwork = null;
    if ( networkMask != null)
    {
        tempNetwork = getNetwork(ipAddress, networkMask);
    }
    else
    {
        //log.info("networkmask: null");
        tempNetwork = ipAddress;
    }
    return Arrays.equals(network, tempNetwork);
        
  }
  
}
