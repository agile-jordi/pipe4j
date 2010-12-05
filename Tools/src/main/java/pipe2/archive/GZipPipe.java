package pipe2.archive;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import pipe2.core.AbstractDelegatingPipe;

public class GZipPipe extends AbstractDelegatingPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, new GZIPOutputStream(os));
	}
}
