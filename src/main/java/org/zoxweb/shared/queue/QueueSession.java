package org.zoxweb.shared.queue;

/**
 * The queue session interface.
 */
public interface QueueSession
    extends AutoCloseable
{
	/**
	 * Adds consumer listener.
	 * @param ql
	 */
	public void addConsumerListener(QueueListener<QueueEvent> ql);

	/**
	 * Removes consumer listener.
	 * @param ql
	 */
	public void removeConsumerListener(QueueListener<QueueEvent> ql);

	/**
	 * Dispatches producer event.
	 * @param qe
	 */
	public void dispatchProducerEvent(QueueEvent qe);

}