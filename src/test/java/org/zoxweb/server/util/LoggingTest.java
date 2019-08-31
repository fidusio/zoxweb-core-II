package org.zoxweb.server.util;

import java.util.logging.Logger;
import org.junit.Test;
import org.zoxweb.server.logging.LoggerUtil;

public class LoggingTest {
  // static final Logger log = Logger.getLogger(LoggingTest.class.getName());
  @Test
  public void logTest()
  {
    LoggerUtil.enableDefaultLogger("org.zoxweb");
    //LoggerUtil.updateLoggingFormat(LoggerUtil.PRODUCTION_FORMAT);
    Logger log = Logger.getLogger(LoggingTest.class.getName());
    log.info("hello");

    LoggerUtil.updateLoggingFormat(LoggerUtil.PRODUCTION_FORMAT);
    Logger log1 = Logger.getLogger(LoggerUtil.class.getName());

    log1.info("hello");
  }
}
