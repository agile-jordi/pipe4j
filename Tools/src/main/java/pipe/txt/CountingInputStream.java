package pipe.txt;

import java.io.IOException;
import java.io.InputStream;

class CountingInputStream extends InputStream {
	private final InputStream is;
	private int count = 0;

	public CountingInputStream(InputStream is) {
		super();
		this.is = is;
	}

	public int read() throws IOException {
		int read = is.read();
		if (read >= 0) {
			++count;
		}
		return read;
	}

	public int read(byte[] b) throws IOException {
		int read = is.read(b);
		this.count += read;
		return read;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		int read = is.read(b, off, len);
		this.count += read;
		return read;
	}

	public int getCount() {
		return count;
	}
}