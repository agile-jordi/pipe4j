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
package pipe4j.pipe.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pipe4j.pipe.AbstractStreamPipeOut;

public class FileOut extends AbstractStreamPipeOut {
	private File file;

	public FileOut(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileOut(File file) throws IOException {
		this.file = file;
	}

	@Override
	protected OutputStream getOutputStream() throws Exception {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IllegalArgumentException(
						"Destination cannot be a directory! Directory: "
								+ this.file.getAbsolutePath());
			}
			if (!file.delete())
				throw new IllegalArgumentException(
						"Could not delete existing file! File: "
								+ this.file.getAbsolutePath());
		} else {
			if (!file.createNewFile()) {
				throw new IllegalArgumentException(
						"Could not create new file: " + file.getAbsolutePath());
			}
		}
		return new FileOutputStream(file);
	}
}
