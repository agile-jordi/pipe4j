package pipe4j.core.connector.profile;

import java.io.IOException;
import java.io.PipedInputStream;

public class DebugPipedInputStream extends PipedInputStream implements Profiled {
	private long readWaitTimeMilliseconds = 0l;

	@Override
	public long getReadWaitTimeMilliseconds() {
		return readWaitTimeMilliseconds;
	}

	@Override
	public long getWriteWaitTimeMilliseconds() {
		return 0;
	}

	@Override
	public synchronized int read() throws IOException {
		final long currentTime = System.currentTimeMillis();
		final int rv = super.read();
		readWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
		return rv;
	}

	@Override
	public synchronized int read(byte[] b) throws IOException {
		final long currentTime = System.currentTimeMillis();
		final int rv = super.read(b);
		readWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
		return rv;
	}

	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		final long currentTime = System.currentTimeMillis();
		final int rv = super.read(b, off, len);
		readWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
		return rv;
	}
}
