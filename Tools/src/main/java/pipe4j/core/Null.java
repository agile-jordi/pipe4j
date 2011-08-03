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

import java.io.Closeable;
import java.io.IOException;

/**
 * When declared as connector for the head and tail of a pipeline. Example:
 * FileIn will declare input as Null, as the input will be a file, and not the
 * output from a previous pipe.
 * 
 * Also used in object pipes to flag that no more objects will be produced.
 * 
 * @author bbennett
 */
public final class Null implements Closeable {
	/**
	 * Singleton instance.
	 */
	public static final Null INSTANCE = new Null();

	private Null() {
	}

	@Override
	public void close() throws IOException {
	}
}
