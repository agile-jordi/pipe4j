package pipe.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class FileSource extends AbstractDelegatingPipe {
	public FileSource(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileSource(File file) throws IOException {
		this(new FileInputStream(file));
	}

	public FileSource(FileInputStream fileReader) throws IOException {
		setInputStream(fileReader);
	}
}
