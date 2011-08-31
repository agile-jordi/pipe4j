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

import pipe4j.core.connector.BlockingBuffer;

/**
 * Pojo implementation of the Connections interface.
 * 
 * @author bbennett
 */
public class ConnectionsImpl implements Connections {
	private OutputStream outputStream;
	private InputStream intputStream;
	private BlockingBuffer inputBuffer;
	private BlockingBuffer outputBuffer;

	public ConnectionsImpl() {
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public InputStream getIntputStream() {
		return intputStream;
	}

	public void setIntputStream(InputStream intputStream) {
		this.intputStream = intputStream;
	}

	@Override
	public BlockingBuffer getInputBuffer() {
		return inputBuffer;
	}

	public void setInputBuffer(BlockingBuffer inputBuffer) {
		this.inputBuffer = inputBuffer;
	}

	@Override
	public BlockingBuffer getOutputBuffer() {
		return outputBuffer;
	}

	public void setOutputBuffer(BlockingBuffer outputBuffer) {
		this.outputBuffer = outputBuffer;
	}

	@Override
	public void close() throws IOException {
		IOException firstException = null;
		if (this.outputBuffer != null) {
			try {
				this.outputBuffer.close();
			} catch (IOException ioe) {
				firstException = ioe;
			}
		}

		if (this.outputStream != null) {
			try {
				this.outputStream.flush();
			} catch (IOException ioe) {
				if (firstException == null)
					firstException = ioe;
			}

			try {
				this.outputStream.close();
			} catch (IOException ioe) {
				if (firstException == null)
					firstException = ioe;
			}
		}

		if (this.intputStream != null) {
			try {
				this.intputStream.close();
			} catch (IOException ioe) {
				if (firstException == null)
					firstException = ioe;
			}
		}

		if (firstException != null)
			throw firstException;
	}
}
