package pipe.string;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import pipe.core.AbstractPipeIn;

public class StringIn extends AbstractPipeIn {
	private String source;

	public StringIn(String source) throws IOException {
		this.source = source;
	}

	@Override
	protected InputStream getInputStream() throws Exception {
		return new ByteArrayInputStream(source.getBytes());
	}
}
