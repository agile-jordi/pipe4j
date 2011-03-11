package pipe4j.pipe.archive;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import pipe4j.pipe.AbstractStreamPipe;

/**
 * Applies gzip to stream.
 * 
 * @author bbennett
 */
public class GZipPipe extends AbstractStreamPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, os);
		((GZIPOutputStream) os).finish();
	}

	@Override
	public OutputStream decorateOut(OutputStream out) throws IOException {
		return new GZIPOutputStream(out);
	}
}
