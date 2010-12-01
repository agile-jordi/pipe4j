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
			if (pipe.getReader() != null) {
				try {
					pipe.getReader().close();
				} catch (IOException e) {
				}
			}

			if (pipe.getWriter() != null) {
				try {
					pipe.getWriter().flush();
				} catch (IOException e) {
				}
				try {
					pipe.getWriter().close();
				} catch (IOException e) {
				}
			}
		}
	}
}