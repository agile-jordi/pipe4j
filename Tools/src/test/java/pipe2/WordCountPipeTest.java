package pipe2;

import junit.framework.TestCase;
import pipe2.core.Pipe;
import pipe2.core.Pipeline;
import pipe2.file.FileIn;
import pipe2.string.StringOut;
import pipe2.txt.WordCountPipe;

public class WordCountPipeTest extends TestCase {
	public void testWordCountPipe() throws Exception {
		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] { new FileIn(TestUtils.txtInFilePath),
				new WordCountPipe(), stringOut });
		
		// wc returns 2915 bytes for some reason
		assertEquals("101\t502\t2914\t2914", stringOut.getString());
	}
}
