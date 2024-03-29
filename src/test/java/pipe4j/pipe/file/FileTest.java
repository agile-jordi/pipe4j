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
package pipe4j.pipe.file;

import java.io.File;

import junit.framework.TestCase;
import pipe4j.core.LinearPipeline;
import pipe4j.core.TestUtils;
import pipe4j.pipe.string.StringOut;
import pipe4j.pipe.util.DigestPipe;

public class FileTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new File(TestUtils.txtOutFilePath).delete();
	}

	public void testFile() throws Exception {
		LinearPipeline.run(new FileIn(TestUtils.txtInFilePath), new FileOut(
				TestUtils.txtOutFilePath));

		StringOut stringOut = new StringOut();
		LinearPipeline.run(new FileIn(TestUtils.txtOutFilePath),
				new DigestPipe(), stringOut);

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}
}
