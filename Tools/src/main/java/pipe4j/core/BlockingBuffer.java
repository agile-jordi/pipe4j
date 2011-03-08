package pipe4j.core;

/**
 * Ordered blocking buffer that supports null values for the purpose of flagging
 * that no more elements will be added to the buffer.
 * 
 * @author bbennett
 * 
 * @param <E>
 *            Element type
 */
public interface BlockingBuffer<E> {
	/**
	 * Put element into buffer, waiting if necessary for space to become
	 * available.
	 * 
	 * @param e
	 *            the element to add
	 * @throws InterruptedException
	 *             if interrupted while waiting
	 */
	void put(E e) throws InterruptedException;

	/**
	 * Removes element from buffer, waiting if necessary until an element
	 * becomes available.
	 * 
	 * @return the head of this queue
	 * @throws InterruptedException
	 *             if interrupted while waiting
	 */
	E take() throws InterruptedException;

	/**
	 * Removes all elements from buffer.
	 */
	void clear();
}
