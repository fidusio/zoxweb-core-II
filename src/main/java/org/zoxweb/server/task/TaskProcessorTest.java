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

    for (int i = 0; i < numberOfTasks; i++) {
      tp.execute(td);
    }

    delta = TaskUtil.waitIfBusyThenClose(25) - delta;

    System.out.println(
        "It took " + Const.TimeInMillis.toString(delta) + " millis callback " + td + " using queue "
            + tp.getQueueMaxSize() + " and " + tp.availableExecutorThreads() + " executor thread");
    System.out.println("Available thread " + tp.availableExecutorThreads() + " total "
        + ((TaskProcessorTest) td).counter + ":" + ((TaskProcessorTest) td).ai.get());
  }

  public static void main(String[] args) {
    int numberOfTasks = 20_000_000;
    TaskProcessor te = TaskUtil.getDefaultTaskProcessor();
    runTest(te, new TaskProcessorTest(), numberOfTasks);
  }

}