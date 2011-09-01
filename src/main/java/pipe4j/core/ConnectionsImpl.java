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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pipe4j.core.connector.BlockingBuffer;

/**
 * Pojo implementation of the Connections interface.
 * 
 * @author bbennett
 */
public class ConnectionsImpl implements Connections {
	private static final String DEFAULT_STREAM = "default_stream";
	private static final String DEFAULT_BUFFER = "default_buffer";
	private static final String INPUT_PREFIX = "input_";
	private static final String OUTPUT_PREFIX = "output_";

	private Map<String, Closeable> closeableMap = Collections
			.synchronizedMap(new HashMap<String, Closeable>());

	public ConnectionsImpl() {
	}

	@Override
	public OutputStream getOutputStream() {
		return getNamedOutputStream(DEFAULT_STREAM);
	}

	@Override
	public void setOutputStream(OutputStream outputStream) {
		setNamedOutputStream(DEFAULT_STREAM, outputStream);
	}

	@Override
	public InputStream getIntputStream() {
		return getNamedInputStream(DEFAULT_STREAM);
	}

	@Override
	public void setInputStream(InputStream inputStream) {
		setNamedInputStream(DEFAULT_STREAM, inputStream);
	}

	@Override
	public BlockingBuffer getInputBuffer() {
		return getNamedInputBuffer(DEFAULT_BUFFER);
	}

	@Override
	public void setInputBuffer(BlockingBuffer inputBuffer) {
		setNamedInputBuffer(DEFAULT_BUFFER, inputBuffer);
	}

	@Override
	public void setNamedInputBuffer(String name, BlockingBuffer inputBuffer) {
		put(INPUT_PREFIX + name, inputBuffer);
	}

	@Override
	public BlockingBuffer getOutputBuffer() {
		return getNamedOutputBuffer(DEFAULT_BUFFER);
	}

	@Override
	public void setOutputBuffer(BlockingBuffer outputBuffer) {
		setNamedOutputBuffer(DEFAULT_BUFFER, outputBuffer);
	}

	@Override
	public void setNamedOutputBuffer(String name, BlockingBuffer outputBuffer) {
		put(OUTPUT_PREFIX + name, outputBuffer);
	}

	@Override
	public void close() throws IOException {
		IOException firstException = null;

		synchronized (closeableMap) {
			for (Closeable closeable : closeableMap.values()) {
				if (closeable != null) {

					synchronized (closeable) {
						if (closeable instanceof Flushable) {
							try {
								((Flushable) closeable).flush();
							} catch (IOException ioe) {
								if (firstException == null)
									firstException = ioe;
							}
						}

						try {
							closeable.close();
						} catch (IOException ioe) {
							if (firstException == null)
								firstException = ioe;
						}
					}
				}
			}
		}

		if (firstException != null)
			throw firstException;
	}

	@Override
	public OutputStream getNamedOutputStream(String name) {
		return (OutputStream) this.closeableMap.get(OUTPUT_PREFIX + name);
	}

	@Override
	public void setNamedOutputStream(String name, OutputStream outputStream) {
		put(OUTPUT_PREFIX + name, outputStream);
	}

	@Override
	public InputStream getNamedInputStream(String name) {
		return (InputStream) this.closeableMap.get(INPUT_PREFIX + name);
	}

	@Override
	public void setNamedInputStream(String name, InputStream inputStream) {
		put(INPUT_PREFIX + name, inputStream);
	}

	@Override
	public BlockingBuffer getNamedInputBuffer(String name) {
		return (BlockingBuffer) this.closeableMap.get(INPUT_PREFIX + name);
	}

	@Override
	public BlockingBuffer getNamedOutputBuffer(String name) {
		return (BlockingBuffer) this.closeableMap.get(OUTPUT_PREFIX + name);
	}

	private void put(String name, Closeable closeable) {
		synchronized (closeableMap) {
			if (this.closeableMap.containsKey(name)) {
				throw new IllegalArgumentException("Connection \"" + name
						+ "\" already defined");
			}
			this.closeableMap.put(name, closeable);
		}
	}
}
