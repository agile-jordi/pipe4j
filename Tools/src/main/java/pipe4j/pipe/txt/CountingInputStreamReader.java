/*
 * Copyright (C) 2010 Bernardo O. Bennett
 * 
 * This file is part of Stream4j.
 * 
 * Stream4j is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Stream4j is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with Stream4j. If not, see <http://www.gnu.org/licenses/>.
 */
package pipe4j.pipe.txt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class CountingInputStreamReader extends InputStreamReader {
	private int count = 0;

	public CountingInputStreamReader(InputStream in) {
		super(in);
	}

	@Override
	public int read() throws IOException {
		int read = super.read();
		if (read != -1) {
			++count;
		}
		return read;
	}

	@Override
	public int read(char[] cbuf, int offset, int length) throws IOException {
		int read = super.read(cbuf, offset, length);
		count += read;
		return read;
	}

	public int getCount() {
		return count;
	}
}