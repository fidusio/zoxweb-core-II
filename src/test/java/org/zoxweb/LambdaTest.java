/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Tony Test Comment
 */


package org.zoxweb;

import java.util.Date;

public class LambdaTest
{
	interface toto
	{
		int add(int x, int y);
		default int sub(int x, int y)
		{
			return x-y;
		}
	}

	public static void main(String ...args)
	{


		toto t = (int x, int y) -> {return x+y;};

		Runnable r = () -> System.out.println("Tizi " + new Date() + " " + Thread.currentThread() );

		r.run();
		r.run();


		System.out.println("CurrentThread " + Thread.currentThread());
		System.out.println(t.add(1,2));
		System.out.println(t.sub(1,2));
		System.nanoTime();
		String s = "John";
		String s2 = "John";
		System.out.println((s==s2) + " " + s.hashCode() + " " + s2.hashCode());
		System.out.println((s==s2) + " " + new Object().hashCode() + " " + new Object().hashCode());

	}
}
