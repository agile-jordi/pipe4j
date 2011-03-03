package beatle.core;

import junit.framework.TestCase;

import org.apache.tools.ant.filters.StringInputStream;

public class XMLParserTest extends TestCase {
	public void testParseXML() throws Exception {
		Tag tag = new XMLParser().parse(new StringInputStream(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<a><b/><c x=\"1\"><d>foo</d></c></a>"));

		Tag expectedTag = new Tag("a").addChild(new Tag("b"));
		expectedTag.addChild(new Tag("c").setAttribute("x", "1").addChild(
				new Tag("d").setValue("foo")));

		assertEquals(expectedTag.toString(), tag.toString());
	}
}
