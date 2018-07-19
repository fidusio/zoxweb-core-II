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
package org.zoxweb.shared.net;

import java.io.IOException;
import java.util.Iterator;


public interface IPMapStore {
	
	public boolean map(String ipAddress, String macAddress);

	public String lookupMAC(String ipAddress) throws IOException;

	public boolean removeIP(String ipAddress);
	
	public void clear(boolean all);

	public Iterator<String> exclusions();
	
	public void addExclusion(String exclusion);
	
	public int size();
	
}