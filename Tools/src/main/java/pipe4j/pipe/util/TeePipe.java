package pipe4j.pipe.util;

import java.io.InputStream;
import java.io.OutputStream;

import pipe4j.pipe.AbstractStreamPipe;

public class TeePipe extends AbstractStreamPipe {
	private final OutputStream teeStream;

	public TeePipe(OutputStream teeStream) {
		super();
		this.teeStream = teeStream;
	}

	public OutputStream getTeeStream() {
		return teeStream;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, new TeeOutputStream(os, getTeeStream()));
	}
}
