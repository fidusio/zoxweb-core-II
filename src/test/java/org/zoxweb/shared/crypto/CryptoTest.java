/*
 * Copyright (c) 2012-2014 ZoxWeb.com LLC.
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
package org.zoxweb.shared.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.zoxweb.server.security.CryptoUtil;
import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedStringUtil;

public class CryptoTest {

	public static void main( String[] args) {
		SecureRandom sr = null;

		try {
			sr = CryptoUtil.defaultSecureRandom();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println( sr.getAlgorithm());
		byte randomBytes[] = new byte[768/8];
		sr.nextBytes(randomBytes);

		for (int i=0 ; i < 20; i++) {
			long ts = System.nanoTime();
			sr.nextBytes(randomBytes);
			ts = System.nanoTime() - ts;
			System.out.println( ts+"\tnanos\t" + new String(SharedBase64.encode( randomBytes)) + ":" + SharedStringUtil.bytesToHex(randomBytes));
		}
	}

}