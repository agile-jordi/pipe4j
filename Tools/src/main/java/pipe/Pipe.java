package pipe;

import java.io.InputStream;
import java.io.OutputStream;

public interface Pipe {
	void setOutputStream(OutputStream outputStream);

	OutputStream getOutputStream();

	void setInputStream(InputStream inputStream);

	InputStream getInputStream();

	void run() throws Exception;

	void setPipeConfiguration(PipeConfiguration pipeConfiguration);

	PipeConfiguration getPipeConfiguration();

	boolean isRunning();

	void setRunning(boolean running);
}