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