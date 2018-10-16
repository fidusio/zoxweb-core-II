package org.zoxweb.shared.net;

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
  
}
