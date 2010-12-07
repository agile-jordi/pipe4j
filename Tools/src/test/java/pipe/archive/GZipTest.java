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
package pipe.archive;

import java.io.File;

import junit.framework.TestCase;
import pipe.core.Pipe;
import pipe.core.Pipeline;
import pipe.core.TestUtils;
import pipe.file.FileIn;
import pipe.file.FileOut;
import pipe.string.StringOut;
import pipe.util.DigestPipe;

public class GZipTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new File(TestUtils.txtOutFilePath).delete();
	}

	public void testGZip() throws Exception {
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new GZipPipe(), new GUnzipPipe(),
				new FileOut(TestUtils.txtOutFilePath) });

		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtOutFilePath),
				new DigestPipe(), stringOut });

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}
}
