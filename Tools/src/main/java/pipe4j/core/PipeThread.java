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

	public void close() {
		closeIn();
		closeOut();
	}

	private void closeIn() {
		if (this.in instanceof InputStream) {
			InputStream is = (InputStream) this.in;
			try {
				is.close();
			} catch (IOException e) {
			}
		} else if (this.in instanceof BlockingQueue) {
			BlockingQueue<?> queue = (BlockingQueue<?>) this.in;
			queue.clear();
		}
	}

	private void closeOut() {
		if (this.out instanceof OutputStream) {
			OutputStream os = (OutputStream) this.out;
			try {
				os.flush();
			} catch (IOException e) {
			}
			try {
				os.close();
			} catch (IOException e) {
			}
		} else if (this.out instanceof BlockingQueue) {
			@SuppressWarnings("unchecked")
			BlockingQueue<Object> queue = (BlockingQueue<Object>) this.out;
			try {
				queue.put(Null.INSTANCE);
			} catch (InterruptedException ignored) {
			}
		}
	}
}
