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
package org.zoxweb.shared.queue;

import java.io.IOException;

import org.zoxweb.shared.util.GetNVGenericMap;
import org.zoxweb.shared.util.NVGenericMap;

/**
 * The queue session interface.
 */
public interface QueueSession
    extends AutoCloseable, GetNVGenericMap
{
	
	
	void connect(NVGenericMap config)
			throws NullPointerException, IllegalArgumentException, IOException;
	
	
	
	
	/**
	 * Adds consumer listener.
	 * @param ql
	 */
	void addConsumerListener(String tag, QueueListener<QueueEvent<?>> ql);

	/**
	 * Removes consumer listener.
	 * @param ql
	 */
	void removeConsumerListener(QueueListener<QueueEvent<?>> ql);
	void removeConsumerListener(String tag);

	/**
	 * Dispatches producer event.
	 * @param qe
	 */
	void dispatchProducerEvent(QueueEvent<?> qe);
	
	QueueListener<QueueEvent<?>> [] getAllListeners();

}