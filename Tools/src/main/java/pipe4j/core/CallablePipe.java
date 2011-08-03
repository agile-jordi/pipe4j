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

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.concurrent.Callable;

public class CallablePipe<I extends Closeable, O extends Closeable> implements
		Callable<Result> {
	private I in;
	private O out;
	private final Pipe<I, O> pipe;

	public CallablePipe(Pipe<I, O> pipe) {
		this.pipe = pipe;
	}

	public I getIn() {
		return in;
	}

	public void setIn(I in) {
		this.in = in;
	}

	public O getOut() {
		return out;
	}

	public void setOut(O out) {
		this.out = out;
	}

	public Pipe<I, O> getPipe() {
		return pipe;
	}

	@Override
	public Result call() throws Exception {
		try {
			if (pipe instanceof ConnectorDecorator) {
				ConnectorDecorator<I, O> decorator = (ConnectorDecorator<I, O>) pipe;
				this.in = decorator.decorateIn(in);
				this.out = decorator.decorateOut(out);
			}

			pipe.run(in, out);
		} catch (Exception e) {
			ResultImpl result = new ResultImpl(Result.Type.FAILURE);
			result.setException(e);
			return result;
		} finally {
			close(in);
			close(out);
		}

		return new ResultImpl(Result.Type.SUCCESS);
	}

	private void close(Closeable closeable) {
		if (closeable instanceof Flushable) {
			try {
				((Flushable) closeable).flush();
			} catch (IOException e) {
			}
		}

		try {
			closeable.close();
		} catch (IOException e1) {
		}
	}
}
