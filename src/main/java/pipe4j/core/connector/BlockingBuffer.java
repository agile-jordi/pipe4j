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

import java.io.Closeable;

/**
 * Ordered blocking buffer that supports null values for the purpose of flagging
 * that no more elements will be added to the buffer.
 * 
 * @author bbennett
 */
public interface BlockingBuffer extends Closeable {
	/**
	 * Put element into buffer, waiting if necessary for space to become
	 * available.
	 * 
	 * @param o
	 *            the element to add
	 * @throws InterruptedException
	 *             if interrupted while waiting
	 */
	void put(Object o) throws InterruptedException;

	/**
	 * Removes element from buffer, waiting if necessary until an element
	 * becomes available.
	 * 
	 * @return the head of this queue
	 * @throws InterruptedException
	 *             if interrupted while waiting
	 */
	Object take() throws InterruptedException;
}
