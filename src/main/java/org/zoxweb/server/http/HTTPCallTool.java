package org.zoxweb.server.http;

import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.task.TaskUtil;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.http.*;
import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.SharedUtil;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class HTTPCallTool implements Runnable
{

    private static Logger log = Logger.getLogger(HTTPCallTool.class.getName());

    private static AtomicLong counter = new AtomicLong();
    private static AtomicLong failCounter = new AtomicLong();

    private HTTPMessageConfigInterface hmci;

    public HTTPCallTool(HTTPMessageConfigInterface hmci)
    {
        this.hmci = hmci;
    }

    public void run()
    {
        try
        {
            HTTPCall hc = new HTTPCall(hmci);
            HTTPResponseData rd = hc.sendRequest();
//            rd.getData();
//            log.info("" + rd);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            failCounter.incrementAndGet();
        }
        counter.incrementAndGet();
    }

    public static void main(String ...args)
    {
        try
        {
            TaskUtil.setMaxTasksQueue(2048);
            TaskUtil.setThreadMultiplier(8);
            int index = 0;
            int repeat = Integer.parseInt(args[index++]);
            String url = args[index++];
            String uri = args[index++];
            HTTPMethod httpMethod = SharedUtil.lookupEnum(args[index++], HTTPMethod.values());
            String content = index < args.length ? IOUtil.inputStreamToString(args[index++]) : null;
            HTTPMessageConfigInterface hmci = HTTPMessageConfig.createAndInit(url, uri, httpMethod);
            hmci.setContentType(HTTPMimeType.APPLICATION_JSON);
            if(content != null)
                hmci.setContent(content);
            System.out.println(GSONUtil.toJSON((HTTPMessageConfig)hmci, true, false, false));
            long ts = System.currentTimeMillis();
            for(int i = 0; i < repeat; i++)
            {
                TaskUtil.getDefaultTaskScheduler().queue(0, new HTTPCallTool(hmci));
            }


            do
            {
                Thread.sleep(50);
            }
            while(TaskUtil.getDefaultTaskProcessor().isBusy() || TaskUtil.getDefaultTaskScheduler().pendingTasks() > 0);

            ts = System.currentTimeMillis() - ts;

            TaskUtil.getDefaultTaskScheduler().close();
            TaskUtil.getDefaultTaskProcessor().close();
            float rate = ((float)counter.get()/(float)ts)*1000;

            log.info("It took:" + Const.TimeInMillis.toString(ts) + " to send:" + counter.get() + " failed:" + failCounter+ " rate:" + rate);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
