package beatle.core;

import org.apache.tools.ant.filters.StringInputStream;

import junit.framework.TestCase;

public class BeatleTest extends TestCase {
	public void testEcho() throws Exception {
		Tag tag = new XMLParser().parse(new StringInputStream(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<echo>foo</echo>"));
		new Beatle().execute(tag);
	}
}
