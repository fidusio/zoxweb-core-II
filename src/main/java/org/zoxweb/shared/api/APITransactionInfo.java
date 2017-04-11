/*
 * Copyright (c) 2012-May 27, 2014 ZoxWeb.com LLC.
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
package org.zoxweb.shared.api;
import org.zoxweb.shared.util.RemoteID;
/**
 * The API transaction information interface.
 * @author mzebib
 */
public interface APITransactionInfo
        extends RemoteID<String> {

	/**
	 * Returns the message.
	 * @return APINotificationMessage
	 */
	public APINotificationMessage getMessage();
	
	/**
	 * Sets the message.
	 * @param message
	 */
	public void setMessage(APINotificationMessage message);
	
	
	/**
	 * Returns the delivery status.
	 * @return APINotificationStatus
	 */
	public APINotificationStatus getDeliverStatus();
	
	/**
	 * Sets the delivery status.
	 * @param status
	 */
	public void setDeliverStatus(APINotificationStatus status);

	/**
	 * Returns the updated time stamp.
	 * @return the last itme updated in miilis
	 */
	public long getUpdateTimeStamp();
	
	/**
	 * Sets the updated time stamp.
	 * @param ts
	 * @throws IllegalArgumentException
	 */
	public void setUpdateTimeStamp(long ts) 
			throws IllegalArgumentException;

	/**
	 * Returns the created time stamp.
	 * @return creation time stamp in millis.
	 */
	public long getCreateTimeStamp();
	
	/**
	 * Sets the created time stamp.
	 * @param ts
	 * @throws IllegalArgumentException
	 */
	public void setCreateTimeStamp(long ts)
            throws IllegalArgumentException;

}