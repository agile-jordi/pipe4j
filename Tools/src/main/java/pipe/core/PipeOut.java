package pipe.core;

import java.io.InputStream;

public interface PipeOut extends Pipe {
	void run(InputStream is) throws Exception;
}
