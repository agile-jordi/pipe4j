package pipe2.std;

import java.io.InputStream;
import java.io.OutputStream;

import pipe2.core.AbstractDelegatingPipe;

public class Stdout extends AbstractDelegatingPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(is, System.out);
	}
}
