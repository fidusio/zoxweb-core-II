package org.zoxweb.shared.net;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;

public class IPV4Test {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @Test
  public void validIPS() throws IOException {
  
    String ipList[] = {
      "10.0.0.1",
      "192.168.0.1",
      "1.1.1.1",
      "1.2.3.4",
      "255.255.255.255",
    };
    
    for(String ip : ipList)
    {
      byte[] ipV4Bytes = InetAddress.getByName(ip).getAddress();
      byte[] ipV4Shared = SharedNetUtil.getV4Address(ip);
      assert(Arrays.equals(ipV4Bytes, ipV4Shared));
    }
  }

  @Test
  public void invalidIPS(){

    String ipList[] = {
            "10.0.0.1983",
            "192.168.0",
            "1.1.1.1.1",
            "1.2.3.a",
            "255.255.255.256",
            "batata.com",
    };

    for(String ip : ipList)
    {

      try {
        SharedNetUtil.getV4Address(ip);
        assert(false);
      }
      catch(Exception e)
      {

      }
    }
  }

  @Test
  public void networkTest() throws IOException
  {
    byte netmask[] = SharedNetUtil.getV4Address("255.255.128.0");
    byte router[] = SharedNetUtil.getV4Address("192.168.0.1");
    byte network[] = SharedNetUtil.getNetwork(router, netmask);

      String ipList[] = {
              "192.168.0.2",
              "192.168.0.254",
              "192.168.1.1",
              "192.168.1.155",
              "192.168.127.155",
              "192.168.128.0",
              "10.0.1.0",
      };


      for(String ip : ipList)
      {
          byte address[] = SharedNetUtil.getV4Address(ip);
          System.out.println(ip + " belongs to network " + SharedNetUtil.belongsToNetwork(network, netmask, address));
      }

  }

}
