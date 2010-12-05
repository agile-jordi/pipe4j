package pipe2.txt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

class CountingInputStreamReader extends InputStreamReader {
	private int count = 0;
	
	public CountingInputStreamReader(InputStream in) {
		super(in);
	}

	@Override
	public int read() throws IOException {
		int read = super.read();
		if (read != -1) {
			++count;
		}
		return read;
	}
	
	@Override
	public int read(char[] cbuf) throws IOException {
		int read = super.read(cbuf);
		count += read;
		return read;
	}
	
	@Override
	public int read(char[] cbuf, int offset, int length) throws IOException {
		int read = super.read(cbuf, offset, length);
		count += read;
		return read;
	}
	
	@Override
	public int read(CharBuffer target) throws IOException {
		int read = super.read(target);
		count += read;
		return read;
	}
	
	public int getCount() {
		return count;
	}
}