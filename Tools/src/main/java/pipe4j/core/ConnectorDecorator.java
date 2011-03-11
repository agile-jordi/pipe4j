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

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Used by pipes that need to wrap input or output. Example: wrapping the
 * {@link OutputStream} with a {@link GZIPOutputStream}.
 * 
 * @author bbennett
 * 
 * @param <I>
 *            Pipe input
 * @param <O>
 *            Pipe output
 */
public interface ConnectorDecorator<I, O> {
	/**
	 * Decorate the pipe input.
	 * 
	 * @param in
	 *            Pipe input
	 * @return decorated input
	 * @throws IOException
	 */
	I decorateIn(I in) throws IOException;

	/**
	 * Decorate the pipe output.
	 * 
	 * @param out
	 *            Pipe output
	 * @return decorated output
	 * @throws IOException
	 */
	O decorateOut(O out) throws IOException;
}
