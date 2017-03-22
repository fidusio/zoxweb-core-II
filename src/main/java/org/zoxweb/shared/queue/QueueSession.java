package org.zoxweb.shared.queue;

public interface QueueSession
	extends AutoCloseable
{
	public void addConsumerListener(QueueListener<QueueEvent> ql);
	public void removeConsumerListener(QueueListener<QueueEvent> ql);
	
	public void dispatchProducerEvent(QueueEvent qe);
}
