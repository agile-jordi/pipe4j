package pipe.net;

import http.NanoHTTPD;
import junit.framework.TestCase;
import pipe.DigestPipe;
import pipe.Pipe;
import pipe.Pipeline;
import pipe.TestUtils;
import pipe.sink.StringSink;

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
		StringSink stringSink = new StringSink();
		new Pipeline(
				new Pipe[] {
						new GetUrlPipe(
								"http://localhost:8080/src/test/java/sample.txt"),
						new DigestPipe(), stringSink }).run();

		assertEquals(TestUtils.txtMD5, stringSink.getString());
	}
}
