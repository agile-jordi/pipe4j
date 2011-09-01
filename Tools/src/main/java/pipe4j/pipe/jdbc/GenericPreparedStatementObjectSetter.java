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
import java.util.Collection;

/**
 * Generic PreparedStatement setter.
 * 
 * @author bbennett
 */
public class GenericPreparedStatementObjectSetter implements
		PreparedStatementObjectSetter {

	@Override
	public void setValues(PreparedStatement preparedStatement, Object object)
			throws Exception {
		if (object instanceof Object[]) {
			Object[] record = (Object[]) object;
			for (int i = 0; i < record.length; i++) {
				preparedStatement.setObject(i + 1, record[i]);
			}
		} else if (object instanceof Collection) {
			Collection<?> row = (Collection<?>) object;
			int i = 1;
			for (Object o : row) {
				preparedStatement.setObject(i, o);
			}
		} else {
			preparedStatement.setObject(1, object);
		}
	}
}
