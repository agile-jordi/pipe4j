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
package pipe4j.pipe.net;

import http.NanoHTTPD;
import junit.framework.TestCase;
import pipe4j.core.Pipeline;
import pipe4j.core.TestUtils;
import pipe4j.pipe.string.StringOut;
import pipe4j.pipe.util.DigestPipe;

public class GetUrlPipeTest extends TestCase {
	private NanoHTTPD httpd;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.httpd = new NanoHTTPD(8080);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.httpd.stop();
	}

	public void testGetUrlPipe() throws Exception {
		StringOut stringIn = new StringOut();
		Pipeline.run(
				new UrlIn("http://localhost:8080/src/test/java/sample.txt"),
				new DigestPipe(), stringIn);

		assertEquals(TestUtils.txtMD5, stringIn.getString());
	}
}
