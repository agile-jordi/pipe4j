package pipe4j.core;

import java.io.InputStream;
import java.io.OutputStream;

import pipe4j.pipe.SimpleStreamPipe;

public class DelayedMiddlePipe extends SimpleStreamPipe {
	private final long delayMilliseconds;

	public DelayedMiddlePipe(long delayMilliseconds) {
		super();
		this.delayMilliseconds = delayMilliseconds;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		byte[] buffer = new byte[8192];
		int bytesRead;
		while (!cancelled() && (bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
			try {
				Thread.sleep(delayMilliseconds);
			} catch (InterruptedException ignored) {
			}
		}
		os.flush();
	}
}
