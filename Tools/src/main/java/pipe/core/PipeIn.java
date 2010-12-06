package pipe.core;

import java.io.OutputStream;

public interface PipeIn extends Pipe {
	void run(OutputStream os) throws Exception;
}
