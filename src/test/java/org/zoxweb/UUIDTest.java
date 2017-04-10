package org.zoxweb;

import java.util.UUID;

public class UUIDTest {

	public static void main(String[] args) {
		long most  = System.nanoTime();
		long least = System.nanoTime();
		int size = 10;
		long listNanos[] = new long[size];
		
		for (int i = 0; i < size; i++) {
			listNanos[i] =System.nanoTime();
		}
		
		UUID uuid = new UUID(most, least);
		
		System.out.println( uuid );
		System.out.println("most\t:" + most);
		System.out.println("least\t:" + least);

		long last = 0L;

		for (long n : listNanos) {
			System.out.println( n + " last equals " + ( last == n) + " " + UUID.randomUUID() + " " + Long.toHexString(n));
			last = n;
		}

		 uuid = UUID.fromString("94ecdf50-cdb9-4668-941b-6005108b4840");
		 System.out.println( uuid + " most " + uuid.getMostSignificantBits() + " least " + uuid.getLeastSignificantBits());
	}

}