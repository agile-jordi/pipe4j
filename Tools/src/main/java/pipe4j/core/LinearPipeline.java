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

import pipe4j.core.builder.LinearPipelineBuilder;
import pipe4j.core.executor.PipelineExecutionException;
import pipe4j.core.executor.PipelineExecutor;

/**
 * Utility class to assemble and execute pipelines.
 * 
 * @author bbennett
 */
public class LinearPipeline {
	public static PipelineInfo run(Pipe... pipeline)
			throws PipelineExecutionException {
		return run(0, pipeline);
	}

	public static PipelineInfo run(long timeoutMilliseconds, Pipe... pipeline)
			throws PipelineExecutionException {
		return run(timeoutMilliseconds, false, pipeline);
	}

	public static PipelineInfo run(long timeoutMilliseconds, boolean debug,
			Pipe... pipeline) throws PipelineExecutionException {
		List<CallablePipe> callables = new LinearPipelineBuilder(debug,
				pipeline).build();

		return PipelineExecutor.execute(timeoutMilliseconds, callables);
	}
}
