package org.zoxweb.shared.util;

import java.util.UUID;
import org.junit.Test;

public class NumSetTest {

  @Test
  public void convertToString()
  {
    long[] longSet = {
        0, 10, 100, 1000, 10000, 1000000, Long.MAX_VALUE, 1, 2, 4, 8, 16, 31, 32, 64, 128, 256, 512, 1024
    };

    for(long val : longSet)
    {
      String str = NumSet.BASE_30.toString(val);
      String str1 = "" + NumSet.BASE_30.getLong(str);
      System.out.println(val +", "  +  str + ", " + str1 + ", " + str1.equals(""+val) + ", converted length: " + str.length());

    }
  }
  
  
  @Test
  public void convertToLong()
  {
   
    String[] stringSet = {
      "Aaas",
      NumSet.BASE_30.toString(Long.MAX_VALUE),
    };

    for(String val : stringSet)
    {
     
      System.out.println(val +", "  +  NumSet.BASE_30.getLong(val));

    }
  }


  @Test
  public void base30UUID()
  {
    for (int i = 0; i < 100; i++) {
      UUID uuid = UUID.randomUUID();
      String str = NumSet.BASE_30.toString(uuid);
      System.out.println(uuid + ", " + str + ", " + str.length());
    }
  }
}
