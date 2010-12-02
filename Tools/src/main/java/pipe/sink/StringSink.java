package pipe.sink;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class StringSink extends AbstractDelegatingPipe {
	private static final int defaultBufferSize = 4 * 1024;
	private int bufferSize = defaultBufferSize;
	private ByteArrayOutputStream baos;

	public StringSink() throws IOException {
		this(defaultBufferSize);
	}

	public StringSink(int bufferSize) throws IOException {
		this.bufferSize = bufferSize;
		baos = new ByteArrayOutputStream();
		setOutputStream(baos);
	}

	public String getString() {
		return new String(this.baos.toByteArray());
	}

	public int getBufferSize() {
		return bufferSize;
	}
}
