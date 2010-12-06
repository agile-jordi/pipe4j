package pipe.adaptor;

import java.io.InputStream;

import pipe.core.AbstractPipeIn;

public class InAdaptor extends AbstractPipeIn {
	private final InputStream inputStream;

	public InAdaptor(InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
}
