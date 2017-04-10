package org.zoxweb;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorTest
	implements Runnable {

	private static volatile int counter = 0;
	
	
	public void run() {
		int index = counter++;
		System.out.println("Started " + index);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println( Thread.currentThread() + " finished " + index  +  " " + new Date());
	}
	
	public static void main(String[] args) {
		long ts = System.currentTimeMillis();
		ExecutorService pool = Executors.newCachedThreadPool();
		
		int count = 0;

		for (int i = 0; i < 100; i++) {
			pool.execute(new ExecutorTest());
		}

		pool.shutdown();

		try {
			while (!pool.awaitTermination(250, TimeUnit.MILLISECONDS)){
				System.out.println(++count);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("After shutdown " + (System.currentTimeMillis() - ts) + " "  + count);
	}

}