package pipe.string;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pipe.core.AbstractDelegatingPipe;

public class StringIn extends AbstractDelegatingPipe {
	private String source;

	public StringIn(String source) throws IOException {
		this.source = source;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(new ByteArrayInputStream(source.getBytes()), os);
	}
}
