package pipe.sink;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class FileSink extends AbstractDelegatingPipe {
	private static final int defaultBufferSize = 8 * 1024;
	private int bufferSize = defaultBufferSize;

	public FileSink(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileSink(File file) throws IOException {
		this(new FileOutputStream(file));
	}

	public FileSink(FileOutputStream fileReader) throws IOException {
		setOutputStream(fileReader);
	}

	public FileSink(String filepath, int bufferSize) throws IOException {
		this(new File(filepath), bufferSize);
	}

	public FileSink(File file, int bufferSize) throws IOException {
		this(new FileOutputStream(file), bufferSize);
	}

	public FileSink(FileOutputStream fileReader, int bufferSize) throws IOException {
		setOutputStream(fileReader);
		this.bufferSize = bufferSize;
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
}
