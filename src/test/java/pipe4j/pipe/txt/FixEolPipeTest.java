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
package pipe4j.pipe.txt;

import junit.framework.TestCase;
import pipe4j.core.LinearPipeline;
import pipe4j.pipe.string.StringIn;
import pipe4j.pipe.string.StringOut;
import pipe4j.pipe.txt.FixEolPipe.Platform;

public class FixEolPipeTest extends TestCase {
	private static final String source = "foo\r\nbar";

	public void testFixEolPipe() throws Exception {
		StringOut stringOut = new StringOut();
		LinearPipeline.run(new StringIn(source), new FixEolPipe(Platform.UNIX),
				stringOut);

		assertEquals("foo\nbar", stringOut.getString());

		LinearPipeline.run(new StringIn(source), new FixEolPipe(Platform.UNIX),
				new FixEolPipe(Platform.DOS), stringOut);

		assertEquals(source, stringOut.getString());

		LinearPipeline.run(new StringIn(source), new FixEolPipe(Platform.MAC),
				stringOut);

		assertEquals("foo\rbar", stringOut.getString());

		LinearPipeline.run(new StringIn(source), new FixEolPipe(Platform.MAC),
				new FixEolPipe(Platform.DOS), stringOut);

		assertEquals(source, stringOut.getString());
	}
}
