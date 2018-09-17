package org.zoxweb.shared.util;

public class LongIDGenerator implements IDGenerator<Long, Long> {

  public static final LongIDGenerator DEFAULT = new LongIDGenerator();
  
  
  private long currentID = 0;

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return "LongIDGenerator";
  }

  @Override
  public Long generateID() {
    // TODO Auto-generated method stub
    return nextID();
  }

  @Override
  public Long generateNativeID() {
    // TODO Auto-generated method stub
    return nextID();
  }
  
  public long currentID()
  {
    return currentID;
  }
  
  public synchronized long nextID()
  {
    return ++currentID;
  }

}
