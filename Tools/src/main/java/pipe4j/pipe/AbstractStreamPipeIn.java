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
package pipe4j.pipe;

import java.io.InputStream;
import java.io.OutputStream;

import pipe4j.core.Null;

public abstract class AbstractStreamPipeIn extends AbstractPipe<Null, OutputStream> {

	public void run(Null in, OutputStream out) throws Exception {
		byte[] buffer = new byte[8192];
		int bytesRead;
		InputStream is = getInputStream();
		while (!cancelled() && (bytesRead = is.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		out.flush();
	}

	protected abstract InputStream getInputStream() throws Exception;
}
