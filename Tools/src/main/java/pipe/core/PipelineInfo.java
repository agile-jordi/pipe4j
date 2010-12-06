package pipe.core;

public class PipelineInfo {
	private final ThreadGroup threadGroup;
	private Exception exception;

	public PipelineInfo(ThreadGroup threadGroup) {
		super();
		this.threadGroup = threadGroup;
	}

	public ThreadGroup getThreadGroup() {
		return threadGroup;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public boolean hasError() {
		return exception != null;
	}
}
