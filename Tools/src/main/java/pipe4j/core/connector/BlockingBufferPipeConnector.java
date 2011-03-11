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

import pipe4j.core.BlockingBuffer;
import pipe4j.core.BlockingBufferImpl;
import pipe4j.core.Null;
import pipe4j.core.PipeThread;

/**
 * Connect object pipes with a {@link BlockingBuffer}.
 * 
 * @author bbennett
 */
public class BlockingBufferPipeConnector extends AbstractPipeConnector {
	@Override
	protected boolean supports(Class<?> in, Class<?> out) {
		return BlockingBuffer.class.isAssignableFrom(in)
				&& BlockingBuffer.class.isAssignableFrom(out);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void connect(PipeThread pipe1, PipeThread pipe2) {
		BlockingBuffer<Object> queue = new BlockingBufferImpl<Object>();
		pipe1.setOut(queue);
		pipe2.setIn(queue);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void close(Object in, Object out) {
		BlockingBuffer bqi = (BlockingBuffer) in;
		bqi.clear();

		BlockingBuffer bqo = (BlockingBuffer) out;
		try {
			bqo.put(Null.INSTANCE);
		} catch (InterruptedException ignored) {
		}
	}
}
