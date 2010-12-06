package pipe.net;

import http.NanoHTTPD;
import junit.framework.TestCase;
import pipe.core.Pipe;
import pipe.core.Pipeline;
import pipe.core.TestUtils;
import pipe.string.StringOut;
import pipe.util.DigestPipe;

public class GetUrlPipeTest extends TestCase {
	private NanoHTTPD httpd;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.httpd = new NanoHTTPD(8080);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		this.httpd.stop();
	}

	public void testGetUrlPipe() throws Exception {
		StringOut stringIn = new StringOut();
		Pipeline.run(new Pipe[] {
				new UrlIn("http://localhost:8080/src/test/java/sample.txt"),
				new DigestPipe(), stringIn });

		assertEquals(TestUtils.txtMD5, stringIn.getString());
	}
}
