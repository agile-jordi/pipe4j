package pipe.txt;

import junit.framework.TestCase;
import pipe.core.Pipe;
import pipe.core.Pipeline;
import pipe.core.TestUtils;
import pipe.file.FileIn;
import pipe.string.StringOut;

public class WordCountPipeTest extends TestCase {
	public void testWordCountPipe() throws Exception {
		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new WordCountPipe(), stringOut });

		// wc returns 2915 bytes for some reason
		assertEquals("101\t502\t2914\t2914", stringOut.getString());
	}
}
