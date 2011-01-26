/*
 * Copyright (C) 2010 Bernardo O. Bennett
 * 
 * This file is part of Stream4j.
 * 
 * Stream4j is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Stream4j is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with Stream4j. If not, see <http://www.gnu.org/licenses/>.
 */
package pipe4j.pipe.jdbc;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

import pipe4j.core.Null;
import pipe4j.pipe.AbstractPipe;

public class PreparedStatementOut extends
		AbstractPipe<BlockingQueue<Object>, Null> {
	private final PreparedStatement preparedStatement;
	private int batchSize = 10;
	private boolean commitBatch = false;

	public PreparedStatementOut(PreparedStatement preparedStatement) {
		super();
		this.preparedStatement = preparedStatement;
	}

	public PreparedStatementOut(PreparedStatement preparedStatement,
			int commitInterval, boolean commitBatch) {
		super();
		this.preparedStatement = preparedStatement;
		if (commitInterval <= 0) {
			throw new IllegalArgumentException(
					"Commit interval must be higher than zero!");
		}
		this.batchSize = commitInterval;
		this.commitBatch = commitBatch;
	}

	@Override
	public void run(BlockingQueue<Object> in, Null out) throws Exception {
		Object obj;
		int count = 0;
		while (!cancelled() && !((obj = in.take()) instanceof Null)) {
			if (obj instanceof Object[]) {
				Object[] row = (Object[]) obj;
				for (int i = 0; i < row.length; i++) {
					this.preparedStatement.setObject(i + 1, row[i]);
				}
			} else if (obj instanceof Collection) {
				Collection<?> row = (Collection<?>) obj;
				int i = 1;
				for (Iterator<?> iterator = row.iterator(); iterator.hasNext(); i++) {
					this.preparedStatement.setObject(i, iterator.next());
				}
			} else {
				this.preparedStatement.setObject(1, obj);
			}
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
