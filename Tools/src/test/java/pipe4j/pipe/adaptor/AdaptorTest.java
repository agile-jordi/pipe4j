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
package pipe4j.pipe.adaptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;
import pipe4j.core.Pipeline;
import pipe4j.pipe.adaptor.InputStreamAdaptor;
import pipe4j.pipe.adaptor.OutputStreamAdaptor;

public class AdaptorTest extends TestCase {
	public void testAdaptor() throws Exception {
		String s = "foo bar";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Pipeline.run(new InputStreamAdaptor(new ByteArrayInputStream(s.getBytes())),
				new OutputStreamAdaptor(baos));

		assertEquals(s, new String(baos.toByteArray()));
	}
}
