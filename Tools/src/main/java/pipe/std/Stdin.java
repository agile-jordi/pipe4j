package pipe.std;

import java.io.InputStream;
import java.io.OutputStream;

import pipe.core.AbstractDelegatingPipe;

public class Stdin extends AbstractDelegatingPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(System.in, os);
	}
}
