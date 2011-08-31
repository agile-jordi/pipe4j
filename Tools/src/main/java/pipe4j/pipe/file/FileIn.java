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
package pipe4j.pipe.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import pipe4j.pipe.SimpleStreamDecoratorPipe;

/**
 * Head pipe providing data from a file.
 * 
 * @author bbennett
 */
public class FileIn extends SimpleStreamDecoratorPipe {
	private File file;

	public FileIn(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileIn(File file) throws IOException {
		this.file = file;
	}

	@Override
	protected InputStream getDecoratedInputStream(InputStream inputStream)
			throws IOException {
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found: "
					+ file.getAbsolutePath());
		}

		if (!file.canRead()) {
			throw new IllegalArgumentException("Cannot read: "
					+ file.getAbsolutePath());
		}
		return new FileInputStream(file);
	}
}
