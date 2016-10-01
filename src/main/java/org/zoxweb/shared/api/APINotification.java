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


/**
 * The API notification interface which extends API service provider interface.
 * @author mzebib
 *
 * @param <V>
 */
public interface APINotification<V>
	extends APIServiceProvider<V>
{	
	/**
	 * This method sends a message and returns transaction information.
	 * @param message
	 * @param apind NOW the message will be send at the message return, QUEUED the message will be queued and sent later
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws APIException
	 */
	public APITransactionInfo sendAPIMessage(APIMessage message, APINotificationDelivery apind) 
			throws NullPointerException, IllegalArgumentException, APIException;
	
	/**
	 * This method updates transaction information.
	 * @param tansaction
	 * @return
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws APIException
	 */
	public APITransactionInfo updateTransactionInfo(APITransactionInfo transaction)  
			throws NullPointerException, IllegalArgumentException, APIException;
	
}
