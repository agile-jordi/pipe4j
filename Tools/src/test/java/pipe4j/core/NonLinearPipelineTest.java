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

import junit.framework.TestCase;
import pipe4j.core.builder.NonLinearPipelineBuilder;
import pipe4j.core.builder.PipelineBuilder;
import pipe4j.core.executor.PipelineExecutor;
import pipe4j.pipe.compare.TextLineComparatorPipe;
import pipe4j.pipe.file.FileIn;
import pipe4j.pipe.string.StringOut;
import pipe4j.pipe.txt.LineReaderPipe;

public class NonLinearPipelineTest extends TestCase {
	public void testCompareCheckSum() throws Exception {
		NonLinearPipelineBuilder builder = new NonLinearPipelineBuilder();
		
		FileIn fileOne = new FileIn(TestUtils.txtInFilePath);
		FileIn fileTwo = new FileIn(TestUtils.txtInFilePath);
		LineReaderPipe lineReaderOne = new LineReaderPipe();
		LineReaderPipe lineReaderTwo = new LineReaderPipe();
		TextLineComparatorPipe comparator = new TextLineComparatorPipe();
		StringOut out = new StringOut();
		
		builder.createStreamConnection(fileOne, lineReaderOne);
		builder.createStreamConnection(fileTwo, lineReaderTwo);
		
		builder.createObjectConnection(lineReaderOne, PipelineBuilder.DEFAULT_CONNECTION, comparator, "one");
		builder.createObjectConnection(lineReaderTwo, PipelineBuilder.DEFAULT_CONNECTION, comparator, "two");
		
		builder.createStreamConnection(comparator, out);
		
		PipelineExecutor.execute(0, builder.build());
		
		assertEquals("identical", out.getString());
	}
}
