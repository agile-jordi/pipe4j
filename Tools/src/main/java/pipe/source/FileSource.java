package pipe.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class FileSource extends AbstractDelegatingPipe {
	private static final int defaultBufferSize = 8 * 1024;
	private int bufferSize = defaultBufferSize;

	public FileSource(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileSource(File file) throws IOException {
		this(new FileInputStream(file));
	}

	public FileSource(FileInputStream fileReader) throws IOException {
		setInputStream(fileReader);
	}

	public FileSource(String filepath, int bufferSize) throws IOException {
		this(new File(filepath), bufferSize);
	}

	public FileSource(File file, int bufferSize) throws IOException {
		this(new FileInputStream(file), bufferSize);
	}

	public FileSource(FileInputStream fileReader, int bufferSize) throws IOException {
		setInputStream(fileReader);
		this.bufferSize = bufferSize;
	}

	public int getBufferSize() {
		return bufferSize;
	}
}
