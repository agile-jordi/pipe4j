package pipe.source;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import pipe.AbstractPipedRunnable;

public class StringSource extends AbstractPipedRunnable {
	private static final int defaultBufferSize = 4 * 1024;
	private int bufferSize = defaultBufferSize;
	
	public StringSource(String source) throws IOException {
		setReader(new StringReader(source));
	}
	
	public StringSource(String source, int bufferSize) throws IOException {
		this(source);
		this.bufferSize = bufferSize;
	}

	@Override
	public void run() throws Exception {
		char[] buffer = new char[bufferSize];
		Reader reader = getReader();
		Writer writer = getWriter();
        while (true) {
            int bytesRead = reader.read(buffer);

            if (bytesRead == -1) {
                break; //we are done
            }

            writer.write(buffer, 0, bytesRead);
        }
	}
}
