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

public class ZipTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new File(TestUtils.txtOutFilePath).delete();
	}

	public void testZip() throws Exception {
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new ZipPipe("foo.txt"), new UnzipPipe(),
				new FileOut(TestUtils.txtOutFilePath) });

		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new DigestPipe(), stringOut });

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}

	public void testZipMaxCompression() throws Exception {
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new ZipPipe("foo.txt", 9), new UnzipPipe(),
				new FileOut(TestUtils.txtOutFilePath) });

		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new DigestPipe(), stringOut });

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}
}
