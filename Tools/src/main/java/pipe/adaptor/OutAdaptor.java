package pipe.adaptor;

import java.io.InputStream;
import java.io.OutputStream;

import pipe.core.AbstractDelegatingPipe;

public class OutAdaptor extends AbstractDelegatingPipe {
	private final OutputStream outputStream;

	public OutAdaptor(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, getOutputStream());
	}
}
