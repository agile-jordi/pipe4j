package pipe.std;

import java.io.InputStream;

import pipe.core.AbstractPipeIn;

public class Stdin extends AbstractPipeIn {
	@Override
	protected InputStream getInputStream() throws Exception {
		return System.in;
	}
}
