package org.zoxweb.server.http;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPMethod;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPResponseData;
import org.zoxweb.shared.util.Const.TimeInMillis;
import org.zoxweb.shared.util.SharedUtil;

public class HTTPCallStressTest
implements Runnable
{
  
  private static Logger log = Logger.getLogger(HTTPCallStressTest.class.getName());
  private static AtomicLong counter = new AtomicLong();
  
  private HTTPMessageConfigInterface hmci;
  
  public HTTPCallStressTest(HTTPMessageConfigInterface hmci)
  {
    this.hmci = hmci;
  }
  
  public void run()
  {
    try
    {
      HTTPCall hc = new HTTPCall(hmci);
      HTTPResponseData rd = hc.sendRequest();
      rd.getData();
      //log.info("" + rd);
      
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    counter.incrementAndGet();
  }
  
  public static void main(String ...args)
  {
    try
    {
      
      int index = 0;
      int repeat = Integer.parseInt(args[index++]);
      String url = args[index++];
      String uri = args[index++];
      HTTPMethod httpMethod = SharedUtil.lookupEnum(args[index++], HTTPMethod.values());
      String content = index < args.length ? IOUtil.inputStreamToString(args[index++]) : null;
      HTTPMessageConfigInterface hmci = HTTPMessageConfig.createAndInit(url, uri, httpMethod);
      hmci.setContentType(HTTPMimeType.APPLICATION_JSON);
      hmci.setContent(content);
      long ts = System.currentTimeMillis();
      for(int i = 0; i < repeat; i++)
      {
        TaskUtil.getDefaultTaskScheduler().queue(0, new HTTPCallStressTest(hmci));
      }
      
      
      while(TaskUtil.isBusy())
      {
        Thread.sleep(50);
      }
      ts = System.currentTimeMillis() - ts;
      
      TaskUtil.getDefaultTaskScheduler().close();
      TaskUtil.getDefaultTaskProcessor().close();
      
      log.info("It took:" + TimeInMillis.toString(ts) + "to send:" + counter.get());
      
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

}
