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
package pipe4j.core.builder;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pipe4j.core.CallablePipe;
import pipe4j.core.ConnectionsImpl;
import pipe4j.core.Pipe;
import pipe4j.core.connector.BlockingBuffer;
import pipe4j.core.connector.BlockingBufferImpl;
import pipe4j.core.connector.profile.DebugBlockingBufferImpl;
import pipe4j.core.connector.profile.DebugPipedInputStream;
import pipe4j.core.connector.profile.DebugPipedOutputStream;

/**
 * Builder of non-linear pipelines, where each pipe can communicate with at
 * least one and at most an infinity number of other pipes.
 * 
 * @author bbennett
 */
public class NonLinearPipelineBuilder implements PipelineBuilder {
	private final boolean debug;
	private Map<Pipe, CallablePipe> pipeline = new HashMap<Pipe, CallablePipe>();

	public NonLinearPipelineBuilder() {
		this(false);
	}

	public NonLinearPipelineBuilder(boolean debug) {
		this.debug = debug;
	}

	public CallablePipe addPipe(Pipe pipe) {
		if (!pipeline.containsKey(pipe)) {
			CallablePipe callablePipe = new CallablePipe(pipe,
					new ConnectionsImpl());
			this.pipeline.put(pipe, callablePipe);
		}

		return pipeline.get(pipe);
	}

	public void createStreamConnection(Pipe source, Pipe sink) {
		createStreamConnection(source, DEFAULT_CONNECTION, sink,
				DEFAULT_CONNECTION);
	}

	public void createStreamConnection(Pipe source,
			String sourceConnectionName, Pipe sink, String sinkConnectionName) {
		CallablePipe sourceCallable = addPipe(source);
		CallablePipe sinkCallable = addPipe(sink);

		PipedInputStream pis = debug ? new DebugPipedInputStream()
				: new PipedInputStream();
		PipedOutputStream pos = null;
		try {
			pos = debug ? new DebugPipedOutputStream(pis)
					: new PipedOutputStream(pis);
		} catch (IOException wontHappen) {
		}

		if (sourceConnectionName == DEFAULT_CONNECTION) {
			sourceCallable.getConnections().setOutputStream(pos);
		} else {
			sourceCallable.getConnections().setNamedOutputStream(
					sourceConnectionName, pos);
		}

		if (sinkConnectionName == DEFAULT_CONNECTION) {
			sinkCallable.getConnections().setInputStream(pis);
		} else {
			sinkCallable.getConnections().setNamedInputStream(
					sinkConnectionName, pis);
		}

	}

	public void createObjectConnection(Pipe source, Pipe sink) {
		createObjectConnection(source, DEFAULT_CONNECTION, sink,
				DEFAULT_CONNECTION);
	}

	public void createObjectConnection(Pipe source,
			String sourceConnectionName, Pipe sink, String sinkConnectionName) {
		CallablePipe sourceCallable = addPipe(source);
		CallablePipe sinkCallable = addPipe(sink);

		BlockingBuffer buffer = debug ? new DebugBlockingBufferImpl()
				: new BlockingBufferImpl();

		if (sourceConnectionName == DEFAULT_CONNECTION) {
			sourceCallable.getConnections().setOutputBuffer(buffer);
		} else {
			sourceCallable.getConnections().setNamedOutputBuffer(
					sourceConnectionName, buffer);
		}

		if (sinkConnectionName == DEFAULT_CONNECTION) {
			sinkCallable.getConnections().setInputBuffer(buffer);
		} else {
			sinkCallable.getConnections().setNamedInputBuffer(
					sinkConnectionName, buffer);
		}
	}

	@Override
	public List<CallablePipe> build() {
		return Collections.unmodifiableList(new ArrayList<CallablePipe>(
				pipeline.values()));
	}
}
