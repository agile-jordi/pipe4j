package pipe.sink;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import pipe.AbstractPipedRunnable;

public class StringSink extends AbstractPipedRunnable {
	private static final int defaultBufferSize = 4 * 1024;
	private int bufferSize = defaultBufferSize;

	public StringSink() throws IOException {
		setWriter(new StringWriter());
	}

	public StringSink(int bufferSize) throws IOException {
		this();
		this.bufferSize = bufferSize;
	}

	public StringBuffer getStringBuffer() {
		return ((StringWriter) getWriter()).getBuffer();
	}

	public String getString() {
		return ((StringWriter) getWriter()).getBuffer().toString();
	}

	@Override
	public void run() throws Exception {
		char[] buffer = new char[bufferSize];
		Reader reader = getReader();
		Writer writer = getWriter();
		while (true) {
			int bytesRead = reader.read(buffer);

			if (bytesRead == -1) {
				break; // we are done
			}

			writer.write(buffer, 0, bytesRead);
		}
	}
}
