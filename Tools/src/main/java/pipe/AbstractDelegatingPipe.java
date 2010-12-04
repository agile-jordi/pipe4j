package pipe;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractDelegatingPipe extends AbstractPipe {

	@Override
	public void run() throws Exception {
		byte[] buffer = new byte[8192];
		InputStream reader = getInputStream();
		OutputStream writer = getOutputStream();
		int bytesRead;
		while (isRunning() && (bytesRead = reader.read(buffer)) != -1) {
			writer.write(buffer, 0, bytesRead);
		}
	}
}
