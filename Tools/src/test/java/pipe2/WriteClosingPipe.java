package pipe2;

import java.io.InputStream;
import java.io.OutputStream;

import pipe2.core.AbstractPipe;

class WriteClosingPipe extends AbstractPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		byte[] buffer = new byte[8];
		int n = is.read(buffer);
		os.write(buffer, 0, n);
		os.close();
	}
}