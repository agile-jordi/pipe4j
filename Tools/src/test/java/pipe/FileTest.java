package pipe;

import java.io.File;

import junit.framework.TestCase;
import pipe.sink.FileSink;
import pipe.sink.StringSink;
import pipe.source.FileSource;

public class FileTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new File(TestUtils.txtOutFilePath).delete();
	}

	public void testDefaultBuffer() throws Exception {
		new Pipeline(new Pipe[] { new FileSource(TestUtils.txtInFilePath),
				new FileSink(TestUtils.txtOutFilePath) }).run();
		
		StringSink stringSink = new StringSink();
		new Pipeline(new Pipe[] { new FileSource(TestUtils.txtOutFilePath),
				new DigestPipe(), stringSink }).run();

		assertEquals(TestUtils.txtMD5, stringSink.getString());
	}
}
