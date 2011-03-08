package pipe4j.core.connector;

import pipe4j.core.Pipe;
import pipe4j.core.PipeThread;

public interface PipeConnector {
	boolean supports(Pipe<?, ?> prev, Pipe<?, ?> next);
	@SuppressWarnings("rawtypes")
	void connect(PipeThread pipe1, PipeThread pipe2) throws Exception;
	void close(Object in, Object out);
}
