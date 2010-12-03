package pipe;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import pipe.sink.StringSink;
import pipe.source.StringSource;

public class PipelineTest extends TestCase {
	private StringBuilder sb = new StringBuilder();

	public PipelineTest() {
		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 1000; ++j) {
				sb.append("This is a test!");
			}

			if (i % 2 == 0)
				sb.append('\r');
			sb.append('\n');
		}
	}

	public void testInvalidArgs() throws Exception {
		try {
			new Pipeline().run();
			fail("Expected IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
		try {
			new Pipeline((Pipe[]) null).run();
			fail("Expected IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
		try {
			new Pipeline((List<Pipe>) null).run();
			fail("Expected IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
	}

	public void testSimple() throws Exception {
		StringSink stringSink = new StringSink();
		Pipeline pipeline = new Pipeline(new Pipe[] {
				new StringSource(sb.toString()), new MiddlePipe(), stringSink });
		ThreadGroup threadGroup = pipeline.run();

		checkThreadGroup(threadGroup);
		assertEquals(sb.toString(), stringSink.getString());
	}

	public void testSimpleTwice() throws Exception {
		StringSink stringSink = new StringSink();
		Pipeline pipeline = new Pipeline(new Pipe[] {
				new StringSource(sb.toString()), new MiddlePipe(), stringSink });
		ThreadGroup threadGroup = pipeline.run();

		checkThreadGroup(threadGroup);
		assertEquals(sb.toString(), stringSink.getString());
		threadGroup = pipeline.run();

		checkThreadGroup(threadGroup);
		assertEquals(sb.toString(), stringSink.getString());
	}

	public void testTimeout() throws Exception {
		Pipeline pipeline = new Pipeline(new Pipe[] { new StringSource(
				sb.toString()) }).addPipe(new SleepPipe(5000), 100).addPipe(
				new StringSink());

		try {
			pipeline.run();
			fail("Expected PipelineException");
		} catch (PipelineException expected) {
			assertNotNull(expected.getCause());
			assertTrue(expected.getCause() instanceof InterruptedException);
			assertEquals("sleep interrupted", expected.getCause().getMessage());
		} catch (Throwable t) {
			fail("Unexpected: " + t);
		}
		checkThreadGroup(pipeline.getThreadGroup());
	}

	public void testException() throws Exception {
		Pipeline pipeline = new Pipeline(new Pipe[] {
				new StringSource(sb.toString()), new MiddlePipe(),
				new ExceptionPipe() });

		try {
			pipeline.run();
			fail("Expected PipelineException");
		} catch (PipelineException expected) {
			assertNotNull(expected.getCause());
			assertTrue(expected.getCause() instanceof IOException);
			assertEquals("Pipe closed", expected.getCause().getMessage());
		} catch (Throwable t) {
			fail("Unexpected: " + t);
		}
		checkThreadGroup(pipeline.getThreadGroup());
	}

	public void testCloseReader() throws Exception {
		Pipeline pipeline = new Pipeline(new Pipe[] {
				new StringSource(sb.toString()), new MiddlePipe(),
				new ReadClosingPipe() });

		try {
			pipeline.run();
			fail("Expected PipelineException");
		} catch (PipelineException expected) {
			assertNotNull(expected.getCause());
			assertTrue(expected.getCause() instanceof IOException);
			assertEquals("Pipe closed", expected.getCause().getMessage());
		} catch (Throwable t) {
			fail("Unexpected: " + t);
		}
		checkThreadGroup(pipeline.getThreadGroup());
	}

	public void testCloseWriter() throws Exception {
		Pipeline pipeline = new Pipeline(new Pipe[] {
				new StringSource(sb.toString()), new WriteClosingPipe(),
				new MiddlePipe() });

		try {
			pipeline.run();
			fail("Expected PipelineException");
		} catch (PipelineException expected) {
			assertNotNull(expected.getCause());
			assertTrue(expected.getCause() instanceof IOException);
			assertEquals("Pipe closed", expected.getCause().getMessage());
		} catch (Throwable t) {
			fail("Unexpected: " + t);
		}
		checkThreadGroup(pipeline.getThreadGroup());
	}

	private void checkThreadGroup(ThreadGroup threadGroup) {
		assertEquals(0, threadGroup.activeCount());
		assertEquals(0, threadGroup.activeGroupCount());
	}
}
