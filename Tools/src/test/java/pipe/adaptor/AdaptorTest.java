package pipe.adaptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;
import pipe.core.Pipe;
import pipe.core.Pipeline;

public class AdaptorTest extends TestCase {
	public void testAdaptor() throws Exception {
		String s = "foo bar";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Pipeline.run(new Pipe[] {
				new InAdaptor(new ByteArrayInputStream(s.getBytes())),
				new OutAdaptor(baos) });

		assertEquals(s, new String(baos.toByteArray()));
	}
}
