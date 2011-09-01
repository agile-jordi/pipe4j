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

import java.sql.PreparedStatement;

import pipe4j.core.connector.BlockingBuffer;
import pipe4j.pipe.SimpleObjectPipe;

/**
 * Inserts objects into database using the provided {@link PreparedStatement}.
 * 
 * @author bbennett
 */
public class PreparedStatementOut extends SimpleObjectPipe {
	private final PreparedStatement preparedStatement;
	private int batchSize = 10;
	private boolean commitBatch = false;
	private PreparedStatementObjectSetter setter = new GenericPreparedStatementObjectSetter();

	public PreparedStatementOut(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public PreparedStatementOut(PreparedStatement preparedStatement,
			int commitInterval, boolean commitBatch,
			PreparedStatementObjectSetter binder) {
		this(preparedStatement);
		if (commitInterval <= 0) {
			throw new IllegalArgumentException(
					"Commit interval must be higher than zero!");
		}
		this.batchSize = commitInterval;
		this.commitBatch = commitBatch;
		this.setter = binder;
	}

	@Override
	protected void run(BlockingBuffer inputBuffer, BlockingBuffer outputBuffer)
			throws Exception {
		Object obj;
		int count = 0;
		while (!cancelled() && (obj = inputBuffer.take()) != null) {
			this.setter.setValues(this.preparedStatement, obj);
			this.preparedStatement.addBatch();
			if (++count % this.batchSize == 0) {
				this.preparedStatement.executeBatch();
				if (this.commitBatch) {
					this.preparedStatement.getConnection().commit();
				}
			}
		}

		if (count % this.batchSize != 0) {
			this.preparedStatement.executeBatch();
		}

		if (this.commitBatch) {
			this.preparedStatement.getConnection().commit();
		}
	}
}
