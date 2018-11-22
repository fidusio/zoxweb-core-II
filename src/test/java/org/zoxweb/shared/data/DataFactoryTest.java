package org.zoxweb.shared.data;

import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.NVEntityInstance;

public class DataFactoryTest {

  
  public static void testDataFactory()
  {
      DataFactory.SINGLETON.registerFactory(ZWDataFactory.SINGLETON, ZWDataFactory.NVEntityTypeClass.values());
      long zwDelta;
      long dataDelta;
      NVEntity one;
      NVEntity two;
      
      
      for (NVEntityInstance nvei : ZWDataFactory.NVEntityTypeClass.values())
      {
          DataFactory.SINGLETON.createNVEntity(nvei.getNVConfigEntity().getName());
          ZWDataFactory.SINGLETON.createNVEntity(nvei.getNVConfigEntity().getName());
      }
      
      int negCounter = 0;
      long totalData = 0;
      long totalZW = 0;
      for (NVEntityInstance nvei : ZWDataFactory.NVEntityTypeClass.values())
      {
          dataDelta = System.nanoTime();
          one = DataFactory.SINGLETON.createNVEntity(nvei.getNVConfigEntity().getMetaTypeBase().getName());
          dataDelta = System.nanoTime() - dataDelta;
          
          zwDelta = System.nanoTime();
          two = ZWDataFactory.SINGLETON.createNVEntity(nvei.getNVConfigEntity().getName());
          zwDelta = System.nanoTime() - zwDelta;
          long diff = zwDelta - dataDelta;
          if (diff < 0 )
          {
            negCounter++;
            totalZW += diff;
          }
          else 
          {
            totalData += diff;
          }
          
          
          if (one != null && two !=null && !one.getClass().equals(two.getClass()))
          {
              System.out.println("mismatch:" + one.getClass() + "," + two.getClass()) ; 
          }
          else if (!one.getClass().equals(two.getClass()))
             System.out.println(nvei.getNVConfigEntity().getName() +":"+ one.getClass().getName() +":"+ two.getClass().getName());
          
          System.out.println("dataDelta:" + dataDelta +", zwDelta:" + zwDelta + ", diff:" +(diff) + (diff < 0 ? ", "  + nvei.getNVConfigEntity().getName() : ""));
          
          
      }
      
      System.out.println("Total:" +  ZWDataFactory.NVEntityTypeClass.values().length + " slow:" + negCounter +  " result:"+(totalData + totalZW));
  }
  
  public static void main(String ...args)
  {
    testDataFactory();
  }
  
}
