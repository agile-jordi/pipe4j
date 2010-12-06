package pipe.core;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractPipeProcessor extends AbstractPipe implements
		PipeProcessor {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		byte[] buffer = new byte[8192];
		int bytesRead;
		while (!isCancelled() && (bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.flush();
	}
}
