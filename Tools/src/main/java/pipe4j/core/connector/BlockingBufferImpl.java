/*
 * Copyright (C) 2010 Bernardo O. Bennett
 * 
 * This file is part of Pipe4j.
 * 
 * Pipe4j is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Pipe4j is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with Pipe4j. If not, see <http://www.gnu.org/licenses/>.
 */
package pipe4j.core.connector;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Standard implementation of the BlockingBuffer interface.
 * 
 * @author bbennett
 * 
 */
public class BlockingBufferImpl implements BlockingBuffer {
	/**
	 * Used to flag null, as the underlying queue does not support nulls.
	 */
	private Object NULL = new Object();

	/**
	 * True if buffer is closed and should not accept any more objects.
	 */
	private AtomicBoolean closed = new AtomicBoolean(false);

	/**
	 * Underlying blocking queue.
	 */
	private BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(1);

	@Override
	public void put(Object o) throws InterruptedException {
		if (this.closed.get()) {
			throw new IllegalStateException();
		}
		if (o == null) {
			queue.put(NULL);
		} else {
			queue.put(o);
		}
	}

	@Override
	public Object take() throws InterruptedException {
		Object take = queue.take();
		if (take == NULL) {
			return null;
		}

		return take;
	}

	@Override
	public void close() throws IOException {
		if (this.closed.get()) {
			return;
		}

		try {
			put(null);
		} catch (InterruptedException e) {
		}
		this.closed.set(true);
	}
}
