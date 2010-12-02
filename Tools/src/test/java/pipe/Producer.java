package pipe;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

class Producer extends AbstractPipe implements Pipe {
	@Override
	public void run() throws Exception {
		Runtime runtime = Runtime.getRuntime();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				getOutputStream()));
		for (int i = 0; i < 10000; i++) {
			writer.write(i + ", used mem: "
					+ (runtime.totalMemory() - runtime.freeMemory()));
			writer.newLine();
		}
	}

	public boolean isRunning() {
		return false;
	}

	public void setRunning(boolean running) {
	}
}