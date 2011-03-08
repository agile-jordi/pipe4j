package pipe4j.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingBufferImpl<E> implements BlockingBuffer<E> {
	private BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(1);

	@Override
	public void put(E e) throws InterruptedException {
		if (e == null) {
			queue.put(Null.INSTANCE);
		} else {
			queue.put(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E take() throws InterruptedException {
		Object take = queue.take();
		if (take == Null.INSTANCE) {
			return null;
		}

		return (E) take;
	}

	@Override
	public void clear() {
		queue.clear();
	}
}
