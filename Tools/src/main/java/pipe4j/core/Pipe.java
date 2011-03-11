package pipe4j.core;

/**
 * Base interface for all pipes. Input and Output types will be declared by
 * implementations.
 * 
 * @author bbennett
 * 
 * @param <I>
 *            Input type
 * @param <O>
 *            Output type
 */
public interface Pipe<I, O> {
	/**
	 * Run the pipe. Implementations should loop the following steps:
	 * 
	 * <ol>
	 * <li>Check if cancellation was requested;</li>
	 * <li>Read from input;</li>
	 * <li>Process data;</li>
	 * <li>Write to output.</li>
	 * </ol>
	 * 
	 * @param in
	 *            Pipe input
	 * @param out
	 *            Pipe output
	 * @throws Exception
	 */
	void run(I in, O out) throws Exception;

	/**
	 * Request execution to be canceled.
	 */
	void cancel();
}
