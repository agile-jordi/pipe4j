package pipe.adaptor;

import java.io.OutputStream;

import pipe.core.AbstractPipeOut;

public class OutAdaptor extends AbstractPipeOut {
	private final OutputStream outputStream;

	public OutAdaptor(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}
}
