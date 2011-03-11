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
package pipe4j.pipe.util;

import java.io.IOException;
import java.io.OutputStream;

class TeeOutputStream extends OutputStream {
	private final OutputStream tee, out;

	public TeeOutputStream(OutputStream chainedStream, OutputStream teeStream) {
		out = chainedStream;
		tee = teeStream;
	}

	@Override
	public void write(int c) throws IOException {
		out.write(c);
		tee.write(c);
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
		tee.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
		tee.write(b, off, len);
	}

	@Override
	public void close() throws IOException {
		flush();

		out.close();
		tee.close();
	}

	@Override
	public void flush() throws IOException {
		out.flush();
		tee.flush();
	}
}