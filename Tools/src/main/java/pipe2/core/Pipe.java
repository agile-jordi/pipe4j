package pipe2.core;

import java.io.InputStream;
import java.io.OutputStream;

public interface Pipe {
	void run(InputStream is, OutputStream os) throws Exception;

	void cancel();
}
