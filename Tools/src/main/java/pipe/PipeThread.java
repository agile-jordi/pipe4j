package pipe;

import java.io.IOException;

class PipeThread extends Thread {
	private final Pipe pipedRunnable;
	private Exception exception;
	
	public PipeThread(ThreadGroup threadGroup, Pipe pipedRunnable) {
		super(threadGroup, "Pipe: " + pipedRunnable.toString());
		this.pipedRunnable = pipedRunnable;
	}
	
	public boolean hasException() {
		return this.exception != null;
	}

	public Exception getException() {
		return exception;
	}
	
	@Override
	public void run() {
		try {
			pipedRunnable.run();
		} catch (Exception e) {
			exception = e;
		} finally {
			if (pipedRunnable.getReader() != null) {
				try {
					pipedRunnable.getReader().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (pipedRunnable.getWriter() != null) {
				try {
					pipedRunnable.getWriter().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					pipedRunnable.getWriter().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}