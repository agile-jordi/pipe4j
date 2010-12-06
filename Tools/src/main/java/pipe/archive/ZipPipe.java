package pipe.archive;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import pipe.core.AbstractPipeProcessor;

public class ZipPipe extends AbstractPipeProcessor {
	private final String entryName;
	private int level = Deflater.DEFAULT_COMPRESSION;

	public ZipPipe(String entryName) {
		super();
		this.entryName = entryName;
	}

	public ZipPipe(String entryName, int level) {
		super();
		this.entryName = entryName;
		this.level = level;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		ZipOutputStream out = new ZipOutputStream(os);
		out.putNextEntry(new ZipEntry(entryName));
		out.setLevel(level);
		super.run(is, out);
		out.closeEntry();
		out.finish();
	}
}
