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

import java.io.Closeable;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import pipe4j.core.CallablePipe;
import pipe4j.core.connector.profile.DebugPipedInputStream;
import pipe4j.core.connector.profile.DebugPipedOutputStream;

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

	@Override
	public void connect(CallablePipe<Closeable, Closeable> pipe1,
			CallablePipe<Closeable, Closeable> pipe2, boolean debug) {
		PipedInputStream in = debug ? new DebugPipedInputStream()
				: new PipedInputStream();
		PipedOutputStream out;
		try {
			out = debug ? new DebugPipedOutputStream(in)
					: new PipedOutputStream(in);
		} catch (IOException e) {
			// will never happen
			throw new RuntimeException(e);
		}
		pipe1.setOut(out);
		pipe2.setIn(in);
	}
}
