package pipe;

import java.io.BufferedReader;

class Consumer extends AbstractBufferedPipedRunnable implements Pipe {
	@Override
	public void run() throws Exception {
		BufferedReader reader = (BufferedReader) getReader();
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	}

	public boolean isRunning() {
		return false;
	}

	public void setRunning(boolean running) {
	}
}