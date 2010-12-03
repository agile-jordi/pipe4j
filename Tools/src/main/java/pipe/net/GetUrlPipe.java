package pipe.net;

import java.io.IOException;
import java.net.URL;

import pipe.AbstractDelegatingPipe;

public class GetUrlPipe extends AbstractDelegatingPipe {
	private static final int defaultBufferSize = 8 * 1024;
	private int bufferSize = defaultBufferSize;

	public GetUrlPipe(String urlString) throws IOException {
		this(urlString, defaultBufferSize);
	}
	
	public GetUrlPipe(String urlString, int bufferSize) throws IOException {
		this.bufferSize = bufferSize;
		URL url = new URL(urlString);
		setInputStream(url.openStream());
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
}
