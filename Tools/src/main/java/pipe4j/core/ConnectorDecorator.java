package pipe4j.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Used by pipes that need to wrap input or output. Example: wrapping the
 * {@link OutputStream} with a {@link GZIPOutputStream}.
 * 
 * @author bbennett
 * 
 * @param <I>
 *            Pipe input
 * @param <O>
 *            Pipe output
 */
public interface ConnectorDecorator<I, O> {
	/**
	 * Decorate the pipe input.
	 * 
	 * @param in
	 *            Pipe input
	 * @return decorated input
	 * @throws IOException
	 */
	I decorateIn(I in) throws IOException;

	/**
	 * Decorate the pipe output.
	 * 
	 * @param out
	 *            Pipe output
	 * @return decorated output
	 * @throws IOException
	 */
	O decorateOut(O out) throws IOException;
}
