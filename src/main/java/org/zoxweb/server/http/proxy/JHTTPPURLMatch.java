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
package org.zoxweb.server.http.proxy;

@SuppressWarnings("serial")
public class JHTTPPURLMatch
    implements java.io.Serializable
{
	

	String match;

	String desc;

	boolean cookies_enabled;

	int actionindex;

	public JHTTPPURLMatch(String match, boolean cookies_enabled,
			int actionindex, String description) {
		this.match = match;
		this.cookies_enabled = cookies_enabled;
		this.actionindex = actionindex;
		this.desc = description;
	}

	public String getMatch() {
		return match;
	}

	public boolean getCookiesEnabled() {
		return cookies_enabled;
	}

	public int getActionIndex() {
		return actionindex;
	}

	public String getDescription() {
		return desc;
	}

	public String toString() {
		return "\"" + match + "\" " + desc;
	}
}
