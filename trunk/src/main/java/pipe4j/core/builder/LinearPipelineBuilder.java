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

import java.util.List;

import pipe4j.core.CallablePipe;
import pipe4j.core.Pipe;

/**
 * Builder of linear pipelines, where each pipe communicates to at least one and
 * at most 2 other pipes.
 * 
 * @author bbennett
 */
public class LinearPipelineBuilder implements PipelineBuilder {
	private final boolean debug;
	private final Pipe[] pipeline;

	public LinearPipelineBuilder(Pipe[] pipeline) {
		this(false, pipeline);
	}

	public LinearPipelineBuilder(boolean debug, Pipe[] pipeline) {
		this.debug = debug;
		this.pipeline = pipeline;
	}

	@Override
	public List<CallablePipe> build() {
		if (pipeline == null || pipeline.length < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		NonLinearPipelineBuilder builder = new NonLinearPipelineBuilder(
				this.debug);
		Pipe previous = pipeline[0];
		for (Pipe pipe : pipeline) {
			if (pipe == pipeline[0])
				continue;
			builder.createObjectConnection(previous, pipe);
			builder.createStreamConnection(previous, pipe);
			previous = pipe;
		}

		return builder.build();
	}
}
