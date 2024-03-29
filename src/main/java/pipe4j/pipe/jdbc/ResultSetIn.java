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
package pipe4j.pipe.jdbc;

import java.sql.ResultSet;

import pipe4j.core.connector.BlockingBuffer;
import pipe4j.pipe.SimpleObjectPipe;

/**
 * Feeds pipeline with objects read from {@link ResultSet}.
 * 
 * @author bbennett
 */
public class ResultSetIn extends SimpleObjectPipe {
	private final ResultSet resultSet;
	private RecordTransformer recordTransformer;

	public ResultSetIn(ResultSet resultSet) {
		this(resultSet, new ObjectArrayRecordTransformer());
	}

	public ResultSetIn(ResultSet resultSet, RecordTransformer recordTransformer) {
		this.resultSet = resultSet;
		this.recordTransformer = recordTransformer;
	}

	@Override
	protected void run(BlockingBuffer inputBuffer, BlockingBuffer outputBuffer)
			throws Exception {
		while (!cancelled() && resultSet.next()) {
			outputBuffer.put(recordTransformer.transformRecord(resultSet));
		}
	}
}
