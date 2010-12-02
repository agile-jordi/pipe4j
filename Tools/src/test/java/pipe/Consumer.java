package pipe;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Consumer extends AbstractPipe implements Pipe {
	@Override
	public void run() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				getInputStream()));
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