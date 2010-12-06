package pipe.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pipe.core.AbstractPipeOut;

public class FileOut extends AbstractPipeOut {
	private File file;

	public FileOut(String filepath) throws IOException {
		this(new File(filepath));
	}

	public FileOut(File file) throws IOException {
		this.file = file;
	}

	@Override
	protected OutputStream getOutputStream() throws Exception {
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
				throw new IllegalArgumentException(
						"Could not create new file: " + file.getAbsolutePath());
			}
		}
		return new FileOutputStream(file);
	}
}
