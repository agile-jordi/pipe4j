package pipe.sink;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class FileSink extends AbstractDelegatingPipe {
	public FileSink(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileSink(File file) throws IOException {
		this(new FileOutputStream(file));
	}

	public FileSink(FileOutputStream fileReader) throws IOException {
		setOutputStream(fileReader);
	}
}
