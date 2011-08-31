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
package pipe4j.pipe.xml;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;
import pipe4j.core.Pipeline;
import pipe4j.pipe.adaptor.CollectionOutAdaptor;
import pipe4j.pipe.string.StringIn;

public class XmlPipeTest extends TestCase {
	public void testXml() throws Exception {
		CollectionOutAdaptor coll = new CollectionOutAdaptor();
		String xml = "<?xml version=\"1.0\"?>"
				+ "<a><c>foo</c><b x=\"1\"/><i>no</i><ignore/></a>";

		XPathIteratorPipe xPathIteratorPipe = new XPathIteratorPipe();
		xPathIteratorPipe.addIgnore(new Ignore("/a/ignore"));
		Pipeline.run(new StringIn(xml), new XMLEventPipe(),
				xPathIteratorPipe, coll);

		Collection<XPathAndValue> expected = new ArrayList<XPathAndValue>();
		expected.add(new XPathAndValue("/a", null));
		expected.add(new XPathAndValue("/a/c", null));
		expected.add(new XPathAndValue("/a/c/text()", "foo"));
		expected.add(new XPathAndValue("/a/b", null));
		expected.add(new XPathAndValue("/a/b/@x", "1"));
		expected.add(new XPathAndValue("/a/i", null));
		expected.add(new XPathAndValue("/a/i/text()", "no"));

		assertEquals(expected.toString(), coll.getCollection().toString());
	}
}
