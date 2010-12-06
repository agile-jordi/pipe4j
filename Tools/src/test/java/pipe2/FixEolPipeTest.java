package pipe2;

import junit.framework.TestCase;
import pipe2.core.Pipe;
import pipe2.core.Pipeline;
import pipe2.string.StringIn;
import pipe2.string.StringOut;
import pipe2.txt.FixEolPipe;
import pipe2.txt.FixEolPipe.Platform;

public class FixEolPipeTest extends TestCase {
	private final String source = "foo\r\nbar";
	public void testFixEolPipe() throws Exception {
		StringOut stringOut = new StringOut();
		Pipeline.run(new Pipe[] {
				new StringIn(source),
				new FixEolPipe(Platform.UNIX),
				stringOut
		});
		
		assertEquals("foo\nbar", stringOut.getString());
		
		Pipeline.run(new Pipe[] {
				new StringIn(source),
				new FixEolPipe(Platform.UNIX),
				new FixEolPipe(Platform.DOS),
				stringOut
		});
		
		assertEquals(source, stringOut.getString());
		
		Pipeline.run(new Pipe[] {
				new StringIn(source),
				new FixEolPipe(Platform.MAC),
				stringOut
		});
		
		assertEquals("foo\rbar", stringOut.getString());
		
		Pipeline.run(new Pipe[] {
				new StringIn(source),
				new FixEolPipe(Platform.MAC),
				new FixEolPipe(Platform.DOS),
				stringOut
		});
		
		assertEquals(source, stringOut.getString());
	}
}
