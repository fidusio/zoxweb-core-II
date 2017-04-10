/*
 * Copyright (c) 2012-2015 ZoxWeb.com LLC.
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

import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPHeaderValue;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

public class StringFormatTest {

	public static void main(String[] args) {
		try {
			String[][] params = {
					{HTTPMimeType.APPLICATION_JSON.getValue(), HTTPHeaderValue.CHARSET_UTF8.getValue()},
					null,
					{"", "mara", "fdr", " ", "test"},
			};

			for (String values[] : params) {
				try {
					System.out.println(SharedStringUtil.formatStringValues("; ", values));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(SharedStringUtil.formatStringValues("; ", HTTPMimeType.APPLICATION_JSON, HTTPHeaderValue.CHARSET_UTF8));

			String toBeParsed[] = {
					"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0",
					"Proxy-Connection: keep-alive",
					"Connection: keep-alive",
					"Host: comet.yahoo.com:443",
					"Accept:",
				};
			
			for (String str : toBeParsed) {
				//System.out.println(SharedUtil.toNVPair(str, ":"));
				System.out.println(SharedUtil.toNVPair(str, ":", true));
				System.out.println(SharedUtil.toNVPair(str, ":", false));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}