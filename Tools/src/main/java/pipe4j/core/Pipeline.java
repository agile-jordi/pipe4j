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
import java.util.ArrayList;
import java.util.List;

import pipe4j.core.connector.PipeConnectorHelper;
import pipe4j.core.executor.PipelineExecutor;

/**
 * Utility class to assemble and execute pipelines.
 * 
 * @author bbennett
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Pipeline {
	public static PipelineInfo run(Pipe... pipeline) {
		return run(0, pipeline);
	}

	public static PipelineInfo run(long timeoutMilliseconds, Pipe... pipeline) {
		return run(timeoutMilliseconds, false, pipeline);
	}

	public static PipelineInfo run(long timeoutMilliseconds, boolean debug,
			Pipe... pipeline) {
		if (pipeline == null || pipeline.length < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		List<CallablePipe<Closeable, Closeable>> callables = new ArrayList<CallablePipe<Closeable, Closeable>>(
				pipeline.length);
		CallablePipe<Closeable, Closeable> previous = new CallablePipe<Closeable, Closeable>(
				pipeline[0]);
		callables.add(previous);
		previous.setIn(Null.INSTANCE);

		for (Pipe<Closeable, Closeable> pipe : pipeline) {
			if (pipe == pipeline[0])
				continue;
			CallablePipe<Closeable, Closeable> callablePipe = new CallablePipe<Closeable, Closeable>(
					pipe);
			callables.add(callablePipe);
			connect(previous, callablePipe, debug);
			previous = callablePipe;
		}
		previous.setOut(Null.INSTANCE);

		return PipelineExecutor.execute(timeoutMilliseconds, callables);
	}

	private static void connect(CallablePipe<Closeable, Closeable> previous,
			CallablePipe<Closeable, Closeable> callablePipe, boolean debug) {
		PipeConnectorHelper.connect(previous, callablePipe, debug);
	}
}
