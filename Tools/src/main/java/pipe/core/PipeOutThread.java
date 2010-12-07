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
package pipe.core;

import java.io.InputStream;

public class PipeOutThread extends PipeThread {
	private final InputStream is;
	private final PipeOut pipe;

	public PipeOutThread(ThreadGroup threadGroup, String name, InputStream is,
			PipeOut pipe) {
		super(threadGroup, name);
		this.is = is;
		this.pipe = pipe;
	}

	@Override
	public PipeOut getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			pipe.run(is);
		} catch (Exception e) {
			setException(e);
		} finally {
			close();
		}
	}

	@Override
	public void close() {
		if (is != null) {
			try {
				is.close();
			} catch (Exception e) {
				if (!hasError()) {
					setException(e);
				}
			}
		}
	}
}