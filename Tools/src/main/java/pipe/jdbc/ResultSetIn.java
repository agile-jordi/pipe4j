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
package pipe.jdbc;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import pipe.core.AbstractPipe;
import pipe.core.PipeProcessor;

public class ResultSetIn extends AbstractPipe implements PipeProcessor {
	private final ResultSet resultSet;

	public ResultSetIn(ResultSet resultSet) {
		super();
		this.resultSet = resultSet;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		ResultSetMetaData md = resultSet.getMetaData();
		int columnCount = md.getColumnCount();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		Object[] row;
		while (resultSet.next()) {
			row = new Object[columnCount];
			for (int i = 0; i < row.length; i++) {
				row[i] = resultSet.getObject(i + 1);
			}
			oos.writeObject(row);
		}
	}
}
