package pipe.string;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import pipe.core.AbstractPipeOut;

public class StringOut extends AbstractPipeOut {
	private ByteArrayOutputStream baos;

	public String getString() {
		return new String(this.baos.toByteArray());
	}

	@Override
	protected OutputStream getOutputStream() throws Exception {
		baos = new ByteArrayOutputStream();
		return baos;
	}
}
