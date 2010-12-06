package pipe.archive;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import pipe.core.AbstractDelegatingPipe;

public class UnzipPipe extends AbstractDelegatingPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		ZipInputStream in = new ZipInputStream(is);
		ZipEntry ze = in.getNextEntry();
		if (ze == null) {
			throw new IOException("Zip stream has no entry!");
		}
		super.run(in, os);
		in.closeEntry();
	}
}
