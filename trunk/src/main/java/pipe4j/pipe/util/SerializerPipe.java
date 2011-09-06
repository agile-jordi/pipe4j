package pipe4j.pipe.util;

import java.io.ObjectOutputStream;

import pipe4j.core.Connections;
import pipe4j.core.connector.BlockingBuffer;
import pipe4j.pipe.AbstractPipe;

/**
 * Reads objects and serializes to output stream.
 * 
 * @author bbennett
 */
public class SerializerPipe extends AbstractPipe {
	@Override
	public void run(Connections connections) throws Exception {
		BlockingBuffer inputBuffer = connections.getInputBuffer();
		ObjectOutputStream oos = new ObjectOutputStream(
				connections.getOutputStream());

		Object obj;
		while (!cancelled() && (obj = inputBuffer.take()) != null) {
			oos.writeObject(obj);
		}
		
		oos.flush();
		oos.close();
	}
}
