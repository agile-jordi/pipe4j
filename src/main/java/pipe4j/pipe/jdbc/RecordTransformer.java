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

/**
 * Callback for transforming a ResultSet row into an object instance;
 *  
 * @author bbennett
 */
public interface RecordTransformer {
	/**
	 * Read current row columns and return an object instance representing the values.
	 * ResultSet.next() is expected to be called before this method.
	 * 
	 * @param resultSet ResultSet
	 * @return Instance representing row values
	 * @throws Exception on any error
	 */
	Object transformRecord(ResultSet resultSet) throws Exception;
}
