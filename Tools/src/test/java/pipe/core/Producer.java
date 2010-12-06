package pipe.core;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

class Producer extends AbstractPipe implements PipeProcessor {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
		for (int i = 0; i < 10000; i++) {
			writer.write(i + ", used mem: "
					+ (runtime.totalMemory() - runtime.freeMemory()));
			writer.newLine();
		}
		writer.flush();
	}
}