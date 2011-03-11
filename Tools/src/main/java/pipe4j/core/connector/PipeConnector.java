package pipe4j.core.connector;

import pipe4j.core.Pipe;
import pipe4j.core.PipeThread;

/**
 * Responsible for connecting two pipes.
 * 
 * @author bbennett
 */
public interface PipeConnector {
	/**
	 * @param prev
	 *            First pipe
	 * @param next
	 *            Second pipe
	 * @return True if this implementation supports connecting this pair of
	 *         pipes
	 */
	boolean supports(Pipe<?, ?> prev, Pipe<?, ?> next);

	/**
	 * Connect two pipes.
	 * 
	 * @param pipe1
	 *            First pipe
	 * @param pipe2
	 *            Second pipe
	 */
	@SuppressWarnings("rawtypes")
	void connect(PipeThread pipe1, PipeThread pipe2);

	void close(Object in, Object out);
}
