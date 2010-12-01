package pipe;

import java.io.BufferedWriter;

class Producer extends AbstractBufferedPipedRunnable implements Pipe {
	@Override
	public void run() throws Exception {
		Runtime runtime = Runtime.getRuntime();
		BufferedWriter writer = (BufferedWriter) getWriter();
		for (int i = 0; i < 10000; i++) {
			writer.write(i + ", used mem: "
					+ (runtime.totalMemory() - runtime.freeMemory()));
			writer.newLine();
		}
	}
}