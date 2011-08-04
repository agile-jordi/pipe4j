package pipe4j.core.connector.profile;

import java.io.IOException;
import java.io.PipedInputStream;

public class DebugPipedInputStream extends PipedInputStream {
	private long totalWaitTimeMilliseconds = 0l;

	public synchronized long getTotalWaitTimeMilliseconds() {
		return totalWaitTimeMilliseconds;
	}

	@Override
	public synchronized int read() throws IOException {
		final long currentTime = System.currentTimeMillis();
		final int rv = super.read();
		totalWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
		return rv;
	}

	@Override
	public synchronized int read(byte[] b) throws IOException {
		final long currentTime = System.currentTimeMillis();
		final int rv = super.read(b);
		totalWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
		return rv;
	}

	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		final long currentTime = System.currentTimeMillis();
		final int rv = super.read(b, off, len);
		totalWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
		return rv;
	}
}
