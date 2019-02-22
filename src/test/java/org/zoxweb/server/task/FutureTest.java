package org.zoxweb.server.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class FutureTest {

    public static void main(String ...args)
    {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(()->
        {

            try
            {
                TimeUnit.SECONDS.sleep(1);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + ":" + TaskUtil.getDefaultTaskProcessor().availableExecutorThreads());
            return "batata";
        }, TaskUtil.getDefaultTaskProcessor());
        cf.handleAsync(( str, e)->
        {
            System.out.println(Thread.currentThread() + ":" + TaskUtil.getDefaultTaskProcessor().availableExecutorThreads());
            return str;
        },TaskUtil.getDefaultTaskProcessor());

        System.out.println("Done");
        TaskUtil.waitIfBusyThenClose(50);
    }


}
