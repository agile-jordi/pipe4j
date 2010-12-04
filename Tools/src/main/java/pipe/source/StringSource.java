package pipe.source;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class StringSource extends AbstractDelegatingPipe {
	private String source;

	public StringSource(String source) throws IOException {
		this.source = source;
	}

	@Override
	public void run() throws Exception {
		setInputStream(new ByteArrayInputStream(source.getBytes()));
		super.run();
	}
}
