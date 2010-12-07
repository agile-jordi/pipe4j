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
import java.io.OutputStream;

public class PipeProcessorThread extends PipeThread {
	private final InputStream is;
	private final OutputStream os;
	private final PipeProcessor pipe;

	public PipeProcessorThread(ThreadGroup threadGroup, String name,
			InputStream is, OutputStream os, PipeProcessor pipe) {
		super(threadGroup, name);
		this.is = is;
		this.os = os;
		this.pipe = pipe;
	}

	@Override
	public PipeProcessor getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			pipe.run(is, os);
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

		if (os != null) {
			try {
				os.flush();
			} catch (Exception e) {
				if (!hasError()) {
					setException(e);
				}
			}
			try {
				os.close();
			} catch (Exception e) {
				if (!hasError()) {
					setException(e);
				}
			}
		}
	}
}