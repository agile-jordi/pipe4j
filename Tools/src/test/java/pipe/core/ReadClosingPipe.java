package pipe.core;

import java.io.InputStream;
import java.io.OutputStream;

class ReadClosingPipe extends AbstractPipe implements PipeProcessor {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		byte[] buffer = new byte[8];
		int n = is.read(buffer);
		os.write(buffer, 0, n);
		is.close();
	}
}