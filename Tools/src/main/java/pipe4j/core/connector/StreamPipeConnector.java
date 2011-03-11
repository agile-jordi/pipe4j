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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import pipe4j.core.PipeThread;

/**
 * Connects stream pipes through a pair of connected {@link PipedInputStream}
 * and {@link PipedOutputStream}.
 * 
 * @author bbennett
 */
public class StreamPipeConnector extends AbstractPipeConnector {
	@Override
	protected boolean supports(Class<?> in, Class<?> out) {
		return in.isAssignableFrom(PipedInputStream.class)
				&& out.isAssignableFrom(PipedOutputStream.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void connect(PipeThread pipe1, PipeThread pipe2) {
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out;
		try {
			out = new PipedOutputStream(in);
		} catch (IOException e) {
			// will never happen
			throw new RuntimeException(e);
		}
		pipe1.setOut(out);
		pipe2.setIn(in);
	}

	@Override
	public void close(Object in, Object out) {
		PipedInputStream pis = (PipedInputStream) in;
		try {
			pis.close();
		} catch (IOException e) {
		}
		PipedOutputStream pos = (PipedOutputStream) out;

		try {
			pos.flush();
		} catch (IOException e) {
		}
		try {
			pos.close();
		} catch (IOException e) {
		}
	}
}
