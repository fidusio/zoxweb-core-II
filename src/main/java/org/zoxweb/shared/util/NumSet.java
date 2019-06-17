package org.zoxweb.shared.util;

import java.util.UUID;

public abstract class NumSet {

  public static final Base30 BASE_30 = new Base30();


  private NumSet() {}
  public static class Base30
  extends NumSet {


    private Base30(){}

    //public static final String MAX_VALUE = BASE_30.toString(Long.MAX_VALUE);


    public static final byte[][] SETS = {
        {
            //"abcdefghijkmnpqrstuvwxy3456789"
            'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z',
            '2','3','4','5','6','7','8','9'
        },
        {
            //"ABCDEFGHIJKMNPQRSTUVWXY3456789"
            'A','B','C','D','E','F','G','H','I','J','K','M','N','P','Q','R','S','T','U','V','W','X','Y','Z',
            '2','3','4','5','6','7','8','9'
        }
    };


    public  String toString(long val)
    {
      return toString(val, SETS, false, 13);
    }

    public  String toString(UUID uuid)
    {
      return toString(uuid.getMostSignificantBits(), SETS, true, 13) +
             toString(uuid.getLeastSignificantBits(), SETS, true, 13);
    }



    public long getLong(String str)
    {
      return getLong(str, SETS);
    }
  }


  public static long getLong(String str, byte[][] sets)
  {
    int radix = sets[0].length;
    long ret = 0;
    byte[] strBytes = SharedStringUtil.getBytes(str);
    for (int i = 0 ; i < strBytes.length; i++)
    {
      byte b = strBytes[strBytes.length-1-i];
      int val = val(b, sets);
      ret += val*Math.pow(radix, i);
    }

    return ret;
  }


  private static int val(byte b, byte[][] sets)
  {
    for (int i=0; i < sets[0].length; i++)
    {
      if(b == sets[0][i] || b == sets[1][i])
        return i;
    }
    throw new NumberFormatException();
  }

  public abstract String toString(long val);

   protected static String toString(long val, byte[][] sets, boolean addLeadingZero, int maxLength)
  {
    int radix = sets[0].length;
    long result = Math.abs(val);;
    long rest;
    StringBuilder ret = new StringBuilder();
    do {
      rest   = result % radix;
      result = result / radix;
      ret.insert(0, (char)sets[0][(int)rest]);
    }
    while(result !=0);


    if(addLeadingZero)
    {
      while(ret.length() < maxLength)
      {
        ret.insert(0, (char)sets[0][0]);
      }
    }

    return ret.toString();
  }
}
