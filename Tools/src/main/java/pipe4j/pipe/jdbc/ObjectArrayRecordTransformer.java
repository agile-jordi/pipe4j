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
import java.sql.SQLException;

/**
 * Generic transformer that returns an object array with all record objects.
 * 
 * @author bbennett
 */
public class ObjectArrayRecordTransformer implements RecordTransformer {
	private int columnCount = -1;

	@Override
	public Object transformRecord(ResultSet resultSet) throws SQLException {
		if (this.columnCount <= 0) {
			this.columnCount = resultSet.getMetaData().getColumnCount();
		}

		Object[] record = new Object[columnCount];
		for (int i = 0; i < record.length; i++) {
			record[i] = resultSet.getObject(i + 1);
		}

		return record;
	}
}
