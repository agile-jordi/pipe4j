package pipe.std;

import java.io.InputStream;
import java.io.OutputStream;

import pipe.core.AbstractDelegatingPipe;

public class Stderr extends AbstractDelegatingPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, System.err);
	}
}
