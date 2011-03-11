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

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import pipe4j.pipe.AbstractPipe;

class Producer extends AbstractPipe<InputStream, OutputStream> {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
		for (int i = 0; i < 10000; i++) {
			writer.write(i + ", used mem: "
					+ (runtime.totalMemory() - runtime.freeMemory()));
			writer.newLine();
		}
		writer.flush();
	}
}