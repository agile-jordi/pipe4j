package pipe4j.pipe.util;

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;
import pipe4j.core.Pipeline;
import pipe4j.core.TestUtils;
import pipe4j.pipe.file.FileIn;
import pipe4j.pipe.string.StringOut;

public class TeePipeTest extends TestCase {
	public void testTee() throws Exception {
		StringOut stringOut = new StringOut();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Pipeline.run(new FileIn(TestUtils.txtInFilePath), new TeePipe(
				baos), stringOut);
		assertEquals(stringOut.getString(), new String(baos.toByteArray()));
	}
}
