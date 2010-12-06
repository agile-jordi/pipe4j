package pipe.core;

import java.io.InputStream;
import java.io.OutputStream;

class PipeThread extends Thread {
	private final InputStream is;
	private final OutputStream os;
	private final Pipe pipe;
	private Exception exception;

	public PipeThread(InputStream is, OutputStream os, Pipe pipe) {
		super();
		this.is = is;
		this.os = os;
		this.pipe = pipe;
	}

	public Exception getException() {
		return exception;
	}

	public boolean hasError() {
		return exception != null;
	}

	public Pipe getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			pipe.run(is, os);
		} catch (Exception e) {
			exception = e;
		} finally {
			close();
		}
	}

	public void close() {
		if (is != null) {
			try {
				is.close();
			} catch (Exception e) {
				if (exception == null) {
					exception = e;
				}
			}
		}

		if (os != null) {
			try {
				os.flush();
			} catch (Exception e) {
				if (exception == null) {
					exception = e;
				}
			}
			try {
				os.close();
			} catch (Exception e) {
				if (exception == null) {
					exception = e;
				}
			}
		}
	}
}