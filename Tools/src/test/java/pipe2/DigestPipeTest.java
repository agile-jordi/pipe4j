package pipe2;

import junit.framework.TestCase;
import pipe2.core.Pipe;
import pipe2.core.Pipeline;
import pipe2.file.FileIn;
import pipe2.string.StringOut;
import pipe2.util.DigestPipe;

public class DigestPipeTest extends TestCase {
	public void testDigestPipe() throws Exception {
		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new DigestPipe(), stringOut });

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}
}
