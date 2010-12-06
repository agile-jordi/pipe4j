package pipe.archive;

import java.io.File;

import junit.framework.TestCase;
import pipe.core.Pipe;
import pipe.core.Pipeline;
import pipe.core.TestUtils;
import pipe.file.FileIn;
import pipe.file.FileOut;
import pipe.string.StringOut;
import pipe.util.DigestPipe;

public class GZipTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new File(TestUtils.txtOutFilePath).delete();
	}

	public void testGZip() throws Exception {
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new GZipPipe(), new GUnzipPipe(),
				new FileOut(TestUtils.txtOutFilePath) });

		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtOutFilePath),
				new DigestPipe(), stringOut });

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}
}
