package pipe.util;

import junit.framework.TestCase;
import pipe.core.Pipe;
import pipe.core.Pipeline;
import pipe.core.TestUtils;
import pipe.file.FileIn;
import pipe.string.StringOut;

public class DigestPipeTest extends TestCase {
	public void testDigestPipe() throws Exception {
		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new DigestPipe(), stringOut });

		assertEquals(TestUtils.txtMD5, stringOut.getString());
	}
}
