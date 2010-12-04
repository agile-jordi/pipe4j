package pipe;

import junit.framework.TestCase;
import pipe.sink.StringSink;
import pipe.source.FileSource;

public class WordCountPipeTest extends TestCase {
	public void testWordCountPipe() throws Exception {
		StringSink stringSink = new StringSink();
		new Pipeline(new Pipe[] { new FileSource(TestUtils.txtInFilePath),
				new WordCountPipe(), stringSink }).run();
		// wc returns 2915 bytes for some reason
		assertEquals("101\t502\t2914\t2914", stringSink.getString());
	}
}
