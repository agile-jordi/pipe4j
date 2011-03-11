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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pipe4j.core.ConnectorDecorator;

public abstract class AbstractStreamPipe extends
		AbstractPipe<InputStream, OutputStream> implements
		ConnectorDecorator<InputStream, OutputStream> {

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		byte[] buffer = new byte[8192];
		int bytesRead;
		while (!cancelled() && (bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.flush();
	}

	@Override
	public InputStream decorateIn(InputStream in) throws IOException {
		return in;
	}

	@Override
	public OutputStream decorateOut(OutputStream out) throws IOException {
		return out;
	}
}
