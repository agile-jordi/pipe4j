package pipe.source;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class StringSource extends AbstractDelegatingPipe {
	private static final int defaultBufferSize = 4 * 1024;
	private int bufferSize = defaultBufferSize;
	
	public StringSource(String source) throws IOException {
		this(source, defaultBufferSize);
	}
	
	public StringSource(String source, int bufferSize) throws IOException {
		setInputStream(new ByteArrayInputStream(source.getBytes()));
		this.bufferSize = bufferSize;
	}

	public int getBufferSize() {
		return bufferSize;
	}
}
