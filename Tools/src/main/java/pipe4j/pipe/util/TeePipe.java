package pipe4j.pipe.util;

import java.io.IOException;
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
	public OutputStream decorateOut(OutputStream out) throws IOException {
		return new TeeOutputStream(out, getTeeStream());
	}
}
