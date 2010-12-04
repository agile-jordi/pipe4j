package pipe;

import java.io.IOException;

class PipeThread extends Thread {
	private final Pipe pipe;
	private Exception exception;

	public PipeThread(ThreadGroup threadGroup, Pipe pipedRunnable) {
		super(threadGroup, "Pipe: " + pipedRunnable.toString());
		this.pipe = pipedRunnable;
	}

	public boolean hasException() {
		return this.exception != null;
	}

	public Exception getException() {
		return exception;
	}

	public Pipe getPipe() {
		return pipe;
	}

	@Override
	public void run() {
		try {
			pipe.run();
		} catch (Exception e) {
			exception = e;
		} finally {
			if (pipe.getInputStream() != null) {
				try {
					pipe.getInputStream().close();
				} catch (IOException e) {
				}
			}

			if (pipe.getOutputStream() != null) {
				try {
					pipe.getOutputStream().flush();
				} catch (IOException e) {
				}
				try {
					pipe.getOutputStream().close();
				} catch (IOException e) {
				}
			}
		}
	}
}