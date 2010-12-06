package pipe.core;

public abstract class PipeThread extends Thread {
	private Exception exception;

	public PipeThread(ThreadGroup group, String name) {
		super(group, name);
	}

	public abstract void close();

	public abstract Pipe getPipe();

	public Exception getException() {
		return exception;
	}

	protected void setException(Exception exception) {
		this.exception = exception;
	}

	public boolean hasError() {
		return exception != null;
	}
}
