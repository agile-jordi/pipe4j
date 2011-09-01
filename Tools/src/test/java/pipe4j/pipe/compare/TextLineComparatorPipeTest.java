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
package pipe4j.pipe.compare;

import java.io.IOException;

import junit.framework.TestCase;
import pipe4j.core.Pipe;
import pipe4j.core.TestUtils;
import pipe4j.core.builder.NonLinearPipelineBuilder;
import pipe4j.core.builder.PipelineBuilder;
import pipe4j.core.executor.PipelineExecutor;
import pipe4j.pipe.file.FileIn;
import pipe4j.pipe.string.StringIn;
import pipe4j.pipe.string.StringOut;
import pipe4j.pipe.txt.LineReaderPipe;

public class TextLineComparatorPipeTest extends TestCase {
	public void testCompareIdentical() throws Exception {
		StringOut out = compare(new FileIn(TestUtils.txtInFilePath),
				new FileIn(TestUtils.txtInFilePath));
		assertEquals("identical", out.getString());
	}

	public void testCompareIDifferent() throws Exception {
		StringIn input1 = new StringIn("foo\nfoo");
		StringIn input2 = new StringIn("foo\nbar");
		StringOut out = compare(input1, input2);
		assertEquals("different", out.getString());
	}

	private StringOut compare(Pipe input1, Pipe input2) throws IOException {
		NonLinearPipelineBuilder builder = new NonLinearPipelineBuilder();

		LineReaderPipe lineReaderOne = new LineReaderPipe();
		LineReaderPipe lineReaderTwo = new LineReaderPipe();
		TextLineComparatorPipe comparator = new TextLineComparatorPipe();
		StringOut out = new StringOut();

		builder.createStreamConnection(input1, lineReaderOne);
		builder.createStreamConnection(input2, lineReaderTwo);

		builder.createObjectConnection(lineReaderOne,
				PipelineBuilder.DEFAULT_CONNECTION, comparator, "one");
		builder.createObjectConnection(lineReaderTwo,
				PipelineBuilder.DEFAULT_CONNECTION, comparator, "two");

		builder.createStreamConnection(comparator, out);

		PipelineExecutor.execute(0, builder.build());
		return out;
	}
}
