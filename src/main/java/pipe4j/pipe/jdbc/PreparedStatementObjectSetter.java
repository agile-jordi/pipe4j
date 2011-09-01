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

/**
 * Callback responsible for setting values from Object to PreparedStatement.
 * 
 * @author bbennett
 */
public interface PreparedStatementObjectSetter {
	/**
	 * Sets values from Object to PreparedStatement.
	 * PreparedStatement.addBatch() must be called if this method is called
	 * multiple times for the same PreparedStatement instance.
	 * 
	 * @param preparedStatement
	 * @param object
	 * @throws Exception
	 */
	void setValues(PreparedStatement preparedStatement, Object object)
			throws Exception;
}
