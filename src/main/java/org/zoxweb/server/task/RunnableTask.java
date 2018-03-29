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
package org.zoxweb.server.task;

public abstract class RunnableTask
		implements TaskExecutor, Runnable {

	static class RunnableTaskContainer extends RunnableTask {
		
		private final Runnable runnable;
		
		RunnableTaskContainer(Runnable runnable)
		{
			this.runnable = runnable;
		}
		
		
	    @Override
		public void run() {
	    	
	    	runnable.run();
//			TaskEvent te = attachedEvent();
//			((Runnable)te.getTaskExecutorParameters()[0]).run();
		}
	}

	protected TaskEvent te;

	@Override
	public void executeTask(TaskEvent event) {
		this.te = event;
		run();
	}

	@Override
	public void finishTask(TaskEvent event) {

	}
	
	protected TaskEvent attachedEvent() {
		return te;
	}

}