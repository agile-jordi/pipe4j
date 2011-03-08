package pipe4j.core.connector;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import pipe4j.core.PipeThread;

public class StreamPipeConnector extends AbstractPipeConnector {
	protected boolean supports(Class<?> in, Class<?> out) {
		return in.isAssignableFrom(PipedInputStream.class)
				&& out.isAssignableFrom(PipedOutputStream.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void connect(PipeThread pipe1, PipeThread pipe2) throws Exception {
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream(in);
		pipe1.setOut(out);
		pipe2.setIn(in);
	}

	@Override
	public void close(Object in, Object out) {
		PipedInputStream pis = (PipedInputStream) in;
		try {
			pis.close();
		} catch (IOException e) {
		}
		PipedOutputStream pos = (PipedOutputStream) out;

		try {
			pos.flush();
		} catch (IOException e) {
		}
		try {
			pos.close();
		} catch (IOException e) {
		}
	}
}
