package pipe4j.core.connector.profile;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class DebugPipedOutputStream extends PipedOutputStream implements
		Profiled {
	private long writeWaitTimeMilliseconds = 0l;

	public DebugPipedOutputStream(PipedInputStream in) throws IOException {
		super(in);
	}

	@Override
	public long getReadWaitTimeMilliseconds() {
		return 0;
	}

	@Override
	public long getWriteWaitTimeMilliseconds() {
		return writeWaitTimeMilliseconds;
	}

	@Override
	public synchronized void write(byte[] b) throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b);
		writeWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}

	@Override
	public synchronized void write(byte[] b, int off, int len)
			throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b, off, len);
		writeWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}

	@Override
	public synchronized void write(int b) throws IOException {
		final long currentTime = System.currentTimeMillis();
		super.write(b);
		writeWaitTimeMilliseconds += System.currentTimeMillis() - currentTime;
	}
}
