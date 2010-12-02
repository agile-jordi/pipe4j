package pipe;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractDelegatingPipe extends AbstractPipe {

	@Override
	public void run() throws Exception {
		byte[] buffer = new byte[getBufferSize()];
		InputStream reader = getInputStream();
		OutputStream writer = getOutputStream();
		int bytesRead;
		while ((bytesRead = reader.read(buffer)) != -1) {
			writer.write(buffer, 0, bytesRead);
		}
	}

	protected abstract int getBufferSize();

}
