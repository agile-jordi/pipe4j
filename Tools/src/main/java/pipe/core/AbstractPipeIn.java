package pipe.core;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractPipeIn extends AbstractPipe implements PipeIn {

	@Override
	public void run(OutputStream os) throws Exception {
		byte[] buffer = new byte[8192];
		int bytesRead;
		InputStream is = getInputStream();
		while (!isCancelled() && (bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.flush();
	}

	protected abstract InputStream getInputStream() throws Exception;
}
