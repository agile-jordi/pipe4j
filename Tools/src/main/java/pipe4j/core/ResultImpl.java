package pipe4j.core;

public class ResultImpl implements Result {
	private final Type type;
	private Exception exception;

	public ResultImpl(Type type) {
		super();
		this.type = type;
	}

	@Override
	public boolean hasException() {
		return exception != null;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	@Override
	public Exception getException() {
		return exception;
	}

	@Override
	public Type getType() {
		return type;
	}
}