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
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import pipe4j.pipe.SimpleStreamPipe;

/**
 * Zips stream into one entry.
 * 
 * @author bbennett
 */
public class ZipPipe extends SimpleStreamPipe {
	private final String entryName;
	private int level = Deflater.DEFAULT_COMPRESSION;

	public ZipPipe(String entryName) {
		super();
		this.entryName = entryName;
	}

	public ZipPipe(String entryName, int level) {
		super();
		this.entryName = entryName;
		this.level = level;
	}

	@Override
	protected void run(InputStream inputStream, OutputStream outputStream)
			throws Exception {
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		zipOutputStream.putNextEntry(new ZipEntry(entryName));
		zipOutputStream.setLevel(level);
		transfer(inputStream, zipOutputStream);
		zipOutputStream.closeEntry();
		zipOutputStream.finish();
	}
}
