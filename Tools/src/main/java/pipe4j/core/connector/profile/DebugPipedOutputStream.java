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
package pipe4j.core.connector.profile;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class DebugPipedOutputStream extends PipedOutputStream implements
		Profiled {
	private long writeWaitTimeMilliseconds = 0l;

	public DebugPipedOutputStream(PipedInputStream in) throws IOException {
		super(in);
	}

	@Override
	public long getReadWaitTimeMilliseconds() {
		return 0;
	}

	@Override
	public long getWriteWaitTimeMilliseconds() {
		return writeWaitTimeMilliseconds;
	}

	@Override
	public void write(byte[] b, int off, int len)
			throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b, off, len);
		writeWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}

	@Override
	public void write(int b) throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b);
		writeWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}
}
