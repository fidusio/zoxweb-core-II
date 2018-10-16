package org.zoxweb.shared.net;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;

public class IPV4Test {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @Test
  public void validIPS() throws UnknownHostException {
  
    String ipList[] = {
      "10.0.0.1",
      "192.168.0.1",
      "1.1.1.1",
      "1.2.3.4",
      "255.255.255.255"
    };
    
    for(String ip : ipList)
    {
      byte[] ipV4Bytes = InetAddress.getByName(ip).getAddress();
      byte[] ipV4Shared = SharedNetUtil.getV4Address(ip);
      assert(Arrays.equals(ipV4Bytes, ipV4Shared));
      
    }
  }

}
