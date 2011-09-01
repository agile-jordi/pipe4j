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

import pipe4j.core.connector.BlockingBufferImpl;

public class DebugBlockingBufferImpl extends BlockingBufferImpl implements
		Profiled {
	private long readWaitTimeMilliseconds = 0l;
	private long writeWaitTimeMilliseconds = 0l;

	@Override
	public long getReadWaitTimeMilliseconds() {
		return readWaitTimeMilliseconds;
	}

	@Override
	public long getWriteWaitTimeMilliseconds() {
		return writeWaitTimeMilliseconds;
	}

	@Override
	public void put(Object o) throws InterruptedException {
		final long currentTime = System.currentTimeMillis();
		super.put(o);
		readWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}

	@Override
	public Object take() throws InterruptedException {
		final long currentTime = System.currentTimeMillis();
		Object rv = super.take();
		writeWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
		return rv;
	}
}
