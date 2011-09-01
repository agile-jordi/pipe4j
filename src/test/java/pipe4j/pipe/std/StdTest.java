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
package pipe4j.pipe.std;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import junit.framework.TestCase;
import pipe4j.core.LinearPipeline;
import pipe4j.pipe.string.StringIn;
import pipe4j.pipe.string.StringOut;

public class StdTest extends TestCase {
	private static final String source = "foo\r\nbar";
	private PrintStream prevOut;
	private PrintStream prevErr;
	private InputStream prevIn;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		prevOut = System.out;
		prevErr = System.err;
		prevIn = System.in;
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		System.setOut(prevOut);
		System.setErr(prevErr);
		System.setIn(prevIn);
	}

	public void testStd() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(source.getBytes());
		System.setIn(in);

		StringOut stringOut = new StringOut();
		LinearPipeline.run(new Stdin(), stringOut);
		assertEquals(source, stringOut.getString());

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		LinearPipeline.run(new StringIn(source), new Stdout());

		assertEquals(source, new String(out.toByteArray()));

		ByteArrayOutputStream err = new ByteArrayOutputStream();
		System.setErr(new PrintStream(err));

		LinearPipeline.run(new StringIn(source), new Stderr());

		assertEquals(source, new String(out.toByteArray()));

	}
}
