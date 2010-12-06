package pipe2.core;

import java.io.IOException;

import junit.framework.TestCase;
import pipe2.string.StringIn;
import pipe2.string.StringOut;

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
			Pipeline.run((Pipe[]) null);
			fail("Expected IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
	}

	public void testSimple() throws Exception {
		StringOut stringOut = new StringOut();
		PipelineInfo info = Pipeline.run(new Pipe[] {
				new StringIn(sb.toString()), new MiddlePipe(), stringOut });

		checkResults(info, null);
		assertEquals(sb.toString(), stringOut.getString());
	}

	public void testTimeout() throws Exception {
		PipelineInfo info = Pipeline.run(
				new Pipe[] { new StringIn(sb.toString()), new SleepPipe(5000),
						new StringOut() }, 100);
		checkResults(info, InterruptedException.class);
	}

	public void testException() throws Exception {
		PipelineInfo info = Pipeline.run(new Pipe[] {
				new StringIn(sb.toString()), new MiddlePipe(),
				new ExceptionPipe() });
		checkResults(info, IOException.class);
	}

	public void testCloseReader() throws Exception {
		PipelineInfo info = Pipeline.run(new Pipe[] {
				new StringIn(sb.toString()), new MiddlePipe(),
				new ReadClosingPipe() });
		checkResults(info, IOException.class);
	}

	public void testCloseWriter() throws Exception {
		PipelineInfo info = Pipeline.run(new Pipe[] {
				new StringIn(sb.toString()), new WriteClosingPipe(),
				new MiddlePipe() });
		checkResults(info, IOException.class);
	}

	private void checkResults(PipelineInfo info,
			Class<? extends Exception> clazz) {
		assertEquals(0, info.getThreadGroup().activeCount());
		assertEquals(0, info.getThreadGroup().activeGroupCount());
		if (clazz == null) {
			assertFalse("No errors expected but got "
					+ (info.getException() == null ? "null" : info
							.getException().getClass().toString()),
					info.hasError());
		} else {
			assertTrue("Errors expected", info.hasError());
			assertTrue("Expected " + clazz.toString() + " but got "
					+ info.getException().getClass().toString(), info
					.getException().getClass().equals(clazz));
		}
	}
}
