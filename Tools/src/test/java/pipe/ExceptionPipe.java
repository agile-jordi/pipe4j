package pipe;

import java.io.IOException;


class ExceptionPipe extends AbstractPipedRunnable implements Pipe {
	@Override
	public void run() throws Exception {
		char[] buffer = new char[8];
		int n = getReader().read(buffer);
		getWriter().write(buffer, 0, n);
		throw new IOException("Argh!");
	}

	public boolean isRunning() {
		return false;
	}

	public void setRunning(boolean running) {
	}
}