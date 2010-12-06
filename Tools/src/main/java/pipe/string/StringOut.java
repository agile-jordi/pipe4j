package pipe.string;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import pipe.core.AbstractDelegatingPipe;

public class StringOut extends AbstractDelegatingPipe {
	private ByteArrayOutputStream baos;

	public String getString() {
		return new String(this.baos.toByteArray());
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		baos = new ByteArrayOutputStream();
		super.run(is, baos);
	}
}
