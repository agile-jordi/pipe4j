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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultImpl implements Result {
	private final Type type;
	private long startTimestamp;
	private long endTimestamp;
	private long readWaitTimeMilliseconds;
	private long writeWaitTimeMilliseconds;
	private final String pipeName;
	private List<Exception> exceptionList = new ArrayList<Exception>();

	public ResultImpl(String pipeName, Type type) {
		super();
		this.pipeName = pipeName;
		this.type = type;
	}

	@Override
	public String getPipeName() {
		return this.pipeName;
	}

	@Override
	public boolean hasException() {
		return !exceptionList.isEmpty();
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	@Override
	public long getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	@Override
	public long getReadWaitTimeMilliseconds() {
		return readWaitTimeMilliseconds;
	}

	public void setReadWaitTimeMilliseconds(long readWaitTimeMilliseconds) {
		this.readWaitTimeMilliseconds = readWaitTimeMilliseconds;
	}

	@Override
	public long getWriteWaitTimeMilliseconds() {
		return writeWaitTimeMilliseconds;
	}

	public void setWriteWaitTimeMilliseconds(long writeWaitTimeMilliseconds) {
		this.writeWaitTimeMilliseconds = writeWaitTimeMilliseconds;
	}
	
	public void addException(Exception exception) {
		this.exceptionList.add(exception);
	}

	public List<Exception> getExceptionList() {
		return Collections.unmodifiableList(this.exceptionList);
	}
	
	@Override
	public void aggregate(ExceptionWrapper exceptionWrapper) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean hasExceptions() {
		return !this.exceptionList.isEmpty();
	}
}