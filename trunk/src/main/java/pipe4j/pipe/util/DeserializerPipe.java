package pipe4j.pipe.util;

import java.io.EOFException;
import java.io.ObjectInputStream;

import pipe4j.core.Connections;
import pipe4j.core.connector.BlockingBuffer;
import pipe4j.pipe.AbstractPipe;

/**
 * Deserializes objects from input stream and writes to output buffer.
 * 
 * @author bbennett
 */
public class DeserializerPipe extends AbstractPipe {
	@Override
	public void run(Connections connections) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(
				connections.getInputStream());
		BlockingBuffer outputBuffer = connections.getOutputBuffer();

		try {
			while (!cancelled()) {
				outputBuffer.put(ois.readObject());
			}
		} catch (EOFException eof) {
		}

		ois.close();
	}
}
