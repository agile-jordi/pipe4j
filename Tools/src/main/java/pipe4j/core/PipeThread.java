package pipe4j.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

public class PipeThread<I, O> extends Thread {
	private I in;
	private O out;
	private final Pipe<I, O> pipe;
	private Exception exception;

	public PipeThread(Pipe<I, O> pipe) {
		super();
		this.pipe = pipe;
	}

	public void setIn(I in) {
		this.in = in;
	}

	public void setOut(O out) {
		this.out = out;
	}

	public Pipe<I, O> getPipe() {
		return pipe;
	}

	public boolean hasError() {
		return this.exception != null;
	}

	public Exception getException() {
		return exception;
	}

	@Override
	public void run() {
		try {
			pipe.run(in, out);
		} catch (Exception e) {
			this.exception = e;
		} finally {
			close();
		}
	}

	private void close(Object obj) {
		if (obj instanceof InputStream) {
			InputStream is = (InputStream) obj;
			try {
				is.close();
			} catch (IOException e) {
			}
		} else if (obj instanceof OutputStream) {
			OutputStream os = (OutputStream) obj;
			try {
				os.flush();
			} catch (IOException e) {
			}
			try {
				os.close();
			} catch (IOException e) {
			}
		} else {
			BlockingQueue<?> queue = (BlockingQueue<?>) obj;
			queue.clear();
		}
	}

	public void close() {
		close(this.in);
		close(this.out);
	}
}
