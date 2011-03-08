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
		String xml = "<?xml version=\"1.0\"?>" +
		"<a><c>foo</c><b x=\"1\"/><i>no</i></a>";
		
		Pipeline.run(new StringIn(xml), new XMLEventPipe(),
				new XPathIteratorPipe(), coll);
		
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
