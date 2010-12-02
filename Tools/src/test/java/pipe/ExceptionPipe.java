package pipe;

import java.io.IOException;


class ExceptionPipe extends AbstractPipe implements Pipe {
	@Override
	public void run() throws Exception {
		byte[] buffer = new byte[8];
		int n = getInputStream().read(buffer);
		getOutputStream().write(buffer, 0, n);
		throw new IOException("Argh!");
	}

	public boolean isRunning() {
		return false;
	}

	public void setRunning(boolean running) {
	}
}