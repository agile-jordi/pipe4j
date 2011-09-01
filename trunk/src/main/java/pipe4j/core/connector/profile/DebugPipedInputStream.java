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

public class DebugPipedInputStream extends PipedInputStream implements Profiled {
	private long readWaitTimeMilliseconds = 0l;

	@Override
	public long getReadWaitTimeMilliseconds() {
		return readWaitTimeMilliseconds;
	}

	@Override
	public long getWriteWaitTimeMilliseconds() {
		return 0;
	}

	/*
	 * Other read methods end up calling this guy, which causes the wait in case
	 * buffer is empty
	 */
	@Override
	public int read() throws IOException {
		final long currentTime = System.currentTimeMillis();
		final int rv = super.read();
		readWaitTimeMilliseconds += (System.currentTimeMillis() - currentTime);
		return rv;
	}
}
