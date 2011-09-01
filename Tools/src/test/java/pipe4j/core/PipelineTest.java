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
import pipe4j.core.executor.PipelineExecutionException;
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
			LinearPipeline.run((Pipe[]) null);
			fail("Expected IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
	}

	public void testSimple() throws Exception {
		StringOut stringOut = new StringOut();

		checkResults(null, 0, new StringIn(sb.toString()), new StreamPipe(),
				stringOut);
		assertEquals(sb.toString(), stringOut.getString());
	}

	public void testTimeout() throws Exception {
		StringOut stringOut = new StringOut();
		checkResults(InterruptedException.class, 1000,
				new StringIn(sb.toString()), new SleepPipe(5000), stringOut);
		assertEquals(sb.toString(), stringOut.getString());
	}

	public void testException() throws Exception {
		checkResults(IOException.class, 0, new StringIn(sb.toString()),
				new StreamPipe(), new ExceptionPipe(), new StringOut());
	}

	public void testCloseReader() throws Exception {
		checkResults(IOException.class, 0, new StringIn(sb.toString()),
				new StreamPipe(), new ReadClosingPipe(), new StringOut());
	}

	public void testCloseWriter() throws Exception {
		checkResults(IOException.class, 0, new StringIn(sb.toString()),
				new WriteClosingPipe(), new StreamPipe(), new StringOut());
	}

	private void checkResults(Class<? extends Exception> clazz, long timeout,
			Pipe... pipeline) {
		PipelineExecutionException e = null;
		try {
			LinearPipeline.run(timeout, pipeline);
		} catch (PipelineExecutionException ex) {
			e = ex;
		} catch (Exception ex) {
			fail();
		}

		if (timeout > 0) {
			assertNotNull(e);
		}

		if (clazz == null) {
			assertFalse("No errors expected but got "
					+ (e == null ? "null" : e.getClass().toString()), e != null);
		} else {
			assertTrue("Errors expected", e != null);

			boolean found = false;
			for (Exception ex : e.getExceptionList()) {
				if (ex.getClass().equals(clazz))
					found = true;
			}
			assertTrue("Expected " + clazz.toString() + " but got "
					+ e.getClass().toString(), found);
		}
	}
}
