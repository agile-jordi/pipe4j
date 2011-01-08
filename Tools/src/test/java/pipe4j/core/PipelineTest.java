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
package pipe4j.core;

import java.io.IOException;

import junit.framework.TestCase;
import pipe4j.core.Pipeline;
import pipe4j.core.PipelineInfo;
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
			Pipeline.run(null);
			fail("Expected IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
	}

	public void testSimple() throws Exception {
		StringOut stringOut = new StringOut();
		PipelineInfo info = Pipeline.run(
				new StringIn(sb.toString()), new MiddlePipe(), stringOut);

		checkResults(info, null);
		assertEquals(sb.toString(), stringOut.getString());
	}

	public void testTimeout() throws Exception {
		PipelineInfo info = Pipeline.run(100,
				new StringIn(sb.toString()), new SleepPipe(5000),
						new StringOut());
		checkResults(info, InterruptedException.class);
	}

	public void testException() throws Exception {
		PipelineInfo info = Pipeline.run(
				new StringIn(sb.toString()), new MiddlePipe(),
				new ExceptionPipe(), new StringOut());
		checkResults(info, IOException.class);
	}

	public void testCloseReader() throws Exception {
		PipelineInfo info = Pipeline.run(
				new StringIn(sb.toString()), new MiddlePipe(),
				new ReadClosingPipe(), new StringOut());
		checkResults(info, IOException.class);
	}

	public void testCloseWriter() throws Exception {
		PipelineInfo info = Pipeline.run(
				new StringIn(sb.toString()), new WriteClosingPipe(),
				new MiddlePipe(), new StringOut());
		checkResults(info, IOException.class);
	}

	private void checkResults(PipelineInfo info,
			Class<? extends Exception> clazz) {
		assertEquals(0, info.getThreadGroup().activeCount());
		assertEquals(0, info.getThreadGroup().activeGroupCount());
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
