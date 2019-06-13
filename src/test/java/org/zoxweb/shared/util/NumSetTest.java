package org.zoxweb.shared.util;

import org.junit.Test;
import org.zoxweb.shared.util.NumSet.Base30;

public class NumSetTest {

  @Test
  public void convert()
  {
    long[] longSet = {
      0, 10, 100, 1000, 10000, 1000000, Long.MAX_VALUE, 1, 2, 4, 8, 16, 31, 32, 64, 128, 256, 512, 1024
    };

    for(long val : longSet)
    {
      System.out.println(val +", " +NumSet.BASE_30.toString(val));
    }
  }
}
