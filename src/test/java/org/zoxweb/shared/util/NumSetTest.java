package org.zoxweb.shared.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;


public class NumSetTest {

  @Test
  public void base27ToString()
  {
    long[] longSet = {
        0, 10, 100, 1000, 10000, 1000000, Long.MAX_VALUE, 1, 2, 4, 8, 16, 31, 32, 64, 128, 256, 512, 1024
    };

    for(long val : longSet)
    {
      String str = NumSet.BASE_27.toString(val);
      String str1 = "" + NumSet.BASE_27.getLong(str);
      System.out.println(val +", "  +  str + ", " + str1 + ", " + str1.equals(""+val) + ", converted length: " + str.length());
    }
  }
  
  
  @Test
  public void base27ToLong()
  {
   
    String[] stringSet = {
      "Aaaab",
      NumSet.BASE_27.toString(Long.MAX_VALUE),
    };

    for(String val : stringSet)
    {
     
      System.out.println(val +", "  +  NumSet.BASE_27.getLong(val));

    }
  }


  @Test
  public void base27UUID()
  {
    for (int i = 0; i < 100; i++) {
      UUID uuid = UUID.randomUUID();
      String uuidStr = uuid.toString();
      String str = NumSet.BASE_27.toString(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
      System.out.println(uuid + ", " + uuidStr.length() + ", " + str + ", " + str.length());
    }
  }

  @Test
  public void parseInt()
  {
    System.out.println(SharedUtil.parseInt("25"));
    System.out.println(SharedUtil.parseInt("0xff"));
  }

  @Test
  public void testIntToBytes()
  {
    int toSend = Integer.MAX_VALUE;
    byte[] array = {(byte) ((toSend >> 24) & 0xff), (byte) ((toSend >> 16) & 0xff), (byte) ((toSend >> 8) & 0xff), (byte) ((toSend) & 0xff)};
    System.out.println(SharedStringUtil.bytesToHex(array));
    System.out.println((byte) ((toSend >> 24) & 0xff));
    System.out.println((byte) ((toSend >> 16) & 0xff));
    System.out.println((byte) ((toSend >> 8) & 0xff));
    System.out.println((byte) ((toSend >> 0) & 0xff));
    System.out.println(BytesValue.INT.toValue(array) + " " + BytesValue.INT.toValue(array).equals(toSend));
  }


  @Test
  public void testShortToBytes()
  {
    short toSend = Short.MAX_VALUE;
    byte[] array = {(byte) ((toSend >> 8) & 0xff), (byte) ((toSend) & 0xff)};
    System.out.println(SharedStringUtil.bytesToHex(array));
    System.out.println((byte) ((toSend >> 8) & 0xff));
    System.out.println((byte) ((toSend >> 0) & 0xff));
    System.out.println(BytesValue.SHORT.toValue(array) + " " + BytesValue.SHORT.toValue(array).equals(toSend));
  }

  @Test
  public void testLongToBytes()
  {
    long toSend = Long.MAX_VALUE;
    byte[] array = {
            (byte) ((toSend >> 56) & 0xff), (byte) ((toSend >> 48) & 0xff), (byte) ((toSend >> 40) & 0xff), (byte) ((toSend>>32) & 0xff),
            (byte) ((toSend >> 24) & 0xff), (byte) ((toSend >> 16) & 0xff), (byte) ((toSend >> 8) & 0xff), (byte) ((toSend) & 0xff)
    };
    System.out.println(SharedStringUtil.bytesToHex(array));
    System.out.println((byte) ((toSend >> 56) & 0xff));
    System.out.println((byte) ((toSend >> 48) & 0xff));
    System.out.println((byte) ((toSend >> 40) & 0xff));
    System.out.println((byte) ((toSend >> 32) & 0xff));
    System.out.println((byte) ((toSend >> 24) & 0xff));
    System.out.println((byte) ((toSend >> 16) & 0xff));
    System.out.println((byte) ((toSend >> 8) & 0xff));
    System.out.println((byte) ((toSend >> 0) & 0xff));
    System.out.println(BytesValue.LONG.toValue(array) + " " + BytesValue.LONG.toValue(array).equals(toSend));
  }
}
