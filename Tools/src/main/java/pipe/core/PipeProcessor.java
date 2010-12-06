package pipe.core;

import java.io.InputStream;
import java.io.OutputStream;

public interface PipeProcessor extends Pipe {
	void run(InputStream is, OutputStream os) throws Exception;
}
