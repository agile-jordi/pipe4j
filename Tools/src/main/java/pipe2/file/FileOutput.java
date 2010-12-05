package pipe2.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pipe2.core.AbstractDelegatingPipe;

public class FileOutput extends AbstractDelegatingPipe {
	private File file;

	public FileOutput(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileOutput(File file) throws IOException {
		this.file = file;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found: "
					+ file.getAbsolutePath());
		}

		if (!file.canRead()) {
			throw new IllegalArgumentException("Cannot read: "
					+ file.getAbsolutePath());
		}
		super.run(new FileInputStream(file), os);
	}
}
