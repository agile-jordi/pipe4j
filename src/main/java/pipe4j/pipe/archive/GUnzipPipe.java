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
package pipe4j.pipe.archive;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import pipe4j.pipe.SimpleStreamPipe;

/**
 * Applyes gunzip to stream.
 * 
 * @author bbennett
 */
public class GUnzipPipe extends SimpleStreamPipe {
	@Override
	protected void run(InputStream inputStream, OutputStream outputStream)
			throws Exception {
		GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
		transfer(gzipInputStream, outputStream);
		gzipInputStream.close();
	}
}
