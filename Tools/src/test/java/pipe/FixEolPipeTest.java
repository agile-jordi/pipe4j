package pipe;

import pipe.FixEolPipe.Platform;
import pipe.sink.StringSink;
import pipe.source.StringSource;
import junit.framework.TestCase;

public class FixEolPipeTest extends TestCase {
	private final String source = "foo\r\nbar";
	public void testFixEolPipe() throws Exception {
		StringSink stringSink = new StringSink();
		Pipeline.runPipeline(new Pipe[] {
				new StringSource(source),
				new FixEolPipe(Platform.UNIX),
				stringSink
		});
		
		assertEquals("foo\nbar", stringSink.getString());
		
		Pipeline.runPipeline(new Pipe[] {
				new StringSource(source),
				new FixEolPipe(Platform.UNIX),
				new FixEolPipe(Platform.DOS),
				stringSink
		});
		
		assertEquals(source, stringSink.getString());
		
		Pipeline.runPipeline(new Pipe[] {
				new StringSource(source),
				new FixEolPipe(Platform.MAC),
				stringSink
		});
		
		assertEquals("foo\rbar", stringSink.getString());
		
		Pipeline.runPipeline(new Pipe[] {
				new StringSource(source),
				new FixEolPipe(Platform.MAC),
				new FixEolPipe(Platform.DOS),
				stringSink
		});
		
		assertEquals(source, stringSink.getString());
	}
}
