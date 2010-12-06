package pipe.std;

import java.io.OutputStream;

import pipe.core.AbstractPipeOut;

public class Stderr extends AbstractPipeOut {
	@Override
	protected OutputStream getOutputStream() throws Exception {
		return System.err;
	}
}
