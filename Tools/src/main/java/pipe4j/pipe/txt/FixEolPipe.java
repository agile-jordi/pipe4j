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
package pipe4j.pipe.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import pipe4j.pipe.AbstractPipe;

public class FixEolPipe extends AbstractPipe<InputStream, OutputStream> {
	public enum Platform {
		DOS, UNIX, MAC, AUTO
	}

	private Platform platform = Platform.AUTO;

	public FixEolPipe() {
	}

	public FixEolPipe(Platform platform) {
		super();
		this.platform = platform;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

		final String eol;
		switch (this.platform) {
		case MAC:
			eol = "\r";
			break;
		case DOS:
			eol = "\r\n";
			break;
		case UNIX:
			eol = "\n";
			break;
		case AUTO:
			eol = System.getProperty("line.separator");
			break;
		default:
			throw new IllegalArgumentException("Unknown platform: "
					+ this.platform);
		}

		String line = reader.readLine();
		if (line != null) {
			writer.write(line);
			while (!cancelled() && ((line = reader.readLine()) != null)) {
				writer.write(eol); // Instead of calling newLine
				writer.write(line);
			}
		}
		writer.flush();
	}
}
