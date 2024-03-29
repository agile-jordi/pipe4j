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
import pipe4j.core.TestUtils;
import pipe4j.pipe.file.FileIn;
import pipe4j.pipe.string.StringOut;

public class WordCountPipeTest extends TestCase {
	public void testWordCountPipe() throws Exception {
		StringOut stringOut = new StringOut();
		LinearPipeline.run(new FileIn(TestUtils.txtInFilePath),
				new WordCountPipe(), stringOut);

		// wc returns 2915 bytes for some reason
		assertEquals("101\t502\t2914\t2914", stringOut.getString());
	}
}
