/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.server.task;

import java.util.concurrent.atomic.AtomicInteger;
import org.zoxweb.shared.util.Const;

public class TaskProcessorTest
    implements Runnable {

  AtomicInteger ai = new AtomicInteger();
  int counter = 0;

  public void run() {
    ai.addAndGet(1);
    inc();
  }

  protected synchronized void inc() {
    counter++;
  }

  public static void runTest(TaskProcessor tp, TaskProcessorTest td, int numberOfTasks) {
    long delta = System.currentTimeMillis();
    int counter = 0;

    for (int i = 0; i < numberOfTasks; i++) {
      counter++;
      //System.out.println("Adding event " + (counter));
      //tp.queueTask(new TaskEvent( tp, td,  (Object[])null));
      tp.execute(td);
    }

    while (td.counter != counter) {
      System.out.println("Available thread " + tp + " " + td.counter);
      synchronized (td) {
        try {
          //System.out.println("Available thread " + te.availableExecutorThreads());
          td.wait(TaskProcessor.WAIT_TIME);
          //System.out.println("Available thread " + te.availableExecutorThreads());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    delta = System.currentTimeMillis() - delta;

    System.out.println(
        "It took " + Const.TimeInMillis.toString(delta) + " millis callback " + td + " using queue "
            + tp.getQueueMaxSize() + " and " + tp.availableExecutorThreads() + " executor thread");
    System.out.println("Available thread " + tp.availableExecutorThreads() + " total "
        + ((TaskProcessorTest) td).counter + ":" + ((TaskProcessorTest) td).ai.get());
  }

  public static void main(String[] args) {

    int taskQueueSize = 800;
    int threadCount = 16;
    int numberOfTasks = 20_000_000;

    if (args.length == 3) {
      try {
        int index = 0;
        taskQueueSize = Integer.parseInt(args[index++]);
        threadCount = Integer.parseInt(args[index++]);
        numberOfTasks = Integer.parseInt(args[index++]);
      } catch (Exception e) {

      }
    }

    //TaskProcessor te = new TaskProcessor(taskQueueSize,  threadCount, Thread.MIN_PRIORITY, false);
    TaskProcessor te = TaskUtil.getDefaultTaskProcessor();

    runTest(te, new TaskProcessorTest(), numberOfTasks);
    //runTest( te, new TaskProcessorTest(), numberOfTasks);
    te.close();

    //ExecutorService executor = Executors.newFixedThreadPool(5);

    //UThread.sleep(20000);
  }

}