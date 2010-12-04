package pipe;

import junit.framework.TestCase;
import pipe.sink.StringSink;
import pipe.source.FileSource;

public class DigestPipeTest extends TestCase {
	public void testDigestPipe() throws Exception {
		StringSink stringSink = new StringSink();
		new Pipeline(new Pipe[] { new FileSource(TestUtils.txtInFilePath),
				new DigestPipe(), stringSink }).run();

		assertEquals(TestUtils.txtMD5, stringSink.getString());
	}
}
