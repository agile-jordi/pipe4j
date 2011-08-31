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

import java.util.concurrent.Callable;

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

	@Override
	public Result call() throws Exception {
		ResultImpl result;
		long endTimestamp, startTimestamp = 0;
		String pipeName = pipe.getClass().getSimpleName();
		try {
			startTimestamp = System.currentTimeMillis();
			pipe.run(this.connections);
			endTimestamp = System.currentTimeMillis();
			result = new ResultImpl(pipeName, Result.Type.SUCCESS);
		} catch (Exception e) {
			endTimestamp = System.currentTimeMillis();
			result = new ResultImpl(pipeName, Result.Type.FAILURE);
			result.setException(e);
		} finally {
			this.connections.close();
		}

		result.setStartTimestamp(startTimestamp);
		result.setEndTimestamp(endTimestamp);

		return result;
	}
}
