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

import java.util.List;

import pipe4j.core.Result.Type;

/**
 * DTO holding basic information of a pipeline execution.
 * 
 * @author bbennett
 */
public class PipelineInfo {
	private List<Result> resultList;
	private Exception exception;
	private boolean timeoutExceeded = false;
	private Result.Type result = Type.SUCCESS;
	private long startTimestamp;
	private long endTimestamp;

	public PipelineInfo(List<Result> resultList) {
		super();
		this.resultList = resultList;
	}
	
	public void setResult(Result.Type result) {
		this.result = result;
	}
	
	public Result.Type getResult() {
		return result;
	}

	public List<Result> getResultList() {
		return resultList;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public boolean hasError() {
		return exception != null;
	}

	public void setTimeoutExceeded(boolean timeoutExceeded) {
		this.timeoutExceeded = timeoutExceeded;
	}

	public boolean isTimeoutExceeded() {
		return timeoutExceeded;
	}

	public long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public long getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
}
