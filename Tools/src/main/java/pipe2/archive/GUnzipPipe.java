package pipe2.archive;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import pipe2.core.AbstractDelegatingPipe;

public class GUnzipPipe extends AbstractDelegatingPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(new GZIPInputStream(is), os);
	}
}
