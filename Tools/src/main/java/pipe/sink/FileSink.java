package pipe.sink;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import pipe.AbstractPipedRunnable;

public class FileSink extends AbstractPipedRunnable {
	private static final int defaultBufferSize = 8 * 1024;
	private int bufferSize = defaultBufferSize;

	public FileSink(String filepath) throws IOException {
		this(new FileWriter(filepath));
	}

	public FileSink(File file) throws IOException {
		this(new FileWriter(file));
	}

	public FileSink(FileWriter fileReader) throws IOException {
		setWriter(fileReader);
	}

	public FileSink(String filepath, int bufferSize) throws IOException {
		this(new FileWriter(filepath), bufferSize);
	}

	public FileSink(File file, int bufferSize) throws IOException {
		this(new FileWriter(file), bufferSize);
	}

	public FileSink(FileWriter fileReader, int bufferSize) throws IOException {
		setWriter(fileReader);
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
