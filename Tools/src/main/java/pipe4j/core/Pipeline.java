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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

import pipe4j.core.connector.BlockingBuffer;
import pipe4j.core.connector.BlockingBufferImpl;
import pipe4j.core.connector.profile.DebugBlockingBufferImpl;
import pipe4j.core.connector.profile.DebugPipedInputStream;
import pipe4j.core.connector.profile.DebugPipedOutputStream;
import pipe4j.core.executor.PipelineExecutor;

/**
 * Utility class to assemble and execute pipelines.
 * 
 * @author bbennett
 */
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

		List<CallablePipe> callables = new ArrayList<CallablePipe>(
				pipeline.length);

		ConnectionsImpl previousConnections = new ConnectionsImpl();
		CallablePipe previous = new CallablePipe(pipeline[0],
				previousConnections);
		callables.add(previous);

		for (Pipe pipe : pipeline) {
			if (pipe == pipeline[0])
				continue;
			ConnectionsImpl connections = new ConnectionsImpl();
			connectDefaultChannels(previousConnections, connections, debug);
			CallablePipe callablePipe = new CallablePipe(pipe, connections);
			callables.add(callablePipe);
			previous = callablePipe;
			previousConnections = connections;
		}

		return PipelineExecutor.execute(timeoutMilliseconds, callables);
	}

	private static void connectDefaultChannels(ConnectionsImpl c1,
			ConnectionsImpl c2, boolean debug) {
		PipedInputStream pis = debug ? new DebugPipedInputStream()
				: new PipedInputStream();
		PipedOutputStream pos = null;
		try {
			pos = debug ? new DebugPipedOutputStream(pis)
					: new PipedOutputStream(pis);
		} catch (IOException wontHappen) {
		}
		c1.setOutputStream(pos);
		c2.setIntputStream(pis);
		BlockingBuffer buffer = debug ? new DebugBlockingBufferImpl()
				: new BlockingBufferImpl();

		c1.setOutputBuffer(buffer);
		c2.setInputBuffer(buffer);
	}
}
