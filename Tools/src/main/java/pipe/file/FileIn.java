package pipe.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import pipe.core.AbstractPipeIn;

public class FileIn extends AbstractPipeIn {
	private File file;

	public FileIn(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileIn(File file) throws IOException {
		this.file = file;
	}

	@Override
	protected InputStream getInputStream() throws Exception {
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found: "
					+ file.getAbsolutePath());
		}

		if (!file.canRead()) {
			throw new IllegalArgumentException("Cannot read: "
					+ file.getAbsolutePath());
		}
		return new FileInputStream(file);
	}
}
