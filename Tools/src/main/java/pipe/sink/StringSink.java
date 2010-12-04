package pipe.sink;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pipe.AbstractDelegatingPipe;

public class StringSink extends AbstractDelegatingPipe {
	private ByteArrayOutputStream baos;

	public StringSink() throws IOException {
	}

	public String getString() {
		return new String(this.baos.toByteArray());
	}
	
	@Override
	public void run() throws Exception {
		baos = new ByteArrayOutputStream();
		setOutputStream(baos);
		super.run();
	}
}
