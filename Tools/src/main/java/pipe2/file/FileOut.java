package pipe2.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pipe2.core.AbstractDelegatingPipe;

public class FileOut extends AbstractDelegatingPipe {
	private File file;

	public FileOut(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileOut(File file) throws IOException {
		this.file = file;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IllegalArgumentException(
						"Destination cannot be a directory! Directory: "
								+ this.file.getAbsolutePath());
			}
			if (!file.delete())
				throw new IllegalArgumentException(
						"Could not delete existing file! File: "
								+ this.file.getAbsolutePath());
		} else {
			if (!file.createNewFile()) {
				throw new IllegalArgumentException("Could not create new file: "
						+ file.getAbsolutePath());
			}
		}
		super.run(is, new FileOutputStream(file));
	}
}
