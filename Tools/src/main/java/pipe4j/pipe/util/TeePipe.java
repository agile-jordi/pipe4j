package pipe4j.pipe.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pipe4j.pipe.AbstractStreamPipe;

public class TeePipe extends AbstractStreamPipe {
	private final OutputStream teeStream;

	public TeePipe(OutputStream teeStream) {
		super();
		this.teeStream = teeStream;
	}

	public OutputStream getTeeStream() {
		return teeStream;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, new TeeOutputStream(os, getTeeStream()));
	}

	public class TeeOutputStream extends OutputStream {
		private final OutputStream tee, out;

		public TeeOutputStream(OutputStream chainedStream,
				OutputStream teeStream) {
			out = chainedStream;
			tee = teeStream;
		}

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

		public void close() throws IOException {
			flush();

			out.close();
			tee.close();
		}

		public void flush() throws IOException {
			out.flush();
			tee.flush();
		}
	}
}
