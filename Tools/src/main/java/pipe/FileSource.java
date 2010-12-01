package pipe;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class FileSource extends AbstractPipedRunnable {
	private static final int defaultBufferSize = 8 * 1024;
	private int bufferSize = defaultBufferSize;

	public FileSource(String filepath) throws IOException {
		this(new FileReader(filepath));
	}

	public FileSource(File file) throws IOException {
		this(new FileReader(file));
	}

	public FileSource(FileReader fileReader) throws IOException {
		setReader(fileReader);
	}

	public FileSource(String filepath, int bufferSize) throws IOException {
		this(new FileReader(filepath), bufferSize);
	}

	public FileSource(File file, int bufferSize) throws IOException {
		this(new FileReader(file), bufferSize);
	}

	public FileSource(FileReader fileReader, int bufferSize) throws IOException {
		setReader(fileReader);
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
