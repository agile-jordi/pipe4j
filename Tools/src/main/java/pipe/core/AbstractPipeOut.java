package pipe.core;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractPipeOut extends AbstractPipe implements PipeOut {

	@Override
	public void run(InputStream is) throws Exception {
		byte[] buffer = new byte[8192];
		int bytesRead;
		OutputStream os = getOutputStream();
		while (!isCancelled() && (bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.flush();
	}

	protected abstract OutputStream getOutputStream() throws Exception;
}
