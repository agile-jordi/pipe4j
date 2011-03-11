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
package pipe4j.core.connector;

import pipe4j.core.Pipe;
import pipe4j.core.PipeThread;

/**
 * Responsible for connecting two pipes.
 * 
 * @author bbennett
 */
public interface PipeConnector {
	/**
	 * @param prev
	 *            First pipe
	 * @param next
	 *            Second pipe
	 * @return True if this implementation supports connecting this pair of
	 *         pipes
	 */
	boolean supports(Pipe<?, ?> prev, Pipe<?, ?> next);

	/**
	 * Connect two pipes.
	 * 
	 * @param pipe1
	 *            First pipe
	 * @param pipe2
	 *            Second pipe
	 */
	@SuppressWarnings("rawtypes")
	void connect(PipeThread pipe1, PipeThread pipe2);

	void close(Object in, Object out);
}
