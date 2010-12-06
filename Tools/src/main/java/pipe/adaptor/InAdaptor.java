package pipe.adaptor;

import java.io.InputStream;
import java.io.OutputStream;

import pipe.core.AbstractDelegatingPipe;

public class InAdaptor extends AbstractDelegatingPipe {
	private final InputStream inputStream;

	public InAdaptor(InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(getInputStream(), os);
	}
}
