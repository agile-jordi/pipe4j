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
package pipe4j.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Thread specialized in executing pipes.
 * 
 * @author bbennett
 * 
 * @param <I>
 *            Pipe input type
 * @param <O>
 *            Pipe output type
 */
public class PipeThread<I, O> extends Thread {
	private I in;
	private O out;
	private final Pipe<I, O> pipe;
	private Exception exception;

	public PipeThread(Pipe<I, O> pipe) {
		super();
		this.pipe = pipe;
	}

	public void setIn(I in) {
		this.in = in;
	}

	public void setOut(O out) {
		this.out = out;
	}

	public Pipe<I, O> getPipe() {
		return pipe;
	}

	public boolean hasError() {
		return this.exception != null;
	}

	public Exception getException() {
		return exception;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			if (pipe instanceof ConnectorDecorator) {
				this.in = ((ConnectorDecorator<I, O>) pipe).decorateIn(this.in);
				this.out = ((ConnectorDecorator<I, O>) pipe)
						.decorateOut(this.out);
			}
			pipe.run(in, out);
		} catch (Exception e) {
			this.exception = e;
		} finally {
			close();
		}
	}

	public void close() {
		closeIn();
		closeOut();
	}

	private void closeIn() {
		if (this.in instanceof InputStream) {
			InputStream is = (InputStream) this.in;
			try {
				is.close();
			} catch (IOException e) {
			}
		} else if (this.in instanceof BlockingBuffer) {
			BlockingBuffer<?> buffer = (BlockingBuffer<?>) this.in;
			buffer.clear();
		}
	}

	private void closeOut() {
		if (this.out instanceof OutputStream) {
			OutputStream os = (OutputStream) this.out;
			try {
				os.flush();
			} catch (IOException e) {
			}
			try {
				os.close();
			} catch (IOException e) {
			}
		} else if (this.out instanceof BlockingBuffer) {
			@SuppressWarnings("unchecked")
			BlockingBuffer<Object> queue = (BlockingBuffer<Object>) this.out;
			try {
				queue.put(null);
			} catch (InterruptedException ignored) {
			}
		}
	}
}
