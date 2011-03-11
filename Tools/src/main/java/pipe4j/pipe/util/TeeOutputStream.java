package pipe4j.pipe.util;

import java.io.IOException;
import java.io.OutputStream;

class TeeOutputStream extends OutputStream {
	private final OutputStream tee, out;

	public TeeOutputStream(OutputStream chainedStream, OutputStream teeStream) {
		out = chainedStream;
		tee = teeStream;
	}

	@Override
	public void write(int c) throws IOException {
		out.write(c);
		tee.write(c);
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
		tee.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
		tee.write(b, off, len);
	}

	@Override
	public void close() throws IOException {
		flush();

		out.close();
		tee.close();
	}

	@Override
	public void flush() throws IOException {
		out.flush();
		tee.flush();
	}
}