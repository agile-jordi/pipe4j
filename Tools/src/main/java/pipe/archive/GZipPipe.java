package pipe.archive;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import pipe.core.AbstractDelegatingPipe;

public class GZipPipe extends AbstractDelegatingPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		GZIPOutputStream gzipos = new GZIPOutputStream(os);
		super.run(is, gzipos);
		gzipos.finish();
	}
}
