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
import java.util.concurrent.Callable;

import pipe4j.core.connector.profile.Profiled;

/**
 * Wraps pipe and connections; implements Callable interface so it can be
 * submitted to an Executor. This class should not be instantiated directly;
 * prefer using a pipeline builder instead.
 * 
 * @author bbennett
 */
public class CallablePipe implements Callable<Result> {
	private final Pipe pipe;
	private final Connections connections;

	public CallablePipe(Pipe pipe, Connections connections) {
		this.pipe = pipe;
		this.connections = connections;
	}

	public void cancel() {
		this.pipe.cancel();
	}

	public Connections getConnections() {
		return connections;
	}

	@Override
	public Result call() {
		String pipeName = pipe.getClass().getSimpleName();
		ResultImpl result = new ResultImpl(pipeName, Result.Type.FAILURE);
		long endTimestamp, startTimestamp = 0;

		try {
			try {
				startTimestamp = System.currentTimeMillis();
				pipe.run(this.connections);
				endTimestamp = System.currentTimeMillis();
				result = new ResultImpl(pipeName, Result.Type.SUCCESS);
			} catch (Exception e) {
				endTimestamp = System.currentTimeMillis();
				result.addException(e);
			} finally {
				try {
					this.connections.close();
				} catch (IOException e) {
					result.addException(e);
				}
			}

			result.setStartTimestamp(startTimestamp);
			result.setEndTimestamp(endTimestamp);

			long readWaitTimeMilliseconds = result
					.getReadWaitTimeMilliseconds();
			long writeWaitTimeMilliseconds = result
					.getWriteWaitTimeMilliseconds();

			if (connections.getIntputStream() instanceof Profiled) {
				readWaitTimeMilliseconds += ((Profiled) connections
						.getIntputStream()).getReadWaitTimeMilliseconds();
			}

			if (connections.getInputBuffer() instanceof Profiled) {
				readWaitTimeMilliseconds += ((Profiled) connections
						.getInputBuffer()).getReadWaitTimeMilliseconds();
			}

			if (connections.getOutputStream() instanceof Profiled) {
				writeWaitTimeMilliseconds += ((Profiled) connections
						.getOutputStream()).getWriteWaitTimeMilliseconds();
			}

			if (connections.getOutputBuffer() instanceof Profiled) {
				writeWaitTimeMilliseconds += ((Profiled) connections
						.getOutputBuffer()).getWriteWaitTimeMilliseconds();
			}

			result.setReadWaitTimeMilliseconds(readWaitTimeMilliseconds);
			result.setWriteWaitTimeMilliseconds(writeWaitTimeMilliseconds);
		} catch (Exception unexpected) {
			result.addException(unexpected);
		}
		return result;
	}
}
