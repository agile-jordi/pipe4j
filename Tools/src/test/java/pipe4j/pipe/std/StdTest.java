package pipe4j.pipe.std;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import junit.framework.TestCase;
import pipe4j.core.Pipeline;
import pipe4j.pipe.string.StringIn;
import pipe4j.pipe.string.StringOut;

public class StdTest extends TestCase {
	private static final String source = "foo\r\nbar";
	private PrintStream prevOut;
	private PrintStream prevErr;
	private InputStream prevIn;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		prevOut = System.out;
		prevErr = System.err;
		prevIn = System.in;
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		System.setOut(prevOut);
		System.setErr(prevErr);
		System.setIn(prevIn);
	}

	public void testStd() throws Exception {
		ByteArrayInputStream in = new ByteArrayInputStream(source.getBytes());
		System.setIn(in);

		StringOut stringOut = new StringOut();
		Pipeline.run(new Stdin(), stringOut);
		assertEquals(source, stringOut.getString());

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		Pipeline.run(new StringIn(source), new Stdout());

		assertEquals(source, new String(out.toByteArray()));

		ByteArrayOutputStream err = new ByteArrayOutputStream();
		System.setErr(new PrintStream(err));

		Pipeline.run(new StringIn(source), new Stderr());

		assertEquals(source, new String(out.toByteArray()));

	}
}
