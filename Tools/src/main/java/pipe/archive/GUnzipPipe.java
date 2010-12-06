package pipe.archive;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import pipe.core.AbstractPipeProcessor;

public class GUnzipPipe extends AbstractPipeProcessor {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(new GZIPInputStream(is), os);
	}
}
