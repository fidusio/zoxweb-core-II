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
package org.zoxweb.shared.util;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {

	public static void main(String[] arg) {
		byte[] num = {0, 1, 2};

		String toConvert = "Man is distinguished, not only by his reason, but by this singular passion from other animals, which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable generation of knowledge, exceeds the short vehemence of any carnal pleasure.";

		System.out.println(toConvert.length());
		//System.out.println(SharedBase64.computeBase64ArraySize(num));
		System.out.println(new String(SharedBase64.encode(num)));

		long tsOld = System.nanoTime();
		byte[] oldBase64 = SharedBase64.encode(toConvert.getBytes());

		byte[] decodedBase64 = SharedBase64.decode(oldBase64);

		tsOld = System.nanoTime() - tsOld;
		System.out.println(oldBase64.length);
		System.out.println(new String(oldBase64));
		System.out.println(new String(decodedBase64));
		System.out.println(tsOld + "nanos");

		String[] testArray = {"1", "12", "123", "1234", "12345", "123456", "1234567", "12345678", "123456789", "1234567890",
				"a", "ab", "abc", "abcd", "abcde", "abcdef", "abcdefg", "abcdefgh", "abcdefghi", "abcdefghij", toConvert, "asure.", "sure.", "any carnal pleasure"};

		String partial = "naelmarwan";


		System.out.println("Length of Array: " + testArray.length);

		for (int i = 0; i < testArray.length; i++) {
			System.out.println(i + 1 + "." + "Original Value: " + testArray[i]);

			byte toEncode[] = testArray[i].getBytes();
			long ts = System.nanoTime();
			byte[] sharedEncodedeBase64 = SharedBase64.encode(toEncode);
			byte[] sharedDecodedBase64 = SharedBase64.decode(sharedEncodedeBase64);
			ts = System.nanoTime() - ts;

			long ts1 = System.nanoTime();
			byte apache64Encoded[] = Base64.encodeBase64(toEncode);//.encodeBase64(toEncode);
			byte apache64Decodded[] = Base64.decodeBase64(apache64Encoded);
			ts1 = System.nanoTime() - ts1;

			long ts2 = System.nanoTime();
			byte java64Encoded[] = java.util.Base64.getEncoder().encode(toEncode);//.encodeBase64(toEncode);
			byte java64Decodded[] = java.util.Base64.getDecoder().decode(java64Encoded);
			ts2 = System.nanoTime() - ts2;

			System.out.println(i + 1 + "." + "Encode Base64: " + new String(sharedEncodedeBase64) + ":\t\t" + (Arrays.equals(sharedEncodedeBase64, apache64Encoded)
					&& Arrays.equals(sharedEncodedeBase64, java64Encoded)));
			System.out.println(i + 1 + "." + "Decoded Base64: " + new String(sharedDecodedBase64) + ":\t\t" + (Arrays.equals(sharedDecodedBase64, apache64Decodded)
					&& Arrays.equals(sharedDecodedBase64, java64Decodded)));
			System.out.println("zoxweb:" + ts + " apache:" + ts1 + " java:" + ts2 + " delta:" + (ts1 - ts) + " factor:" + ((float) ts1 / (float) ts) + "," + ((float) ts2 / (float) ts));
			System.out.println(ts + " nanos: " + testArray[i].equals(new String(sharedDecodedBase64)));
		}

		byte[] byteArray = new byte[256];

		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = (byte) i;
		}

		byte[] byteArrayBase64 = SharedBase64.encode(byteArray);
		System.out.println(new String(byteArrayBase64));
		System.out.println("Original Array length:" + byteArray.length + " base 64 length:" + byteArrayBase64.length);
		byte[] byteArrayOriginal = SharedBase64.decode(byteArrayBase64);
		System.out.println(Arrays.equals(byteArray, byteArrayOriginal));

		System.out.println("BASE_64 length:" + SharedBase64.BASE_64.length + " REVERSE_BASE_64 length:" + SharedBase64.REVERSE_BASE_64.length);

		System.out.println("Partial test:" + new String(SharedBase64.encode(partial.getBytes(), 4, 6)) + "," + new String(SharedBase64.encode(partial.getBytes(), 1, 1)));
		byte fullname[] = SharedBase64.encode(partial.getBytes());
		System.out.println("Marwan " + new String(fullname));

		System.out.println("Equals:" + SharedUtil.slowEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".getBytes(), SharedBase64.BASE_64));
	}

}