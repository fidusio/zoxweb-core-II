package org.zoxweb.shared.util;

public abstract class NumSet {

  public static final Base30 BASE_30 = new Base30();


  private NumSet() {}
  public static class Base30
  extends NumSet {


    private Base30(){}


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

    public static final int BASE_VAL = SETS[0].length;

    public  String toString(long val)
    {

      long result = val;
      long rest;
      StringBuilder ret = new StringBuilder();
      do {
        rest   = result % BASE_VAL;
        result = result / BASE_VAL;
        ret.insert(0, (char)SETS[0][(int)rest]);
      }
      while(result !=0);

      return ret.toString();
    }
  }


  public abstract String toString(long val);
}
