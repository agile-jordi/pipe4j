package pipe2;

import java.io.File;

import junit.framework.TestCase;
import pipe2.core.Pipe;
import pipe2.core.Pipeline;
import pipe2.file.FileOut;
import pipe2.file.FileIn;
import pipe2.string.StringOut;
import pipe2.util.DigestPipe;

public class FileTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new File(TestUtils.txtOutFilePath).delete();
	}

	public void testFile() throws Exception {
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new FileOut(TestUtils.txtOutFilePath) });
		
		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtOutFilePath),
				new DigestPipe(), stringOut });

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}
}
