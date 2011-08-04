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

import java.io.IOException;

import junit.framework.TestCase;
import pipe4j.pipe.StreamPipe;
import pipe4j.pipe.string.StringIn;
import pipe4j.pipe.string.StringOut;

public class PipelineTest extends TestCase {
	private StringBuilder sb = new StringBuilder();

	public PipelineTest() {
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 1000; ++j) {
				sb.append("This is a test!");
			}

			if (i % 2 == 0)
				sb.append('\r');
			sb.append('\n');
		}
	}

	public void testInvalidArgs() throws Exception {
		try {
			Pipeline.run((Pipe[]) null);
			fail("Expected IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
	}

	public void testSimple() throws Exception {
		StringOut stringOut = new StringOut();
		PipelineInfo info = Pipeline.run(new StringIn(sb.toString()),
				new StreamPipe(), stringOut);

		checkResults(info, null, false);
		assertEquals(sb.toString(), stringOut.getString());
	}

	public void testTimeout() throws Exception {
		StringOut stringOut = new StringOut();
		PipelineInfo info = Pipeline.run(1000, new StringIn(sb.toString()),
				new SleepPipe(5000), stringOut);
		checkResults(info, InterruptedException.class, true);
		assertEquals(sb.toString(), stringOut.getString());
	}

	public void testException() throws Exception {
		PipelineInfo info = Pipeline.run(new StringIn(sb.toString()),
				new StreamPipe(), new ExceptionPipe(), new StringOut());
		checkResults(info, IOException.class, false);
	}

	public void testCloseReader() throws Exception {
		PipelineInfo info = Pipeline.run(new StringIn(sb.toString()),
				new StreamPipe(), new ReadClosingPipe(), new StringOut());
		checkResults(info, IOException.class, false);
	}

	public void testCloseWriter() throws Exception {
		PipelineInfo info = Pipeline.run(new StringIn(sb.toString()),
				new WriteClosingPipe(), new StreamPipe(), new StringOut());
		checkResults(info, IOException.class, false);
	}

	private void checkResults(PipelineInfo info,
			Class<? extends Exception> clazz, boolean timeout) {
		assertEquals(timeout, info.isTimeoutExceeded());
		if (clazz == null) {
			assertFalse("No errors expected but got "
					+ (info.getException() == null ? "null" : info
							.getException().getClass().toString()),
					info.hasError());
		} else {
			assertTrue("Errors expected", info.hasError());
			assertTrue("Expected " + clazz.toString() + " but got "
					+ info.getException().getClass().toString(), info
					.getException().getClass().equals(clazz));
		}
	}
}
