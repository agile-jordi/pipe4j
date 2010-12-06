package pipe2.core;

import java.io.InputStream;
import java.io.OutputStream;

class SleepPipe extends MiddlePipe {
	private long millis;

	public SleepPipe(long millis) {
		super();
		this.millis = millis;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, os);
		Thread.sleep(millis);
	}
}