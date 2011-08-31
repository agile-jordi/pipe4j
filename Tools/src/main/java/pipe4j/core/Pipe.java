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

/**
 * Base interface for all pipes. Input and Output types will be declared by
 * implementations.
 * 
 * @author bbennett
 * 
 */
public interface Pipe {
	/**
	 * Run the pipe. Implementations should loop the following steps:
	 * 
	 * <ol>
	 * <li>Check if cancellation was requested;</li>
	 * <li>Read from input;</li>
	 * <li>Process data;</li>
	 * <li>Write to output.</li>
	 * </ol>
	 * 
	 * @param connections
	 *            Connection holder
	 * @throws Exception
	 */
	void run(Connections connections) throws Exception;

	/**
	 * Request execution to be canceled.
	 */
	void cancel();
}
