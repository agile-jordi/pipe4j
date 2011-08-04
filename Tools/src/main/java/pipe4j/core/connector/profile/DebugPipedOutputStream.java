package pipe4j.core.connector.profile;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class DebugPipedOutputStream extends PipedOutputStream {
	private long totalWaitTimeMilliseconds = 0l;

	public DebugPipedOutputStream(PipedInputStream in) throws IOException {
		super(in);
	}

	public synchronized long getTotalWaitTimeMilliseconds() {
		return totalWaitTimeMilliseconds;
	}

	@Override
	public synchronized void write(byte[] b) throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b);
		totalWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}

	@Override
	public synchronized void write(byte[] b, int off, int len)
			throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b, off, len);
		totalWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}

	@Override
	public synchronized void write(int b) throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b);
		totalWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}
}
